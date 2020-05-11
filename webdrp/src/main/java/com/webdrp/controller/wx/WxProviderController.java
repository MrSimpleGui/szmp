package com.webdrp.controller.wx;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.common.Result;
import com.webdrp.entity.Provider;
import com.webdrp.entity.Member;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.ProviderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商城供应商接口")
@RestController
@RequestMapping("/wechat/v1/arrangement")
public class WxProviderController {

    @Autowired
    ProviderService providerService;

    @SystemControllerLog(description = "/通过ID获取供应商名字")
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer ventorId, @PBUserAnnotation Member member){
        Provider provider = providerService.findOne(ventorId);
        return Result.success(provider);
    }

}
