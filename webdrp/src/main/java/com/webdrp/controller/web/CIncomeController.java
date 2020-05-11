package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Income;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.IncomeVo;
import com.webdrp.service.IncomeService;
import com.webdrp.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 21:28 2020-02-19
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@RestController
@RequestMapping("/wechat/api")
public class CIncomeController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    MemberService memberService;

    @ApiOperation("收入记录")
    @WechatRequest
    @GetMapping("/incomeList")
    public Result incomeList(@PBUserAnnotation Member member, Pager pager){
        Income income = new Income();
        income.setTargetRichUserId(member.getId());
        List<IncomeVo> list = incomeService.findMyIncome(income,pager);
        for (int i = 0;i < list.size();i++){
            list.get(i).setOrderId(0);
            list.get(i).setGrade(0);
        }
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("订单收入记录")
    @WechatRequest
    @GetMapping("/orderIncomeList")
    public Result orderIncomeList(@PBUserAnnotation Member member, Pager pager){
        Income income = new Income();
        income.setTargetRichUserId(member.getId());
        List<IncomeVo> list = incomeService.findMyIncome(income,pager);
        for (int i = 0;i < list.size();i++){
            list.get(i).setOrderId(0);
            list.get(i).setGrade(0);
        }
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("收入记录分析")
    @WechatRequest
    @GetMapping("/salesAnalyse")
    public Result salesAnalyse(@PBUserAnnotation Member member){
        Integer zhitui = memberService.findTeamUserOrderCountFirst(member);
        Integer sumTeam = memberService.findTeamUserOrderCountV1(member);
        return Result.success().addAttribute("zhitui",zhitui).addAttribute("sumTeam",sumTeam);
    }


}
