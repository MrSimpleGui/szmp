package com.webdrp.mapper;

import com.webdrp.entity.Config;
import org.springframework.stereotype.Component;

import java.util.List;

//配置类
@Component
public interface ConfigMapper {

    void insert(Config config);

    void update(Config config);

    List<Config> findAll(Config config);

    List<Config> findByKey(String key);

    void updateKefu(Config config);

    Config findKefu(Config config);

    Config findCanPay(Config config);

    /**
     * 跟据Key查找一个
     * @param config
     * @return
     */
    Config findByKeyOne(Config config);

    void updateDownload(Config config);
}
