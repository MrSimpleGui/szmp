package com.webdrp.interceptor;

import com.webdrp.common.PBKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
/**
 * @Author: zhang yuan ming
 * @Date: create in 11:01 2020-03-16
 * @mail: zh878998515@gmail.com
 * @Description:全局请求日志
 */
@Log4j2
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            log.debug("映射不是一个方法");
            return true;
        }

        String requestId = request.getSession().getId();
        log.info("requestId="+requestId+" requestURL:"+request.getRequestURL());
        log.info("requestId="+requestId+" requestURI:"+request.getRequestURI());
        log.info("requestId="+requestId+" requestMethod:"+request.getMethod());
        Enumeration<String> headersKey = request.getHeaderNames();
        while (headersKey.hasMoreElements()){
            String k = headersKey.nextElement();
            log.info("requestId="+requestId+" requestHeader: " + k + "=="+request.getHeader(k));
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 跨域问题解决
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Auth, Platform, App-Version, Token, token");

        return true;
    }
}

