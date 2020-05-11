package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.IncomeType;
import com.webdrp.entity.Income;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.IncomeNameVo;
import com.webdrp.entity.vo.MemberSuperiorVo;
import com.webdrp.service.IncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yuanming on 2018/8/14.
 */
@Api(tags = "后台接口系统端 收入记录接口")
@RestController
@RequestMapping("/sys/income")
public class IncomeController extends BaseController<Income,Integer>{

    @Autowired
    IncomeService incomeService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Income> getService() {
        return incomeService;
    }

    @ApiOperation("收入记录类型")
    @GetMapping("/getIncomeType")
    public Result getIncomeType(@RequestHeader(value="token") String token){
        return Result.success(IncomeType.map);
    }

    @ApiOperation("更新收入，接口关闭")
    @Override
    public Result update(String token, Income income) {
        return Result.success();
    }

    @ApiOperation(value = "删除佣金，会减少客户的钱包",notes = "incomeid")
    @PostMapping("/deleteIncome")
    public Result deleteIncome(@RequestParam Integer incomeid){
        Income income = new Income();
        income.setId(incomeid);
        incomeService.deleteUserIncome(income);
        return Result.success();
    }

    @ApiOperation("分页查询所有，返回用户名")
    public Result loadAll(@RequestHeader(value="token") String token, Income income, Pager pager){
        IncomeNameVo incomeNameVo = new IncomeNameVo();
        BeanUtils.copyProperties(income, incomeNameVo);
        List<IncomeNameVo> list = incomeService.loadAll(incomeNameVo, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list", list);
    }

}
