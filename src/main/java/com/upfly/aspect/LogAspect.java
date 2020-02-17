package com.upfly.aspect;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 日志记录器

    @Pointcut("execution(* com.upfly.controller.*.*(..))") // @Pointcut定义切面 execution拦截哪些类
    public void log() {}

    @Before("log()") // 这个方法会在切面之前执行
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(); // 类名.方法名
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, className, args);
        logger.info("Request: {}", requestLog);
    }

    @After("log()")
    public void doAfter() {}

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) { // 方法执行完返回的时候进行拦截 result参数捕获拦截方法所返回的内容
        logger.info("Result: {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
