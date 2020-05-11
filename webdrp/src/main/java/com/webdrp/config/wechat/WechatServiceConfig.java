package com.webdrp.config.wechat;

import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.cache.MemcacheCacheStorager;
import com.foxinmy.weixin4j.cache.RedisCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * 微信公众号接口配置
 * Created by hello on 2017/6/22.
 */
@Component
@PropertySource("classpath:weixin4j.properties")
public class WechatServiceConfig {

    @Autowired
    JedisPool jedisPool;

    @Value("${weixin4j.server.prd.app.id}")
    String appid;


    //授权用，小野云商
    @Bean(name = "oneWechatService")
    public WeixinProxy getOneWechatService(){
        RedisCacheStorager redisCacheStorager1 = new RedisCacheStorager(jedisPool);

        return new WeixinProxy(new WeixinAccount(WechatConfig.oneAppId,WechatConfig.oneSecret), redisCacheStorager1);
    }

    // 支付使用小野云商调起支付
    @Bean(name = "wechatXyPayService")
    public WeixinPayProxy getWechatXyPayService(){
        return new WeixinPayProxy(new WeixinPayAccount(WechatConfig.oneAppId,WechatConfig.onePaySign,WechatConfig.oneMerchantID));
    }

    // 授权API
    @Bean(name = "oauthApi1")
    public OauthApi getOauthApi1(){
        return getOneWechatService().getOauthApi();
    }


//    @Bean(name = "wechatCompanyService")
//    public com.foxinmy.weixin4j.qy.WeixinProxy getWechatCompanyService(){
//        return new com.foxinmy.weixin4j.qy.WeixinProxy();
//    }

    //支付用 O斗小野民宿
//    @Bean(name = "wechatService")
//    public WeixinProxy getWechatService(){
//        RedisCacheStorager redisCacheStorager = new RedisCacheStorager(jedisPool);
//        return new WeixinProxy(new WeixinAccount("wx1d9f3a76ae4bcc3b","8bc46bfa563a6ee13cb535995da02aa1"),redisCacheStorager);
//    }
//
//    // 支付使用O斗小野民宿调起支付
//    @Bean(name = "wechatPayService")
//    public WeixinPayProxy getWechatPayService(){
//        return new WeixinPayProxy();
//    }
//
//    // 支付
//    @Bean(name = "oauthApi")
//    public OauthApi getOauthApi(){
//        return getWechatService().getOauthApi();
//    }


}
