package com.boot.security.server.advice;

import com.boot.security.server.utils.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.boot.security.server.annotation.LogAnnotation;
import com.boot.security.server.model.SysLogs;
import com.boot.security.server.service.SysLogService;

import io.swagger.annotations.ApiOperation;

/**
 * 统一日志处理
 */
@Aspect
@Component
public class LogAdvice {
	/*
	在AOP中切面就是与业务逻辑独立，但又垂直存在于业务逻辑的代码结构中的通用功能组合；切面与业务逻辑相交的点就是切点；
	连接点就是把业务逻辑离散化后的关键节点；切点属于连接点，是连接点的子集；
	Advice（增强）就是切面在切点上要执行的功能增加的具体操作；
	在切点上可以把要完成增强操作的目标对象（Target）连接到切面里，这个连接的方式就叫织入。
	 */

	@Autowired
	private SysLogService logService;

	//定义切点
	@Pointcut("@annotation(com.boot.security.server.annotation.LogAnnotation)")
	public void pointcut(){}

	@Around("pointcut()")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
        sysLogs.setUser(UserUtil.getLoginUser()); // 设置当前登录用户
		//获取当前代理的对象 可以通过 joinPoint.getSignature() 获取注解所在方法上的信息
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		//获取使用日志注解 也可以在参数中修改为
		//@Around("pointcut()  && @annotation("logAnnotation")")
		//public Object logSave(ProceedingJoinPoint joinPoint,LogAnnotation logAnnotation)
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
		if (StringUtils.isEmpty(module)) {
			//获取swagger中对于该方法的描述信息
			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
			if (apiOperation.value() != null) {
				module = apiOperation.value();
			}
		}

		if (StringUtils.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setModule(module);

		try {
			//调用或调用您在其上设置切入点的方法（proceed）
			//环绕通知 ProceedingJoinPoint 执行proceed方法的作用是让目标方法执行，
			//这也是环绕通知和前置、后置通知方法的一个最大区别。
			//简单理解，环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的.
			Object object = joinPoint.proceed();

			sysLogs.setFlag(true);

			return object;
		} catch (Exception e) {
			sysLogs.setFlag(false);
			sysLogs.setRemark(e.getMessage());
			throw e;
        } finally {
            if (sysLogs.getUser() != null) {
                logService.save(sysLogs);
            }
        }

	}
}
