package com.webdrp.service.impl;

import com.webdrp.common.PBKey;
import com.webdrp.entity.Config;
import com.webdrp.mapper.ConfigMapper;
import com.webdrp.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午3:27 2018/8/26
 * @mail: zh878998515@gmail.com
 * @Description:设置文件
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigMapper configMapper;


    @Override
    public void updateKefu(Config config) {
        config.setConfigKey(PBKey.KEFU_QRCODE);
        configMapper.updateKefu(config);
    }

    @Override
    public Config findKefu(Config config) {
        config.setConfigKey(PBKey.KEFU_QRCODE);
        return configMapper.findKefu(config);
    }

    @Override
    public Config findCanPay(Config config) {
        return configMapper.findCanPay(config);
    }

    /**
     * 根据Key查找一个
     *
     * @param config
     * @return
     */
    @Override
    public Config findByKeyOne(Config config) {
        return configMapper.findByKeyOne(config);
    }

    @Override
    public void update(Config config) {
        configMapper.update(config);
    }

    @Override
    public void updateDownload(Config config) {
        configMapper.updateDownload(config);
    }


}
