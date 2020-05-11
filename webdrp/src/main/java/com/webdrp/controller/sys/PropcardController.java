package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.entity.Propcard;
import com.webdrp.err.BusinessException;
import com.webdrp.service.PropcardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Api(tags = "后台 用户道具卡")
@RestController
@RequestMapping("/sys/propcard")
public class PropcardController extends BaseController<Propcard, Integer> {

    @Autowired
    private PropcardService propcardService;

    @Override
    public BaseService<Propcard> getService() {
        return propcardService;
    }

    public Result add(@RequestHeader(value="token") String token, Propcard t)throws Exception {
        if(Objects.isNull(t.getType())){
            throw new BusinessException("请设置类型");
        }
        if(t.getType().equals(0)){
            t.setCount(1);
            t.setName("改名卡");
            t.setCommodityId(225);
            t.setCommodityStyleId(375);
        } else if(t.getType().equals(1)){
            t.setCount(0);
            t.setName("改级卡");
            t.setCommodityId(226);
            t.setCommodityStyleId(374);
        }
        getService().add(t);
        return Result.success();
    }

    public Result delete(@RequestHeader(value="token") String token,Integer id) {
        Propcard propcard = new Propcard();
        propcard.setId(id);
        getService().delete(propcard);
        return Result.success();
    }
}
