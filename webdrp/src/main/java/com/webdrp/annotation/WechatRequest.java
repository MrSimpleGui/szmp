package com.webdrp.annotation;

import java.lang.annotation.*;

/**
 * Created by Macx on 2017/7/2.
 */
//微信请求
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WechatRequest {

    String value() default "微信登录注解";
}
