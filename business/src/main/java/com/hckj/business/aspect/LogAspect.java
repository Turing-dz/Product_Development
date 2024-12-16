package com.hckj.business.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 定义切点（这里是controller里面所有的public方法）
     */
    @Pointcut("execution(public * com.hckj..*Controller.*(..))")
    public void pointe() {}
    @Before("pointe()")
    public void doBefore(){
        log.info("前置通知");
    }
    @After("pointe()")
    public void doAfter(){
        log.info("后置通知");
    }
    @Around("pointe()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("环绕开始");
        Object result = proceedingJoinPoint.proceed();
        log.info("环绕结束");
        return result;
    }
}
