package com.tangyu.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tangyu.utils.HttpUtils;

/****
 * 
 * @author tangyu
 * @date 2016年12月6日 下午5:07:54
 */
@Aspect
@Component
@Order(-5)
public class LogRecodConfig {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * com.tangyu.web..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("url:{},method:{},IP:{},method:{},args:{}", request.getRequestURL().toString(), request.getMethod(),
				HttpUtils.getRequestIp(request),
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
				Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.info("response:{},cost time:{}", ret, (System.currentTimeMillis() - startTime.get()));
	}
}
