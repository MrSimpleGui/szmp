package com.webdrp.annotation;

import java.lang.annotation.*;

/**
 * Created by Macx on 2017/7/2.
 */
//微信  支付请求
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WechatRequest1 {

    String value() default "微信登录注解";
}
