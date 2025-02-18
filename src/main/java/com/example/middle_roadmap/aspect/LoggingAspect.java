package com.example.middle_roadmap.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @SuppressWarnings("unused")
    @Pointcut("execution(* com.example.middle_roadmap.controller.*.*(..))")
    private void handleControllerLogging() {
    }

    @SuppressWarnings("unused")
    @Pointcut("execution(* com.example.middle_roadmap.service.*.*(..))")
    private void handleServiceLogging() {
    }

    @SuppressWarnings("unused")
    @Pointcut("execution(* com.example.middle_roadmap.repository.*.*(..))")
    private void handleRepositoryLogging() {
    }

    @SuppressWarnings("unused")
    @Pointcut("handleControllerLogging() || handleServiceLogging() || handleRepositoryLogging()")
    private void handleFMSFlow() {
    }

    @Before("handleFMSFlow()")
    public void before(JoinPoint theJoinPoint) {
        String method = theJoinPoint.getSignature().toShortString();
        String timeStamp = LocalDateTime.now().toString();
        String loginId = "defaultLoginID";
        log.info("Before calling method: {} at {} by login ID: {}", method, timeStamp, loginId);
    }

    @AfterReturning(
            pointcut = "handleFMSFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
        String method = theJoinPoint.getSignature().toShortString();
        String timeStamp = LocalDateTime.now().toString();
        String loginId = "defaultLoginID";
        log.info("After Returning from method: {} at {} by login ID: {}", method, timeStamp, loginId);
        log.info("Method returned: {}", theResult);
    }

    @AfterThrowing(
            pointcut = "handleFMSFlow()",
            throwing = "theException")
    public void afterThrowing(JoinPoint theJoinPoint, Throwable theException) {
        String method = theJoinPoint.getSignature().toShortString();
        log.error("Executing After Throwing on method: {}", method);
        log.error("The exception is: ", theException);
    }
}
