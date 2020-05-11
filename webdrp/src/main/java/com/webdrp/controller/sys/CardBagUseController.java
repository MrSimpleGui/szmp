package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.entity.Cardbaguse;
import com.webdrp.service.CardbaguseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后台 卡包使用记录")
@RestController
@RequestMapping("/sys/cardbaguse")
public class CardBagUseController extends BaseController<Cardbaguse, Integer> {

    @Autowired
    private CardbaguseService cardbaguseService;

    @Override
    public BaseService<Cardbaguse> getService() {
        return cardbaguseService;
    }
}
