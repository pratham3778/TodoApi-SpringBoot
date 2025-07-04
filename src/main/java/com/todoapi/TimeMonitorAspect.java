package com.todoapi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitorAspect {

    @Around("@annotation(TimeMonitor)")
    public void logTime(ProceedingJoinPoint joinPoint) {
        // System.out.println("Logging Time"); //business logic

        long start = System.currentTimeMillis(); //start time of the code

        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            System.out.println("Something went wrong during the execution");
        } finally {
            long end = System.currentTimeMillis();
            long totalExecutionTime = end - start;
            System.out.println("Total Time of execution of the method is: " + totalExecutionTime + " ms..");
        }
    }
}
