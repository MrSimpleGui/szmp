package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.entity.ShareImage;
import com.webdrp.service.ShareImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:12 2020-03-24
 * @mail: zh878998515@gmail.com
 * @Description:分享二维码后台管理
 */
@Api(tags = "后台接口 商品分享二维码相关")
@RestController
@RequestMapping("/sys/shareimage")
public class ShareImageController extends BaseController<ShareImage,Integer> {

    @Autowired
    ShareImageService shareImageService;
    /**
     * 抽象业务类  T 实体对象   ID为
     *
     * @return
     */
    @Override
    public BaseService<ShareImage> getService() {
        return shareImageService;
    }



    @ApiOperation("清除所有商品推广二维码")
    @PostMapping("/clearQrCode")
    public Result clearQrCode(@RequestHeader(value="token") String token){
        shareImageService.clearAllQrCode();
        return Result.success();
    }

    @ApiOperation("清除某一个商品推广二维码 id=> 商品ID")
    @PostMapping("/clearOneQrCode")
    public Result clearOneQrCode(@RequestHeader(value="token") String token,@RequestParam Integer id){
        shareImageService.clearOneQrCode(id);
        return Result.success();
    }

}
