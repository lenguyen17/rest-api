package com.restapi.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.restapi.service.*.*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Before executing service method: " + methodName);
    }

    @After("execution(* com.restapi.service.*.*(..))")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("After executing service method: " + methodName);
    }
}
