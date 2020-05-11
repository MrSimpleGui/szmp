package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.entity.CardBag;
import com.webdrp.service.CardBagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "后台 卡包")
@RestController
@RequestMapping("/sys/cardbag")
public class CardBagController extends BaseController<CardBag, Integer> {

    @Autowired
    private CardBagService cardBagService;

    @Override
    public BaseService<CardBag> getService() {
        return cardBagService;
    }

    @ApiOperation("给系统用户初始化卡片")
    @RequestMapping(value = "/initSystemCard",method = RequestMethod.GET)
    @ResponseBody
    public Result initSystemCard(@RequestHeader(value="token") String token){
        CardBag cardBag = new CardBag();
        cardBag.setNumber(1000);
        cardBag.setRichUserId(99998);
        cardBag.beforeCreate();
        try {
            cardBagService.add(cardBag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
