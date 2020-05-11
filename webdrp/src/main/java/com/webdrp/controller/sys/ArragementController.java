package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.constant.AgentType;
import com.webdrp.entity.Application;
import com.webdrp.entity.Provider;
import com.webdrp.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "后台接口 供应商")
@RequestMapping("/sys/agent")
public class ArragementController extends BaseController<Provider,Integer>  {

    @Autowired
    ProviderService providerService;
    /**
     * 抽象业务类  T 实体对象   ID为
     *
     * @return
     */
    @Override
    public BaseService<Provider> getService() {
        return providerService;
    }

    @ApiOperation("供应商类型")
    @GetMapping("/types")
    public Result types(@RequestHeader("token") String token){
        return Result.success(AgentType.map);
    }


    @ApiOperation("获取省市供应商,出账单选择框用")
    @GetMapping("/getAgentListCityAndProvince")
    public Result getAgentListCityAndProvince(@RequestHeader("token") String token){
        List<Provider> list = providerService.getAgentListCityAndProvince();
        return Result.success(list);
    }


}
