package com.hckj.business.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 定义切点（这里是controller里面所有的public方法）
     */
    @Pointcut("execution(public * com.hckj..*Controller.*(..))")
    public void pointe() {}
//    @Before("pointe()")
//    public void doBefore(){
//        log.info("前置通知");
//    }
//    @After("pointe()")
//    public void doAfter(){
//        log.info("后置通知");
//    }
//    @Around("pointe()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        log.info("环绕开始");
//        Object result = proceedingJoinPoint.proceed();
//        log.info("环绕结束");
//        return result;
//    }
    @Around("pointe()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("----------------- 环绕通知开始  -----------------");
        long startTime = System.currentTimeMillis();
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String signature = proceedingJoinPoint.getSignature().toString();
        String name = signature;
        // 打印请求信息
        log.info("请求地址: {}", request.getRequestURL().toString(), request.getMethod());
        log.info("类名方法: {}", signature, name);
        log.info("远程地址: {}", request.getRemoteAddr());
        // 打印请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            args[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {"cvv2", "idCard"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        log.info("请求参数：{}", JSONObject.toJSONString(arguments, excludeFilter));
        Object result = proceedingJoinPoint.proceed();//执行目标方法,切点的处理过程
        log.info("返回结果：{}", JSONObject.toJSONString(result, excludeFilter));
        log.info("----------------- 环绕通知结束 耗时：{} ms -----------------", System.currentTimeMillis() - startTime);
        return result;
    };

}
