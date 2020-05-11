package com.webdrp.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.jssdk.JSSDKAPI;
import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.PBKey;
import com.webdrp.common.Result;
import com.webdrp.entity.*;
import com.webdrp.entity.dto.LogisticsDto;
import com.webdrp.entity.vo.CommodityAllVo;
import com.webdrp.entity.vo.CommodityStyleVo;
import com.webdrp.entity.vo.OrderVo;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.*;
import com.webdrp.service.impl.QRServiceImpl;
import com.webdrp.util.DateUtils;
import com.webdrp.util.DoubleUtil;
import com.webdrp.util.MdRenderUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:05 2020-02-15
 * @mail: zh878998515@gmail.com
 * @Description:2020年第一版本微信webdro项目
 */
@Log4j2
@Controller
@RequestMapping("/wechat/h5")
public class WebTabController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    CommodityStyleService commodityStyleService;

    @Autowired
    MemberService memberService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    GradeService gradeService;

    @Autowired
    ConfigService configService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    QRServiceImpl qrService;

    @Autowired
    OrderService orderService;

    @Autowired
    SignInService signInService;

    @Autowired
    BannerService bannerService;

    @Value("${wechat.tuiguang}")
    private  String sysurl;

    @SystemControllerLog(description = "客服二维码页面")
    @RequestMapping("/ceg")
    public String ceg(Model model){
        Config config = new Config();
        config.setConfigKey(PBKey.KEFU_QRCODE);
        Config config1 =  configService.findKefu(config);
        model.addAttribute("kefu",config1.getNote());
        return "wechat/ceg";
    }

    @SystemControllerLog(description = "收入记录界面")
    @WechatRequest
    @RequestMapping("/income")
    public String income(Model model,@PBUserAnnotation Member member){
        Integer zhitui = memberService.findTeamUserOrderCountFirst(member);
        Integer sumTeam = memberService.findTeamUserOrderCountV1(member);
        model.addAttribute("zhitui",zhitui);
        model.addAttribute("sumTeam",sumTeam);
        return "wechat/income";
    }

    @SystemControllerLog(description = "订单分润")
    @WechatRequest
    @RequestMapping("/orderIncome")
    public String orderIncome(){
        return "wechat/orderIncome";
    }

    @SystemControllerLog(description = "新版首页")
    @WechatRequest
    @RequestMapping("/index")
    public String index(Model model,@PBUserAnnotation Member member){
        Banner banner = new Banner();
        banner.setType(0);
        banner.setMenuId(0);
        List<Banner> list = bannerService.findAll(banner);
        model.addAttribute("bannerList",list);
        // 轮播图 end


        boolean buyOne = false;
        Member item = memberService.findOne(member.getId());
        if (item.getGrade().intValue() > 0){
            buyOne = true;
        }
        model.addAttribute("buyOne",buyOne);

        return "wechatv1/index";
    }

    @SystemControllerLog(description = "新版推广二维码")
    @WechatRequest
    @RequestMapping("/myQrCode")
    public String myQrCode(Model model,@PBUserAnnotation Member member) throws Exception {
        String url1 = sysurl+"?tips="+ member.getId();
        String imageUrl = "";
        member = memberService.findOne(member.getId());
        if(Objects.isNull(member.getOpenidwx())){
            if (member.getGrade().intValue()==0){
                log.error("不升级想获取推广名片richUserId="+ member.getId());
            }else{
                Config query = new Config();
                query.setConfigKey(PBKey.BASE_IMAGE);
                Config config = configService.findByKeyOne(query);
                imageUrl = qrService.merge(config.getNote(),url1, member.getId(), member.getNickname(),member.getHeadImg());
                member.setOpenidwx(imageUrl);
                memberService.updateImageUrl(member);
            }
        }
        model.addAttribute("qrCode", member.getOpenidwx());
        return "wechat/myQrCode";
    }

    @SystemControllerLog(description = "新版订单详情")
    @WechatRequest
    @RequestMapping("/orderDetail")
    public String orderDetail(Model model, HttpServletRequest request, @PBUserAnnotation Member member, @RequestParam Integer id){
        Order order = orderService.findByWechatIdAndOrderId(id, member.getId());
        if (Objects.isNull(order)){
            return "redirect:/wechat/h5/personal";
        }else{
            OrderVo orderVo = orderService.detail(order);
            model.addAttribute("order",orderVo);
        }

        LogisticsDto dto = null;
        if (!StringUtils.isEmpty(order.getWuliuId())){
            try {
                dto = orderService.getLogisticsMsg(order.getWuliuId());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


        String uri = request.getRequestURI();
        if (Objects.nonNull(request.getQueryString()))
            uri+="?"+request.getQueryString();
        //微信配置 给分享朋友圈 分享给朋友接口// 支付
        String config = paymentService.jssdkConfig(uri, JSSDKAPI.onMenuShareTimeline,JSSDKAPI.onMenuShareAppMessage,JSSDKAPI.hideMenuItems,JSSDKAPI.chooseWXPay);
        model.addAttribute("jssdk", JSONObject.parse(config));

        // 物流信息
        if (Objects.nonNull(dto)&&Objects.nonNull(dto.getResult())){
            model.addAttribute("logistics",dto.getResult().getList());
        }else{
            model.addAttribute("logistics",null);
        }

        return "wechat/orderDetail";
    }

    @SystemControllerLog(description = "新版订单列表")
    @WechatRequest
    @RequestMapping("/orderList")
    public String orderList(){
        return "wechat/order";
    }

    @SystemControllerLog(description = "新版个人中心")
    @WechatRequest
    @RequestMapping("/personal")
    public String personal(Model model,@PBUserAnnotation Member member){
        Member member1 = memberService.findOne(member.getId());
        model.addAttribute("user", member1);

        Grade grade = gradeService.findByRank(member1.getGrade());
        model.addAttribute("grade",grade);

        // 今日收入
        Income income = new Income();
        income.setTargetRichUserId(member.getId());
        model.addAttribute("dayIncome",incomeService.sumDayIncome(income));

        double sumIncome = incomeService.sumUserIncome(member.getId());
        model.addAttribute("sumIncome",sumIncome);

        return "wechat/personal";
    }

    @SystemControllerLog(description = "新版预支付")
    @WechatRequest
    @RequestMapping("/prepay")
    public String prepay(HttpServletRequest request, Model model , @PBUserAnnotation Member member, @RequestParam Integer packageId, @RequestParam Integer count){
        CommodityStyle commodityStyle = new CommodityStyle();
        commodityStyle.setId(packageId);
        CommodityStyleVo detail = commodityStyleService.detail(commodityStyle);
        model.addAttribute("package",detail);
        model.addAttribute("count",count);

        Member user = memberService.findOne(member.getId());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("takeName",user.getRealName());
        model.addAttribute("address",user.getAddress());

        // 是否有地址
        boolean hasAddress = true;
        if (StringUtils.isEmpty(user.getPhone())){
            hasAddress = false;
        }
        model.addAttribute("hasAddress",hasAddress);
        // 总价格
        model.addAttribute("subPrice",detail.getPrice()*count);

        boolean noExpress = true;

        Commodity commodity = commodityService.findOne(detail.getCommodityId());
        if (Objects.nonNull(commodity.getExpress())){
            if (commodity.getExpress().doubleValue() > 0){
                noExpress = false;
                model.addAttribute("express",commodity.getExpress());
            }
        }
        model.addAttribute("noExpress",noExpress);

        //配置禁止复制url分享
        String uri = request.getRequestURI();
        if (Objects.nonNull(request.getQueryString()))
            uri+="?"+request.getQueryString();
        //微信配置 给分享朋友圈 分享给朋友接口// 支付
        String config = paymentService.jssdkConfig(uri, JSSDKAPI.onMenuShareTimeline, JSSDKAPI.onMenuShareAppMessage,JSSDKAPI.hideMenuItems,JSSDKAPI.chooseWXPay);
        model.addAttribute("jssdk", JSONObject.parse(config));

        return "wechat/prepay";
    }

    @SystemControllerLog(description = "新版商品详情")
    @WechatRequest
    @RequestMapping("/productDetail")
    public String productDetail(Model model,@RequestParam Integer id){
        CommodityAllVo product = commodityService.findVoById(id);
        model.addAttribute("product",product);
//        最大价格最小价格
        List<Double> list = new ArrayList<>();
        double minPrice = 0.00;
        double maxPrice = 0.00;
        product.getCommodityStyleVoList().forEach(item->{
            list.add(item.getPrice());
        });
        minPrice = Collections.min(list);
        maxPrice = Collections.max(list);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("maxPrice",maxPrice);

        // markDown转换成html
        product.setContext(MdRenderUtils.renderMdToHtmlString(product.getContext()));

        return "wechat/productDetail";
    }

    @SystemControllerLog(description = "新版商品详情V1")
    @WechatRequest
    @RequestMapping("/productDetailV1")
    public String productDetailV1(Model model,@RequestParam Integer id){
        CommodityAllVo product = commodityService.findVoById(id);
        model.addAttribute("product",product);
//        最大价格最小价格
        List<Double> list = new ArrayList<>();
        double minPrice = 0.00;
        double maxPrice = 0.00;
        if (Objects.nonNull(product)){
            product.getCommodityStyleVoList().forEach(item->{
                list.add(item.getPrice());
            });
            minPrice = Collections.min(list);
            maxPrice = Collections.max(list);
            model.addAttribute("minPrice",minPrice);
            model.addAttribute("maxPrice",maxPrice);

            // markDown转换成html
            product.setContext(MdRenderUtils.renderMdToHtmlString(product.getContext()));
            if(Objects.nonNull(product.getService())){
                product.setService(MdRenderUtils.renderMdToHtmlString(product.getService()));
            }
            if(Objects.nonNull(product.getSpecification())){
                product.setSpecification(MdRenderUtils.renderMdToHtmlString(product.getSpecification()));
            }
        }

        return "wechatv1/mallProductDetail";
    }

    @SystemControllerLog(description = "新版返佣金")
    @WechatRequest
    @RequestMapping("/record")
    public String record(@PBUserAnnotation Member member){
        return "wechat/record";
    }

    @SystemControllerLog(description = "新版我的团队")
    @WechatRequest
    @RequestMapping("/team")
    public String team(Model model,@PBUserAnnotation Member member){
        long first = memberService.countFirst(member.getId());
        long second = memberService.countSecond(member.getId());
        long third = memberService.countThird(member.getId());
        long team = memberService.findTeamUserCount(member);

        model.addAttribute("first",first);
        model.addAttribute("second",second);
        model.addAttribute("third",third);
        model.addAttribute("team",team);
        return "wechat/team";
    }

    @SystemControllerLog(description = "新版通知")
    @RequestMapping("/topic")
    public String topic(){
        return "wechat/topic";
    }

    @Autowired
    NotificationService notificationService;

    @SystemControllerLog(description = "通知详情")
    @RequestMapping("/topicDetail")
    public String topicDetail(Model model,@RequestParam Integer id){
        Notification notification = notificationService.findOne(id);
        notification.setContent(MdRenderUtils.renderMdToHtmlString(notification.getContent()));
        model.addAttribute("detail",notification);
        return "wechat/topicDetail";
    }

    @SystemControllerLog(description = "公司简介")
    @RequestMapping("/company")
    public String company(Model model){
         Config config = new Config();
         config.setConfigKey(PBKey.COMPANY_DESC);
         Config result = configService.findByKeyOne(config);
         // 渲染MD
         result.setNote1(MdRenderUtils.renderMdToHtmlString(result.getNote1()));
        model.addAttribute("company",result);
        return "wechat/company";
    }

    @SystemControllerLog(description = "新版发起提现")
    @WechatRequest
    @RequestMapping("/toRecord")
    public String toRecord(@PBUserAnnotation Member member, Model model){
        Member member1 = memberService.findOne(member.getId());
        model.addAttribute("user", member1);
        return "wechat/toRecord";
    }

    @SystemControllerLog(description = "新版设置用户名密码")
    @WechatRequest
    @RequestMapping("/setting")
    public String setting(Model model,@PBUserAnnotation Member member){
        Member item = memberService.findOne(member.getId());
        model.addAttribute("username",item.getUsername());
        return "wechat/setting";
    }

    @SystemControllerLog(description = "下载APP页面")
    @RequestMapping("/download")
    public String download(Model model){
        Config config = new Config();
        config.setConfigKey(PBKey.DOWNLOAD_LINK);
        Config newConfig = configService.findByKeyOne(config);
        model.addAttribute("config",newConfig);
        return "wechat/download";
    }

    @SystemControllerLog(description = "签到页面")
    @WechatRequest
    @RequestMapping("/signIn")
    public String signIn(@PBUserAnnotation Member member, Model model){
        List<SignIn> signIns = signInService.findByRichUserId(member.getId());
        model.addAttribute("list",signIns);
        model.addAttribute("size",signIns.size());
        boolean hasSign = false;
        if (signIns.size() > 0){
            SignIn item = signInService.findUserLastTime(member.getId());
            if (DateUtils.dateToYYYYMMDD().equals(DateUtils.dateToYYYYMMDD(item.getDate()))){
                hasSign = true;
            }
        }
        model.addAttribute("hasSign",hasSign);
        return "wechat/signIn";
    }

     @SystemControllerLog(description = "购物车")
    @WechatRequest
    @RequestMapping("/shoppingCart")
    public String shoppingCart(){
        return "wechat/shoppingCart";
    }

    @Autowired
    ShoppingCartService shoppingCartService;

    @SystemControllerLog(description = "购物车去购买")
    @WechatRequest
    @RequestMapping("/prepayCart")
    public String prepayCart(@PBUserAnnotation Member member,Model model,@RequestParam Integer []ids){
        Member user = memberService.findOne(member.getId());

        // 是否有地址
        boolean hasAddress = true;
        if (StringUtils.isEmpty(user.getPhone())){
            hasAddress = false;
        }

        List<ShoppingCart> list =shoppingCartService.findByCardIds(ids);
        double subPrice = 0.0d;
        for (ShoppingCart item:list){
            subPrice = subPrice + (item.getPrice() * item.getInventory());
        }

        boolean noExpress = true;

            model.addAttribute("packages",list);
            model.addAttribute("phone",user.getPhone());
            model.addAttribute("takeName",user.getRealName());
            model.addAttribute("address",user.getAddress());
            model.addAttribute("hasAddress",hasAddress);
            model.addAttribute("subPrice", DoubleUtil.formatDouble(subPrice));
            model.addAttribute("noExpress",noExpress);
            model.addAttribute("cardIds",ids);
            model.addAttribute("count",ids.length);

        return "wechat/prepayCart";
    }

    @SystemControllerLog(description = "订单物流")
    @WechatRequest
    @RequestMapping("/logisticsDetails")
    public String logisticsDetail(@PBUserAnnotation Member member,Model model,@RequestParam Integer id){
        Order order = orderService.findByWechatIdAndOrderId(id,member.getId());
        if (Objects.isNull(order)){
            return "wechat/index";
        }
        LogisticsDto dto = null;
        if (!StringUtils.isEmpty(order.getWuliuId())){
            try {
                dto = orderService.getLogisticsMsg(order.getWuliuId());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        if (Objects.nonNull(dto)&&Objects.nonNull(dto.getResult())){
            model.addAttribute("logistics",dto.getResult().getList());
        }else{
            model.addAttribute("logistics",dto);
        }
        return "wechat/logisticsDetails";
    }



}
