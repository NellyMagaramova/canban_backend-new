package com.backend.canban.canban_backend.aop;

import lombok.extern.java.Log;
import  org.aspectj.lang.ProceedingJoinPoint;
import  org.aspectj.lang.annotation.Around;
import  org.aspectj.lang.annotation.Aspect;
import  org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
@Log
public class LoggingAspect {

    @Around("execution ( * com.backend.canban.canban_backend.controllers..*(..)))")
    public Object profileControllerMethods(ProceedingJoinPoint  proceedingJoinPoint) throws  Throwable
    {
        MethodSignature  methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info("-----Execution " + className + "." + methodName + "----------");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /* выполняем сам код */
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        log.info("---Execution time of" + className + "." + methodName + "::"
            + stopWatch.getTotalTimeMillis() + "ms" );
        return  result;
    }
}
