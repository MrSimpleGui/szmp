package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.entity.Image;
import com.webdrp.service.ImageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuanming on 2018/8/14.
 */
@Api(tags = "后台接口系统端图片接口（未测试）")
@RestController
@RequestMapping("/sys/image")
public class ImageController extends BaseController<Image,Integer>{

    @Autowired
    ImageService imageService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Image> getService() {
        return imageService;
    }
}
