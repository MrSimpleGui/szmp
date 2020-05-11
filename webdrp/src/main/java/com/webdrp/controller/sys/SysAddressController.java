package com.webdrp.controller.sys;

import com.webdrp.common.Result;
import com.webdrp.entity.AddressConfig;
import com.webdrp.service.AddressConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "后台管理地址相关")
@RestController
@RequestMapping("/sys/address")
public class SysAddressController {

    @Autowired
    AddressConfigService addressConfigService;

    @ApiOperation(value = "根据Pcode获取地址表",notes = "传0能拿到全部省份的，然后传省份的进来能拿到省下面的市，传市进来能拿到区，特殊的 四大特区都是市，没有省")
    @GetMapping("/findAllByPcode")
    public Result getAddressByCode(@RequestHeader("token")String token, @RequestParam String pcode){
        AddressConfig addressConfig = new AddressConfig();
        addressConfig.setPcode(pcode);
        return Result.success().addAttribute("list",addressConfigService.findAll(addressConfig));
    }
}
