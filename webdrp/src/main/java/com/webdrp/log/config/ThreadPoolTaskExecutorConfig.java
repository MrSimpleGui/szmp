package com.webdrp.log.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午2:49 2018/2/18
 * @mail: zh878998515@gmail.com
 * @Description: 配置线程池
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {

    /**
     * 线程池配置
     * @return
     */
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        return threadPoolTaskExecutor;
    }

}
