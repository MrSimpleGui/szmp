package com.webdrp.config;

import com.webdrp.common.PBKey;
import com.webdrp.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.interfaces.PBEKey;
import java.util.List;

/**
 * Created by yuanming on 2018/8/4.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRichUserInterceptor()).
                addPathPatterns("/pb/**").excludePathPatterns("/pb/user/login","/pb/app/login");
        registry.addInterceptor(getSysUserInterceptor()).
                addPathPatterns("/sys/**")
                .excludePathPatterns("/sys/user/login")
                .excludePathPatterns("/sys/test/**");
        registry.addInterceptor(getWechatOAuthInterceptor()).
                addPathPatterns("/wechat/h5/**").excludePathPatterns("/wechat/h5/download").
                addPathPatterns("/wechat/api/**").
                addPathPatterns("/app/pay/**").   // 支付宝支付测试使用
                addPathPatterns("/wechat/v1/**").
                excludePathPatterns("/wechat/v1/pay/notify","/wechat/v1/systemBusy","/wechat/v1/pay/notifyYs","/wechat/api/login").order(2);// 忽略这个Url
//        registry.addInterceptor(getWechatOAuthInterceptor1()).
//                addPathPatterns("/wechat/v1/pay/**","/wechat/v1/product/pay/**").excludePathPatterns("/wechat/v1/pay/notify","/wechat/v1/pay/saoBeiNotify","/wechat/v1/systemBusy","/wechat/v1/pay/notifyYs");
        //文档密码
        registry.addInterceptor(getDocInterceptor()).
                addPathPatterns("/swagger-ui.html");

        // 全局日志处理器
        registry.addInterceptor(getRequestLogInterceptor()).addPathPatterns("/**").order(1);
        //供应商接口
        registry.addInterceptor(getAgentInterceptor()).addPathPatterns("/agent/**").excludePathPatterns("/agent/user/login");
    }
    public RequestLogInterceptor getRequestLogInterceptor(){
        return new RequestLogInterceptor();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //公共微信注解
        resolvers.add(new PBUserAnnotationImpl());     //手机版本用户注解
        resolvers.add(new SysUserAnnotationImpl());    //后台管理用户注解
        resolvers.add(new AgentUserAnnotationImpl());  //供应商注解
    }
    //处理跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**")
                   .allowedOrigins("*")
                   .allowedMethods("GET","POST","OPTIONS")
                   .allowedHeaders("token","auth","agentauth")
                   // APP 端请求头
                   .allowedHeaders(PBKey.PLATFORM,PBKey.APP_VERSION,PBKey.AUTH)
                   .allowCredentials(true)
                   .maxAge(36000);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/qr_code/**").addResourceLocations("file:classpath:qr_images");

    }

    @Bean
    public RichUserInterceptor getRichUserInterceptor(){
        return new RichUserInterceptor();
    }
    @Bean
    public SysUserInterceptor getSysUserInterceptor(){
        return new SysUserInterceptor();
    }
    @Bean
    public DocInterceptor getDocInterceptor(){
        return new DocInterceptor();
    }

    @Bean
    public WechatOAuthInterceptor getWechatOAuthInterceptor(){
        return new WechatOAuthInterceptor();
    }

//    @Bean
//    public WechatOAuthInterceptor1 getWechatOAuthInterceptor1(){
//        return  new WechatOAuthInterceptor1();
//    }


    @Bean
    public AgentInterceptor getAgentInterceptor(){
        return new AgentInterceptor();
    }

}
