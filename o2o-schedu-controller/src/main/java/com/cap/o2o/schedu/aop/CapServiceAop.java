package com.cap.o2o.schedu.aop;

import com.cap.o2o.service.LogExceptionService;
import com.cap.o2o.service.LogTimeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ron on 2018/11/29.
 */
@Component
@Aspect
@Slf4j
public class CapServiceAop {

    @Autowired
    private LogExceptionService logExceptionService;
    @Autowired
    private LogTimeService logTimeService;

    @Pointcut("execution(* com.cap.o2o.service.impl..*.*(..))")
    public void capServiceAop(){}

    @Before(value = "capServiceAop()")
    public void methodBefore(JoinPoint joinPoint){
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        String name = joinPoint.getSignature().getName();
        log.info("{} 开始执行",declaringType+"."+name);
    }


    @After(value = "capServiceAop()")
    public void methodAfter(JoinPoint joinPoint){
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        String name = joinPoint.getSignature().getName();
        log.info("{} 结束执行",declaringType+"."+name);
    }

    @AfterThrowing(pointcut = "capServiceAop()",throwing = "e")
    public void methodAfterThrowing(JoinPoint joinPoint, Throwable e){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logExceptionService.addRecord(joinPoint,request,e);
    }


}
