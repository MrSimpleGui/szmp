package com.webdrp.controller.sys;

import com.webdrp.common.PBKey;
import com.webdrp.common.Result;
import com.webdrp.entity.Config;
import com.webdrp.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Created by yuanming on 2018/8/22.
 */
@Api(tags = "后台 设置相关接口")
@RestController
@RequestMapping("/sys/setting")
public class OtherSettingController {

    @Autowired
    ConfigService configService;

//    @ApiOperation("设置微信用户下一个ID")
//    @PostMapping("/setAutoInCreate")
//    public Result setAutoInCreate(@RequestParam Integer addnum){
//
//
//        return Result.success();
//    }

    @ApiOperation("设置客服二维码")
    @PostMapping("/setKeFuQrCode")
    public Result setKeFuQrCode(@RequestHeader(value="token") String token,@RequestParam String imageUrl){
        Config config = new Config();
        config.setNote(imageUrl);
        configService.updateKefu(config);
        return Result.success();
    }

    @ApiOperation("查询客服二维码")
    @GetMapping("getKefuQrCode")
    public Result getKefuQrCode(@RequestHeader(value="token") String token){
        Config config = new Config();
        config.setConfigKey(PBKey.KEFU_QRCODE);
        return Result.success(configService.findKefu(config));
    }


    @ApiOperation("设置公司介绍")
    @PostMapping("/setCompany")
    public Result setCompany(@RequestHeader(value="token") String token,Config config){
        if (Objects.isNull(config)){
            return Result.fail("参数不全",400);
        }
        if (Objects.isNull(config.getId())){
            return Result.fail("参数ID不能为空",400);
        }

        if (config.getId().intValue() <= 0){
            return Result.fail("参数ID不能为空1",400);
        }

        config.setConfigKey(PBKey.COMPANY_DESC);
        configService.update(config);
        return Result.success();
    }

    @ApiOperation("获取公司介绍")
    @GetMapping("/getCompanyDescription")
    public Result getCompany(@RequestHeader(value="token") String token){
        Config config = new Config();
        config.setConfigKey(PBKey.COMPANY_DESC);
        return Result.success(configService.findByKeyOne(config));
    }

    @ApiOperation(value = "设置APP下载链接", notes = "val->ios链接，note->android链接，note1->文字，note2->图片")
    @PostMapping("/setDownloadLink")
    public Result setDownloadLink(@RequestHeader(value="token") String token,Config config){
        if(Objects.isNull(config) || (Objects.isNull(config.getVal()) && Objects.isNull(config.getNote()))){
            return Result.fail("参数不全",400);
        }
        config.setConfigKey(PBKey.DOWNLOAD_LINK);
        configService.updateDownload(config);
        return Result.success();
    }

    @ApiOperation(value = "获取APP下载链接", notes = "val->ios链接，note->android链接，note1->文字，note2->图片")
    @GetMapping("/getDownloadLink")
    public Result getDownloadLink(@RequestHeader(value="token") String token){
        Config config = new Config();
        config.setConfigKey(PBKey.DOWNLOAD_LINK);
        return Result.success(configService.findByKeyOne(config));
    }



    @ApiOperation("设置平台推广二维码 note -> 图片链接地址")
    @PostMapping("/setBaseImage")
    public Result setBaseImage(@RequestHeader(value="token") String token, Config config){
        configService.update(config);
        return Result.success();
    }

    @ApiOperation("查询平台推广二维码 note -> 图片链接地址")
    @GetMapping("getBaseImage")
    public Result getBaseImage(@RequestHeader(value="token") String token){
        Config config = new Config();
        config.setConfigKey(PBKey.BASE_IMAGE);
        return Result.success(configService.findByKeyOne(config));
    }

}
