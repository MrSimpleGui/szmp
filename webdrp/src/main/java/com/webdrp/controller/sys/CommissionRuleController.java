package com.webdrp.controller.sys;

import com.webdrp.common.*;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.vo.CommissionRuleVo;
import com.webdrp.service.CommissionRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "后台 分佣规则")
@RestController
@RequestMapping("/sys/commissionRule")
public class CommissionRuleController extends BaseController<CommissionRule, Integer> {

    @Autowired
    private CommissionRuleService commissionRuleService;

    @Override
    public BaseService<CommissionRule> getService() {
        return commissionRuleService;
    }


//
//    @PostMapping("/insert")
//    @ApiOperation("插入分佣规则")
    public Result add(@RequestHeader(value = "token") String token, CommissionRule commissionRule) {
        commissionRuleService.insert(commissionRule);
        return Result.success();
    }
//
//    @PostMapping("/update")
//    @ApiOperation("更新分佣规则")
//    public Result update(@RequestHeader(value = "token") String token, CommissionRule commissionRule) {
//        commissionRuleService.update(commissionRule);
//        return Result.success();
//    }
//
//    @PostMapping("/delete")
//    @ApiOperation("删除分佣规则")
    public Result delete(@RequestHeader(value = "token") String token, Integer id) {
        CommissionRule commissionRule = new CommissionRule();
        commissionRule.setId(id);
        commissionRuleService.delete(commissionRule);
        return Result.success();
    }
//
    @RequestMapping(value = "/findVoByPage", method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有，带角色名")
    @ResponseBody
    public Result loadAll(@RequestHeader(value = "token") String token, CommissionRuleVo commissionRule, Pager pager) {
        List<CommissionRuleVo> list = commissionRuleService.loadAll(commissionRule, pager);
        return Result.success().addAttribute("pager", pager).addAttribute("list", list);
    }


}
