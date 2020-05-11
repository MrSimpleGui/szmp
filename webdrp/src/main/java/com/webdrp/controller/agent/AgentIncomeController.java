package com.webdrp.controller.agent;

import com.webdrp.annotation.AgentUserAnnotation;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.AgentIncomeType;
import com.webdrp.entity.AgentIncome;
import com.webdrp.entity.Provider;
import com.webdrp.service.AgentIncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "agent 端获取收入记录")
@RestController
//@RequestMapping("/agent/income")
public class AgentIncomeController{

    @Autowired
    AgentIncomeService agentIncomeService;

    @ApiOperation("通过旅游团ID获取分润")
    @GetMapping("/findByCsid")
    public Result findByCsid(@RequestHeader("agentauth") String token, @AgentUserAnnotation Provider provider, @RequestParam Integer csid){
        AgentIncome agentIncome = new AgentIncome();
        agentIncome.setAgentId(provider.getId());
        agentIncome.setCsid(csid);
        return Result.success(agentIncomeService.findByCsid(agentIncome));
    }

    @ApiOperation("供应商 获取自己收益记录 ")
    @GetMapping("/findByIncomeByPage")
    public Result findByCsid(@RequestHeader("agentauth") String token, @AgentUserAnnotation Provider provider,AgentIncome agentIncome, Pager pager){
        agentIncome.setAgentId(provider.getId());
        List<AgentIncome> list = agentIncomeService.loadAll(agentIncome,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("供应商收入类型,key-value")
    @GetMapping("/agentIncomeTypes")
    public Result agentIncomeTypes(@RequestHeader(value="agentauth") String token,@AgentUserAnnotation Provider provider){
        return Result.success(AgentIncomeType.map);
    }


}
