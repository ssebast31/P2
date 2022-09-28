package com.jobly.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static Logger log = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before(value="execution(* com.jobly.*.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {	
		log.info(String.format("In - [ %s : %s ]: Started", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
	}
	
	@After(value="execution(* com.jobly.*.*.*(..))")
	public void logAfter(JoinPoint joinPoint) {	
		log.info(String.format("In - [ %s : %s ]: Ended", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
	}
	
	
}
