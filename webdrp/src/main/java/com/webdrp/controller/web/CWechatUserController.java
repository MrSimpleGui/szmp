package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.PBKey;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Grade;
import com.webdrp.entity.Income;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.PropcardVo;
import com.webdrp.err.BusinessException;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.GradeService;
import com.webdrp.service.IncomeService;
import com.webdrp.service.MemberService;
import com.webdrp.service.PropcardService;
import com.webdrp.util.Sha256decode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 00:34 2020-02-20
 * @mail: zh878998515@gmail.com
 * @Description:团队
 */
@RestController
@RequestMapping("/wechat/api")
public class CWechatUserController {

    @Autowired
    MemberService memberService;

    @Autowired
    private PropcardService propcardService;

    @ApiOperation("我的团队查询 type类型  1一级  2二级 3 三级 4团队")
    @WechatRequest
    @GetMapping(value = "/teamList")
    public Result myTeamUser(@PBUserAnnotation Member member, Pager pager, @RequestParam Integer type){

        switch (type){
            case 4:
                return Result.success().addAttribute("list", memberService.findTeamUserByPage(member.getId(),pager)).addAttribute("pager",pager);
            case 3:
                return Result.success().addAttribute("list", memberService.findThird(member.getId(),pager)).addAttribute("pager",pager);
            case 2:
                return Result.success().addAttribute("list", memberService.findSecond(member.getId(),pager)).addAttribute("pager",pager);
            case 1:
                return Result.success().addAttribute("list", memberService.findFirst(member.getId(),pager)).addAttribute("pager",pager);
            default:
                return Result.success().addAttribute("list", memberService.findFirst(member.getId(),pager)).addAttribute("pager",pager);
        }
    }

    @ApiOperation("设置用户名密码")
    @WechatRequest
    @PostMapping(value = "/setting")
    public Result setting(HttpSession session, @RequestParam String username, @RequestParam String password, String nickName){
        Member member = (Member)session.getAttribute(PBKey.PBLOGINKEY);
        // 去重复
        username = username.trim();
        // 设置密码
        password = Sha256decode.getSHA256Str(password);
        // 先查这个用户名有没被用过
        Member item = memberService.findOne(member.getId());
        if (Objects.nonNull(item.getUsername())&& (!item.getUsername().equals(username))){
            Member exit = memberService.findByUserName(username);
            if (Objects.nonNull(exit)){
                return Result.fail("用户名已被注册",400);
            }
        }
        item.setUsername(username);
        item.setPassword(password);
        memberService.updateUserNameAndPassword(item);
       return Result.success();
    }

    @ApiOperation("设置密码")
    @WechatRequest
    @PostMapping(value = "/settingPassword")
    public Result setting(HttpSession session, @RequestParam String password, String nickName){
        Member member = (Member)session.getAttribute(PBKey.PBLOGINKEY);
        // 设置密码
        password = Sha256decode.getSHA256Str(password);
        // 先查这个用户名有没被用过
        Member item = memberService.findOne(member.getId());
        item.setPassword(password);
        memberService.updateUserNameAndPassword(item);
        return Result.success();
    }


    @ApiOperation("我的团队数据分析")
    @SystemControllerLog(description = "我的团队统计数据")
    @WechatRequest
    @GetMapping("/teamAnalyse")
    public Result teamAnalyse(@PBUserAnnotation Member richUser){

        long first = memberService.countFirst(richUser.getId());
        long second = memberService.countSecond(richUser.getId());
        long third = memberService.countThird(richUser.getId());
        long team = memberService.findTeamUserCount(richUser);

        return Result.success().addAttribute("first",first).addAttribute("second",second).addAttribute("third",third).addAttribute("team",team);

    }

    @ApiOperation("获取用户信息")
    @SystemControllerLog(description = "获取用户信息")
    @WechatRequest
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@PBUserAnnotation Member richUser){
        Member item = memberService.findOne(richUser.getId());
        return Result.success().addAttribute("user",item);
    }

    @Autowired
    GradeService gradeService;

    @Autowired
    IncomeService incomeService;

    @ApiOperation("获取用户信息Plus")
    @SystemControllerLog(description = "获取用户信息Plus")
    @WechatRequest
    @GetMapping("/getUserInfoPlus")
    public Result getUserInfoPlus(@PBUserAnnotation Member richUser){
        Member item = memberService.findOne(richUser.getId());
        Grade grade = gradeService.findByRank(item.getGrade());
        // 今日收入
        Income income = new Income();
        income.setTargetRichUserId(richUser.getId());
        double sumIncome = incomeService.sumUserIncome(richUser.getId());
        return Result.success().addAttribute("user",item)
                .addAttribute("grade",grade)
                .addAttribute("dayIncome",incomeService.sumDayIncome(income))
                .addAttribute("sumIncome",sumIncome);
    }

    @ApiOperation("登录接口")
    @SystemControllerLog(description = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password){
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        String token = memberService.login(member);
        return Result.success().addAttribute("token",token);
    }

    @ApiOperation("改名")
    @SystemControllerLog(description = "改名")
    @WechatRequest
    @GetMapping("/rename")
    public Result rename(@PBUserAnnotation Member richUser, @RequestParam String nickname){
        if(Objects.isNull(nickname)){
            throw new BusinessException("缺少参数");
        }
        richUser.setNickname(nickname);
        propcardService.rename(richUser);
        return Result.success();
    }

    @ApiOperation("改上级")
    @SystemControllerLog(description = "改上级")
    @WechatRequest
    @GetMapping("/resuper")
    public Result resuper(@PBUserAnnotation Member richUser, @RequestParam Integer topId){
        if(Objects.isNull(topId)){
            throw new BusinessException("缺少参数");
        }
        propcardService.resuper(richUser, topId);
        return Result.success();
    }

    @ApiOperation("获取功能道具")
    @SystemControllerLog(description = "获取功能道具")
    @WechatRequest
    @GetMapping("/findPropcard")
    public Result findPropcard(@PBUserAnnotation Member richUser){
        List<PropcardVo> list =  propcardService.findPropcard(richUser);
        return Result.success().addAttribute("list",list);
    }

}
