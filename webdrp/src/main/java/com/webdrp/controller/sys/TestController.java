package com.webdrp.controller.sys;


import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.common.Result;
import com.webdrp.entity.*;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.OrderMapper;
import com.webdrp.service.*;
import com.webdrp.service.impl.*;
import com.webdrp.util.CusAccessObjectUtil;
import com.webdrp.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/sys/test")
public class TestController {

    @Autowired
    QRServiceImpl qrService;

    @GetMapping("/mergeIamge")
    public Result shareImage(){
        String baseImage = "http://qn.yywluo.cn/img/FtvTkIGhFi9f61xXT-9mlSadWYqU.jpg";
        String urlAddress = "http://xyrc.yyezl.com/hui/sadaasd";
        Integer memberId = 123;
        try {
            String dd = qrService.shareImageMerge(baseImage,urlAddress,memberId);
            return Result.success(dd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail("错误",500);
    }

    @GetMapping("/test")
    public Result test(){

        return Result.success();
    }

    public static void main(String[] args) {
    }

}
