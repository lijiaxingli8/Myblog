package com.coolding.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
/*
    日志切面
    记录日志信息
*/
public class LogAspect {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.coolding.blog.web.*.*(..))")
    public void log(){}

    @Before("log()")
    /**
     * 得到请求的url,ip,方法和参数
     * */
    public void DoBefore (JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Result : {}",requestLog);
    }

    @After("log()")
    public void DoAfter () {
        logger.info("---------DoAfter-----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void DoAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object args;

        public RequestLog(String url, String ip, String classMethod, Object args) {
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
                    ", args=" + args +
                    '}';
        }
    }
}
