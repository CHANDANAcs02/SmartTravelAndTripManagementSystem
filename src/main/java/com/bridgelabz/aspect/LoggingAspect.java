package com.bridgelabz.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Applies logging to all methods inside the service package
    @Around("execution(* com.bridgelabz.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint)
            throws Throwable {

        Logger logger =
                LoggerFactory.getLogger(
                        joinPoint.getTarget().getClass()
                );

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        long startTime = System.currentTimeMillis();

        logger.info(
                "Service started - Class: {}, Method: {}",
                className,
                methodName
        );

        try {

            Object result = joinPoint.proceed();

            long executionTime =
                    System.currentTimeMillis() - startTime;

            logger.info(
                    "Service completed - Class: {}, Method: {}, Execution Time: {} ms",
                    className,
                    methodName,
                    executionTime
            );

            logger.debug(
                    "Service result - Class: {}, Method: {}, Result: {}",
                    className,
                    methodName,
                    result
            );

            return result;

        } catch (Exception exception) {

            long executionTime =
                    System.currentTimeMillis() - startTime;

            logger.warn(
                    "Service warning - Class: {}, Method: {}, Error: {}",
                    className,
                    methodName,
                    exception.getMessage()
            );

            logger.error(
                    "Service failed - Class: {}, Method: {}, Execution Time: {} ms",
                    className,
                    methodName,
                    executionTime,
                    exception
            );

            throw exception;
        }
    }
}