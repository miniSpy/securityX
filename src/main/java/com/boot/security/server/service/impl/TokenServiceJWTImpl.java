package com.boot.security.server.service.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.dto.Token;
import com.boot.security.server.service.SysLogService;
import com.boot.security.server.service.TokenService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token存到redis的实现类<br>
 * jwt实现的token
 *
 */
@Primary
@Service
public class TokenServiceJWTImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	//注入操作redis的类
	private RedisTemplate<String, LoginUser> redisTemplate;
	@Autowired
	private SysLogService logService;
	/**
	 * 私钥
	 */
	@Value("${token.jwtSecret}")
	private String jwtSecret;

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

	@Override
	public Token saveToken(LoginUser loginUser) {
		loginUser.setToken(UUID.randomUUID().toString());
		//存储Token到redis数据库  非jwttoken 是随机生成的字符串 这个就是uuid
		//并且设置登录时间和过期时间给loginUser
		cacheLoginUser(loginUser);
		// 登陆日志
		logService.save(loginUser.getId(), "登陆", true, null);


		//将刚才生成的Token转换为JJWT 的Token
		String jwtToken = createJWTToken(loginUser);

		/*
			private String token;
			登陆时间戳（毫秒）
			private Long loginTime;
		 */
		return new Token(jwtToken, loginUser.getLoginTime());
	}

	/**
	 * 生成jwt
	 * 3
	 * @param loginUser
	 * @return
	 */
	private String createJWTToken(LoginUser loginUser) {
		//claims 用于自定义JWT Token中的荷载
		Map<String, Object> claims = new HashMap<>();
		// 放入一个随机字符串，通过该串可找到登陆用户
		claims.put(LOGIN_USER_KEY, loginUser.getToken());

				 // getKeyInstance() 单例
		         //签名算法 和 盐 signWith(SignatureAlgorithm.HS256, getKeyInstance())
		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();

		return jwtToken;
	}


	//存储
	private void cacheLoginUser(LoginUser loginUser) {
		//给loginUser设置登录时间和过期时间
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
		// 根据uuid将loginUser缓存 key---->value

	redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);




	}

	/**
	 * 更新缓存的用户信息
	 */
	@Override
	public void refresh(LoginUser loginUser) {
		cacheLoginUser(loginUser);
	}

	@Override
	public LoginUser getLoginUser(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}

		return null;
	}

	@Override
	public boolean deleteToken(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			LoginUser loginUser = redisTemplate.opsForValue().get(key);
			if (loginUser != null) {
				//redis中删除
				redisTemplate.delete(key);
				// 退出日志
				logService.save(loginUser.getId(), "退出", true, null);
				return true;
			}
		}

		return false;
	}

	private String getTokenKey(String uuid) {
		return "tokens:" + uuid;
	}


	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (TokenServiceJWTImpl.class) {
				if (KEY == null) {// 双重锁
					//DatatypeConverter.parseBase64Binary 将字符串转换为BASE64编码
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					// 通过秘钥 得到签名
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}

		return KEY;
	}

	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}
		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
			log.error("{}已过期", jwtToken);
		} catch (Exception e) {
			log.error("{}", e);
		}

		return null;
	}
}
