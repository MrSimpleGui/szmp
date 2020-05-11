package com.webdrp.controller.sys;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.SysUserAnnotation;
import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.constant.AgentIncomeType;
import com.webdrp.entity.AgentIncome;
import com.webdrp.entity.User;
import com.webdrp.service.AgentIncomeService;
import com.webdrp.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "后台 agent端的收入")
@RestController
@RequestMapping("/sys/agent/income")
public class SysAgentIncomeController extends BaseController<AgentIncome,Integer> {

    @Autowired
    AgentIncomeService agentIncomeService;

    /**
     * 抽象业务类  T 实体对象 ID为
     * @return
     */
    @Override
    public BaseService<AgentIncome> getService() {
        return agentIncomeService;
    }

    @Override
    @ApiOperation("单个添加 废弃")
    public Result add(@RequestHeader(value="token") String token, AgentIncome agentIncome){
        return Result.fail("错误",200);
    }

    @ApiOperation("添加")
    @PostMapping("/insert")
    public Result insert(@RequestHeader(value="token") String token, @SysUserAnnotation User user, AgentIncome agentIncome) throws Exception {
        agentIncome.setUserId(user.getId());
        agentIncome.setUserName(user.getUsername());
        agentIncomeService.add(agentIncome);
        return Result.success();
    }

    @Override
    public Result delete(@RequestHeader(value="token") String token,@RequestParam Integer id) {
        AgentIncome agentIncome = new AgentIncome();
        agentIncome.setId(id);
        agentIncome.setDeleteTime(DateUtils.dateToString(new Date()));
        //假删除
        agentIncomeService.delete(agentIncome);
        return Result.success();
    }


    @ApiOperation("供应商收入类型,key-value")
    @GetMapping("/agentIncomeTypes")
    public Result agentIncomeTypes(@RequestHeader(value="token") String token){
        return Result.success(AgentIncomeType.map);
    }

}
