package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Result;
import com.webdrp.entity.Member;
import com.webdrp.entity.SignIn;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.SignInService;
import com.webdrp.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:54 2020-02-21
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "ToC 签到相关")
@RestController
@RequestMapping("/wechat/api")
public class CSignInController {

    @Autowired
    SignInService signInService;

    @ApiOperation("签到")
    @WechatRequest
    @PostMapping("/signIn")
    public Result signIn(@PBUserAnnotation Member member) throws Exception {
        int count = signInService.findByRichUserIdCount(member.getId());
        if (count != 0){
            SignIn signInLastTime = signInService.findUserLastTime(member.getId());
            if (DateUtils.dateToYYYYMMDD().equals(DateUtils.dateToYYYYMMDD(signInLastTime.getDate()))){
                return Result.fail("您今天已经签过到,请明天再签",400);
            }
        }
        SignIn signIn = new SignIn();
        signIn.setDate(DateUtils.dateToStringYyyyMMddHHmmss(new Date()));
        signIn.setUserId(member.getId());
        signIn.setNickName(member.getNickname());
        signInService.add(signIn);
        return Result.success();
    }

    @SystemControllerLog(description = "签到数据")
    @WechatRequest
    @GetMapping("/signInData")
    public Result getSignData(@PBUserAnnotation Member member){
        List<SignIn> signIns = signInService.findByRichUserId(member.getId());

        boolean hasSign = false;
        if (signIns.size() > 0){
            SignIn item = signInService.findUserLastTime(member.getId());
            if (DateUtils.dateToYYYYMMDD().equals(DateUtils.dateToYYYYMMDD(item.getDate()))){
                hasSign = true;
            }
        }
        return Result.success().addAttribute("list",signIns).addAttribute("size",signIns.size()).addAttribute("hasSign",hasSign);
    }




}
