package org.marking.emaromba.auth.util.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect @Component
public class LogService {
	
	@Pointcut("execution(* org.marking.emaromba.auth.rest.*.*(..))")
	private void controllers() {}
	
	@Pointcut("execution(* org.marking.emaromba.auth.service.*.*(..))")
	private void services() {}
	
	@Pointcut("@annotation(org.marking.emaromba.auth.util.logging.Log)")
	private void annotationLog() {}
	
	@Around("controllers() || services() || annotationLog()")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		
		final Logger logger = LogManager.getLogger(joinPoint.getTarget().getClass());
		
		ThreadContext.put("_from_api", "true");
		ThreadContext.put("_originalSourceClassName", joinPoint.getSignature().getDeclaringTypeName());
		ThreadContext.put("_originalSourceMethodName", joinPoint.getSignature().getName());
		
		try {
			logger.info("Enter in " + joinPoint.getSignature().getName());
			Object returned = joinPoint.proceed();
			logger.info("Returned of " + joinPoint.getSignature().getName(), returned);
			
			return returned;
		}
		catch(Throwable e) {
			logger.error("Exception in " + joinPoint.getSignature().getName(), e);
			throw e;
		}	
	}
}
