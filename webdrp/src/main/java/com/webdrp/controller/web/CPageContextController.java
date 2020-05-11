package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.PBKey;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.*;
import com.webdrp.entity.vo.CommodityVoIndex;
import com.webdrp.err.BusinessException;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.*;
import com.webdrp.service.impl.QRServiceImpl;
import com.webdrp.util.MdRenderUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:36 2020-03-10
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Log4j2
@RestController
@RequestMapping("/wechat/api")
public class CPageContextController {

    @Autowired
    MemberService memberService;

    @Autowired
    GradeService gradeService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    QRServiceImpl qrService;

    @Autowired
    BannerService bannerService;

    @Value("${wechat.tuiguang}")
    private  String sysurl;

    @Autowired
    ConfigService configService;

    @Autowired
    CommodityService commodityService;

    // 轮播图
    @ApiOperation("首页轮播图")
    @WechatRequest
    @GetMapping("/indexBanner")
    public Result indexBanner(@PBUserAnnotation Member member, Pager pager){
        Banner banner = new Banner();
        banner.setMenuId(0);
        List<Banner> list = bannerService.findAll(banner);

        return Result.success().addAttribute("bannerList",list);
    }

    @ApiOperation("首页活动")
    @WechatRequest
    @GetMapping("/indexActivity")
    public Result indexActivity(@PBUserAnnotation Member member, Pager pager){
        Banner banner = new Banner();
        banner.setType(1);
        List<Banner> list = bannerService.findAll(banner);

        return Result.success().addAttribute("activityList",list);
    }

    @ApiOperation("获取活动和轮播")
    @WechatRequest
    @GetMapping("/findBanner")
    public Result findBanner(@PBUserAnnotation Member member, Banner banner){
        if(Objects.isNull(banner)){
            throw new BusinessException("参数不能为空");
        }
        List<Banner> list = bannerService.findAll(banner);
        return Result.success().addAttribute("activityList",list);
    }

    @ApiOperation("首页商品")
    @WechatRequest
    @GetMapping("/indexCommodity")
    public Result indexCommodity(@PBUserAnnotation Member member, Pager pager){
        List<CommodityVoIndex> list = commodityService.findIndexCommodity(pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }


    @SystemControllerLog(description = "推广二维码 app版本")
    @ApiOperation("推广二维码")
    @WechatRequest
    @GetMapping("/myQrCodes")
    public Result myQrCode(Model model, @PBUserAnnotation Member member) throws Exception {
        String url1 = sysurl+"?tips="+member.getId();
        String imageUrl = "";
        member = memberService.findOne(member.getId());
        if(Objects.isNull(member.getOpenidwx())){
            if (member.getGrade().intValue()==0){
                log.error("不升级想获取推广名片richUserId="+member.getId());
            }else{
                Config query = new Config();
                query.setConfigKey(PBKey.BASE_IMAGE);
                Config config = configService.findByKeyOne(query);
                imageUrl = qrService.merge(config.getNote(),url1,member.getId(),member.getNickname(),member.getHeadImg());
                member.setOpenidwx(imageUrl);
                memberService.updateImageUrl(member);
            }
        }
        return Result.success().addAttribute("qrCode",member.getOpenidwx());
    }

    @SystemControllerLog(description = "个人中心")
    @ApiOperation("个人中心")
    @WechatRequest
    @GetMapping("/personals")
    public Result personal(@PBUserAnnotation Member member){
        Result result = Result.success();
        Member member1 = memberService.findOne(member.getId());
        result.addAttribute("user",member1);
        Grade grade = gradeService.findByRank(member1.getGrade());
        result.addAttribute("grade",grade);
        // 今日收入
        Income income = new Income();
        income.setTargetRichUserId(member.getId());
        result.addAttribute("dayIncome",incomeService.sumDayIncome(income));

        double sumIncome = incomeService.sumUserIncome(member.getId());
        result.addAttribute("sumIncome",sumIncome);
        return result;
    }


    @SystemControllerLog(description = "公司简介")
    @ApiOperation("公司简介")
    @WechatRequest
    @GetMapping("/company")
    public Result company(@PBUserAnnotation Member member){
        Config config = new Config();
        config.setConfigKey(PBKey.COMPANY_DESC);
        Config result = configService.findByKeyOne(config);
        // 渲染MD
        result.setNote1(MdRenderUtils.renderMdToHtmlString(result.getNote1()));
        return Result.success(result);
    }





}
