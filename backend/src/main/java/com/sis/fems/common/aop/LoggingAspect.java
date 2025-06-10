package com.sis.fems.common.aop;

import com.sis.fems.a.mapper.LogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final LogMapper logMapper;

    public LoggingAspect(@Lazy LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Around("execution(* com.sis.fems..dto..*(..))")  // 실행 방지 차원 지정 영역 수정
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String parameters = Arrays.toString(joinPoint.getArgs());

        logger.info("▶ ENTER: {} with params: {}", methodName, parameters);
        try {
            logMapper.insertLog("INFO", "▶ ENTER: " + methodName + " with params: " + parameters);
        } catch (Exception e) {
            logger.warn("Log insert failed (ENTER): {}", e.getMessage());
        }

        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("✔ EXIT: {} returned: {}", methodName, result);
            try {
                logMapper.insertLog("INFO", "✔ EXIT: " + methodName + " returned: " + result);
            } catch (Exception e) {
                logger.warn("Log insert failed (EXIT): {}", e.getMessage());
            }
            return result;
        } catch (Throwable ex) {
            logger.error("❌ EXCEPTION in {}", methodName, ex);
            try {
                logMapper.insertLog("ERROR", "❌ EXCEPTION in " + methodName + ": " + ex.getMessage());
            } catch (Exception e) {
                logger.warn("Log insert failed (ERROR): {}", e.getMessage());
            }
            throw ex;
        }
    }
}

