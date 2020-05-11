package com.webdrp.interceptor;

import com.webdrp.annotation.AgentUserAnnotation;
import com.webdrp.common.PBKey;
import com.webdrp.entity.Provider;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

/**
 * Created by yuanming on 2018/8/4.
 */
public class AgentUserAnnotationImpl implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(AgentUserAnnotation.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        Provider provider = null;
        provider = (Provider)nativeWebRequest.getAttribute(PBKey.AGENT_LOGIN_KEY,NativeWebRequest.SCOPE_SESSION);
        if (Objects.nonNull(provider)){
            return provider;
        }else{
            throw  new RuntimeException("用户信息异常");
        }
    }
}
