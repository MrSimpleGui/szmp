package com.webdrp.service;

import com.webdrp.entity.Config;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午3:26 2018/8/26
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface ConfigService {

    void updateKefu(Config config);

    Config findKefu(Config config);

    Config findCanPay(Config config);

    /**
     * 根据Key查找一个
     * @param config
     * @return
     */
    Config findByKeyOne(Config config);

    void update(Config config);

    void updateDownload(Config config);

}
