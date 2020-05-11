package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.entity.Cardbaglog;
import com.webdrp.service.CardbaglogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后台 卡包赠送记录")
@RestController
@RequestMapping("/sys/cardbaglog")
public class CardBagLogController extends BaseController<Cardbaglog, Integer> {

    @Autowired
    private CardbaglogService cardbaglogService;

    @Override
    public BaseService<Cardbaglog> getService() {
        return cardbaglogService;
    }
}
