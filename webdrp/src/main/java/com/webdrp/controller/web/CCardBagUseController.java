package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Result;
import com.webdrp.entity.CardBag;
import com.webdrp.entity.Cardbaglog;
import com.webdrp.entity.Cardbaguse;
import com.webdrp.entity.Member;
import com.webdrp.service.CardBagService;
import com.webdrp.service.CardbaguseService;
import com.webdrp.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 01:24 2020-04-24
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/wechat/api")
public class CCardBagUseController {

    @Autowired
    CardBagService cardBagService;

    @ApiOperation("使用会员卡")
    @WechatRequest
    @PostMapping("/useCardBag")
    public Result useCardBag(@PBUserAnnotation Member member, @RequestParam String telephone,@RequestParam String ttName){
        Cardbaguse cardbaguse = new Cardbaguse();
        cardbaguse.setPhone(telephone);
        cardbaguse.setName(ttName);
        cardbaguse.setRichUserId(member.getId());
        cardBagService.useCard(cardbaguse);
        return Result.success();
    }

    @Autowired
    MemberService memberService;


    @ApiOperation("送会员卡给别人")
    @WechatRequest
    @PostMapping("/senCardToUser")
    public Result senCardToUser(@PBUserAnnotation Member member, @RequestParam Integer toId){
        if (toId.intValue() == member.getId().intValue()){
            return Result.success();
        }

        Cardbaglog cardbaglog = new Cardbaglog();
        cardbaglog.setNumber(1);
        cardbaglog.setOrignUserId(member.getId());
        cardbaglog.setToUserId(toId);

        Member targetUser =memberService.findOne(toId);
        if (Objects.isNull(targetUser)){
            return Result.fail("赠送失败",400);
        }
        if (targetUser.getGrade().intValue() > 0){
            return Result.fail("赠送失败",400);
        }

        cardBagService.giveCardToUser(cardbaglog);
        return Result.success();
    }


}
