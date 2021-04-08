package com.boot.security.server.config;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.boot.security.server.page.table.PageTableArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	/**
	 * 跨域支持
	 *
	 * @return
	 * addCorsMappings：跨域 重写 addCorsMappings()方法
	 * 而所谓的跨域请求就是指：当前发起请求的域与该请求指向的资源所在的域不一样。
	 * 这里的域指的是这样的一个概念：我们认为若协议 + 域名 + 端口号均相同，那么就是同域。
	 */
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedMethods("*");
//			}
//		};
//	}

	/**
	 * datatable分页解析
	 *
	 * @return
	 */
	@Bean
	public PageTableArgumentResolver tableHandlerMethodArgumentResolver() {
		return new PageTableArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(tableHandlerMethodArgumentResolver());
		System.out.println("ssd");
	}

	/**
	 * 上传文件根路径
	 */
	@Value("${files.path}")
	private String filesPath;

	/**
	 * 外部文件访问   静态资源  ，我们想自定义静态资源映射目录的话，
	 * 只需重写addResourceHandlers方法即可。 可以直接访问
	 * eg:http://localhost:8080/pages/dict/addDict.html
	 *
	 *
	 1.  src 路径下的文件 在编译后都会放到 WEB-INF/classes 路径下。默认classpath 就是指这里。
	 2. 用maven构建 项目时，resources 目录就是默认的classpath

	 addResoureHandler：指的是对外暴露的访问路径
	 addResourceLocations：指的是内部文件放置的目录

	 FILE_URL_PREFIX :      file:
	 filesPath       ${file-path:d:/files}

	 */
	@Override
	//springboot中配置addResourceHandler和addResourceLocations，使得可以从磁盘中读取图片、视频、音频等
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/statics/**")
				.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + filesPath + File.separator);
	}

}
