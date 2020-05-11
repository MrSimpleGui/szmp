package com.webdrp.controller.web;

import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.PBKey;
import com.webdrp.common.Result;
import com.webdrp.entity.Config;
import com.webdrp.service.ConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 21:28 2020-02-19
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/wechat/api")
public class CCegController {

    @Autowired
    ConfigService configService;

    @ApiOperation("客服联系信息")
    @WechatRequest
    @GetMapping("/kefu")
    public Result kefu(){
        Config config = new Config();
        config.setConfigKey(PBKey.KEFU_QRCODE);
        Config config1 =  configService.findKefu(config);
        return Result.success().addAttribute("kefu",config1.getNote());
    }
}
