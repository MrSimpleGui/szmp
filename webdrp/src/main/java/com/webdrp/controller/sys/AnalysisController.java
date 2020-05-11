package com.webdrp.controller.sys;

import com.webdrp.common.Result;
import com.webdrp.constant.AnalysisType;
import com.webdrp.constant.RecordStatus;
import com.webdrp.entity.Analysis;
import com.webdrp.entity.Member;
import com.webdrp.service.*;
import com.webdrp.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yuanming on 2018/8/16.
 */
@Api(tags = "后台 统计相关接口")
@RestController
@RequestMapping("/sys/analysis")
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    @Autowired
    MemberService memberService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    RecordService recordService;

    @Autowired
    OrderService orderService;

    @ApiOperation("获取某一天微信24小时内的新用户数据统计")
    @GetMapping("/newWechatUser")
    public Result findWechatNewUserCount(@RequestHeader(value = "token") String token,String date){
        Analysis analysis = new Analysis();
        if (date==null){
            date = DateUtils.getLastDateYYYYMMDD();
        }
        analysis.setDate(date);
        analysis.setaType(AnalysisType.RICHUSER_DATE_COUNT);
        List<Analysis> list = analysisService.findAll(analysis);
        return Result.success(list);
    }

    @ApiOperation("获取某一天微信24小时内的访问用户量数据统计")
    @GetMapping("/logWechatUser")
    public Result findLogWechatNewUserCount(@RequestHeader(value = "token") String token,String date){
        Analysis analysis = new Analysis();
        if (date==null){
            date = DateUtils.getLastDateYYYYMMDD();
        }
        analysis.setDate(date);
        analysis.setaType(AnalysisType.RICHUSER_DATE_COUNT_ACTIVE);
        List<Analysis> list = analysisService.findAll(analysis);
        return Result.success(list);
    }


    @ApiOperation("当前微信用户总数量")
    @GetMapping("/sumWechatUser")
    public Result sumWechatUser(){
        return Result.success(memberService.count(new Member()));
    }

    @ApiOperation("当前已经分佣金总数,包括提现和未提现")
    @GetMapping("/sumIncome")
    public Result sumIncome(){
        return Result.success(incomeService.sumIncome());
    }

    @ApiOperation("当前已提现already，提现中waitRecord，提现失败error")
    @GetMapping("/sumRecord")
    public Result sumRecord(){
        Double todo = recordService.sumRecord(RecordStatus.TODO);
        Double yes = recordService.sumRecord(RecordStatus.YES);
        Double error = recordService.sumRecord(RecordStatus.ERROR);
        return Result.success().addAttribute("waitRecord",todo).addAttribute("already",yes).addAttribute("error",error);
    }


    @ApiOperation("统计顶层发码人员")
    @GetMapping("/sumCountZidIsZero")
    public Result sumCountZidIsZero(){

        return Result.success();
    }


}
