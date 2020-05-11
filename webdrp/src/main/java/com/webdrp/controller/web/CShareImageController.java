package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Result;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShareImage;
import com.webdrp.service.CommodityService;
import com.webdrp.service.ShareImageService;
import com.webdrp.service.impl.QRServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:58 2020-04-01
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "分享二维码")
@RestController
@RequestMapping("/wechat/api")
public class CShareImageController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    ShareImageService shareImageService;

    @Autowired
    QRServiceImpl qrService;


    @Value("${wechat.product.detail}")
    public String productDetailUrl;

    @WechatRequest
    @ApiOperation("获取产品分享图片")
    @GetMapping("/shareImage")
    public Result getCommodityShareImage(@PBUserAnnotation Member member, @RequestParam Integer commodityId){
        ShareImage shareImage = new ShareImage();
        shareImage.setCommodityId(commodityId);
        shareImage.setMemberId(member.getId());
        ShareImage item = shareImageService.findByMemberIdAndCommodityId(shareImage);
        if (Objects.isNull(item)){
            Commodity commodity = commodityService.findOne(commodityId);
            if (Objects.isNull(commodity)){
                return Result.fail("产品不存在",400);
            }
            String baseImage = commodity.getShareImg();
            if (StringUtils.isEmpty(baseImage)){
                return Result.fail("产品分享图不存在",400);
            }
            String shareImageUrl = productDetailUrl + commodityId + "&tips="+member.getId();
            try {
                String imageName =  qrService.shareImageMerge(baseImage,shareImageUrl,member.getId());
                shareImage.setImageUrl(imageName);
                shareImageService.add(shareImage);
                return Result.success(shareImage);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("系统错误",500);
            }
        }
        return Result.success(item);
    }
}
