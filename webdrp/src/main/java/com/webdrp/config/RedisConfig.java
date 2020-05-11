package com.webdrp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;


/**
 * Created by yuanming on 2018/8/4.
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.database}")
    private int database;



    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;

    // 日志
    private Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public JedisPool redisPoolFactory()  throws Exception {
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
        return jedisPool;
    }


    /**
     * 配置RedisTemplate
     * 是为了替换默认的JDK的序列化器,使用默认的序列化器,key会乱码;
     *
     * 此处在Spring中的实现是,他有一个默认的RedisTemplate Bean,但使用了
     * @ConditionalOnMissingBean(type = RedisTemplate.class)这样一个注解,
     * 表示在我们没有配置自定义的bean的情况下,才使用它默认的bean
     */
    @Bean
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<Object,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return  redisTemplate;
    }





    //静态注入缓存
    @Autowired
    public void setRedisTemplate(RedisTemplate<Object,Object> redisTemplate) {
        MybatisRedisCache.setJedisConnectionFactory(redisTemplate);
    }
}