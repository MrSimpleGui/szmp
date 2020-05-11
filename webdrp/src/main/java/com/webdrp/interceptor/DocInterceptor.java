package com.webdrp.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuanming on 2018/8/4.
 * 系统用户token的拦截方案
 */
public class DocInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)){
//            logger.debug("不是一个映射方法！");
//            return true;
//        }
        String auth = request.getRequestURI();

        System.out.println("request = [" +request.getQueryString() + "]");
        if (request.getQueryString()!=null&&request.getQueryString()!="null"&&request.getQueryString().length()!=0)
            return true;
        else{
            response.sendRedirect("/");
            return true;
        }
    }
}
