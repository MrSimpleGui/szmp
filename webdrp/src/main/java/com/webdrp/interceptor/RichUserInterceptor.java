package com.webdrp.interceptor;

import com.webdrp.common.PBKey;
import com.webdrp.entity.Member;
import com.webdrp.err.AppLoginTimeoutRuntimeException;
import com.webdrp.err.JSONRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanming on 2018/8/4.
 * token的拦截方案
 */
public class RichUserInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            logger.debug("不是一个映射方法！");
            return true;
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "auth");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        HttpSession session = request.getSession();
        String auth = request.getHeader("auth");
        if (Objects.isNull(auth)){
            throw new JSONRuntimeException("bad request");
        }
        if (auth.isEmpty()){
            //假如是json返回
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            if (Objects.nonNull(responseBody)||Objects.nonNull(postMapping)&&Objects.nonNull(getMapping)){
                throw new JSONRuntimeException("bad request");
            }else{
                response.sendRedirect("http://www.baidu.com");
                return false;
            }
        }

        String []auths = auth.split("\\.");
        if (auths.length != 3){
            throw new JSONRuntimeException("bad request");
        }
        //给迷惑的uuid
       // String uuid = auths[0];
        String usrename = auths[1];
        String sign = auths[2];
        String token = ""+redisTemplate.opsForValue().get(usrename);
        if (token.isEmpty()||"null".equals(token)||token==""||token == null){
            throw new AppLoginTimeoutRuntimeException("登录超时");
        }
        String []tokens = token.split("\\.");
        if (tokens.length == 3){
            if (tokens[1].equals(usrename)&&tokens[2].equals(sign)){
                //获取出用户信息
                ValueOperations<String, Object> operations=redisTemplate.opsForValue();
                //获取用户消息
                Member member = (Member) operations.get(tokens[0]);
                if (Objects.isNull(member)){
                    throw new AppLoginTimeoutRuntimeException("登录超时");
                }else{
                    //刷新redis的存活时间
                    operations.set(tokens[1],token,60*60*24, TimeUnit.SECONDS);
                    operations.set(tokens[0], member,60*60*24, TimeUnit.SECONDS);
                    session.setAttribute(PBKey.PBLOGINKEY, member);
                }
                return true;
            }
            return false;
        }

         return false;
    }
}
