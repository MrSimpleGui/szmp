package com.webdrp.config.wechat;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.spring.SpringBeanFactory;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信消息处理Netty服务配置
 * Created by hello on 2017/6/22.
 */
//@Component
//@PropertySource("classpath:weixin4j.properties")
public class WechatServerThread implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
    WechatMenuConfig wechatMenuConfig;

    /**
     * 服务监听的端口号,目前微信只支持80端口,可以考虑用nginx做转发到此端口
     */
    private int port;
    /**
     * 服务器token信息
     * 明文模式:String aesToken = ""; 密文模式:AesToken aesToken = new
     * AesToken("公众号appid", "公众号token","公众号加密/解密消息的密钥");
     */
    private AesToken aesToken;

    /**
     * 处理微信消息的全限包名(也可通过addHandler方式一个一个添加)
     */
    private String handlerPackage="com.webdrp.config.wechat.handler";

    /**
     * 用spring去获取bean
     */
    private ApplicationContext applicationContext;

    public WechatServerThread(){}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment environment = applicationContext.getEnvironment();
        this.applicationContext = applicationContext;
        // 设置
        String runModel = environment.getRequiredProperty("wechat.run.model");
        logger.info("wechat server in "+runModel+" environment");
        switch (runModel){
            case "develop":
                this.port = environment.getRequiredProperty("weixin4j.server.port", int.class);
                this.aesToken = new AesToken(
                        environment.getRequiredProperty("weixin4j.server.dev.app.id", String.class),
                        environment.getRequiredProperty("weixin4j.server.dev.app.token", String.class),
                        environment.getRequiredProperty("weixin4j.server.dev.app.aeskey", String.class)
                );
                logger.debug("not update wechat menu in develop model");
                // 开始运行Netty
                this.start();
                break;
            case "product":
                this.port = environment.getRequiredProperty("weixin4j.server.port", int.class);
                this.aesToken = new AesToken(
                        environment.getRequiredProperty("weixin4j.server.prd.app.id", String.class),
                        environment.getRequiredProperty("weixin4j.server.prd.app.token", String.class),
                        environment.getRequiredProperty("weixin4j.server.prd.app.aeskey", String.class)
                );
                if (wechatMenuConfig != null){
                    wechatMenuConfig.updateMenu();
                    logger.debug("update wechat menu success");
                }
                else {
                    logger.error("update wechat menu error");
                }

                // 开始运行Netty
                this.start();
                break;
            default:
                logger.warn("can not found the run model");
                break;
        }
        //this.start();
    }

    private ExecutorService executor;

    /**
     * 启动函数
     */
//    @PostConstruct
    public void start() {
        executor = Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("wechat message netty server start success");
                    // 指定开发者token信息。
                    new WeixinServerBootstrap(aesToken)
                            // 扫描处理消息的包。
                            .handlerPackagesToScan(handlerPackage)
                            .resolveBeanFactory(
                                    // 声明处理消息类由Spring容器去实例化。
                                    new SpringBeanFactory(applicationContext))
                            //.addHandler(DebugMessageHandler.global) // 当没有匹配到消息处理时输出调试信息，开发环境打开。
                            // 当没有匹配到消息处理时输出空白回复(公众号不会出现「该公众号无法提供服务的提示」)，正式环境打开。
                            .openAlwaysResponse()
                            // 绑定服务的端口号，即对外暴露(微信服务器URL地址)的服务端口。
                            .startup(port);
                } catch (Exception e) {
                    InternalLoggerFactory.getInstance(getClass()).error( "wechat message netty server run error ", e);
                }
            }
        });
    }

    @PreDestroy
    public void stop() {
        executor.shutdownNow();
        logger.info("wechat message netty server stop success");
    }
}
