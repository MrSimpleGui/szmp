package com.webdrp.controller.agent;

import com.webdrp.annotation.AgentUserAnnotation;
import com.webdrp.common.Result;
import com.webdrp.entity.Provider;
import com.webdrp.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "agent 供应商登录接口")
@RestController
@RequestMapping("/agent/user")
public class AgentUserController {

    @Autowired
    ProviderService providerService;

//    @ApiOperation("agent登录")
//    @PostMapping("/login")
//    public Result agentLogin(String username,String password){
//        return Result.success(providerService.agentLogin(username,password));
//    }
//
//    @ApiOperation("agent获取本人信息")
//    @GetMapping("/getData")
//    public Result getMyData(@AgentUserAnnotation Provider provider,@RequestHeader("agentauth") String token){
//        Provider dbProvider = providerService.findOne(provider.getId());
//        return Result.success(dbProvider);
//    }


}
