package com.devsync.authservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.devsync.authservice.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[LOG BEFORE] Method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.devsync.authservice.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[LOG AFTER RETURN] Method: {}, Result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.devsync.authservice.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("[LOG EXCEPTION] Method: {}, Exception: ", joinPoint.getSignature().getName(), ex);
    }

}
