package com.webdrp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.jssdk.JSSDKAPI;
import com.foxinmy.weixin4j.jssdk.JSSDKConfigurator;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.type.TicketType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.ListsuffixResultDeserializer;
import com.webdrp.common.SaoBeiKey;
import com.webdrp.constant.CommodityType;
import com.webdrp.constant.OrderStatus;
import com.webdrp.entity.Member;
import com.webdrp.entity.User;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.OrderMapper;
import com.webdrp.service.PaymentService;
import com.webdrp.service.PropcardService;
import com.webdrp.service.VipCardService;
import com.webdrp.util.saobei.MD5;
import com.webdrp.util.saobei.OrderResponse;
import com.webdrp.util.saobei.PosPrepayRe;
import com.webdrp.util.saobei.PrePayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * 支付业务接口实现类
 * Created by hello on 2017/8/30.
 */
@Service
@PropertySource("classpath:weixin4j.properties")
public class PaymentServiceImpl implements PaymentService {


    @Value("${wechat.auth.url}")
    private String domain;

    @Value("${wechat.pay.notify}")
    private String notifyUrl;

    /**
     * 小野云商通知URL
     */
    @Value("${wechat.pay.notify2}")
    private String notifyUrlYs;



    @Value("${wechat.pay.saoBeiNotify}")
    private String saoBeiNotify;



    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeixinPayProxy wechatPayService;

    @Autowired
    @Qualifier("wechatXyPayService")
    private WeixinPayProxy wechatXyPayService;

    @Autowired
    private WeixinProxy wechatService;

    /**
     * 小野云商的
     */
    @Autowired
    private WeixinProxy oneWechatService;



    @Autowired
    PayIncomeAndUserV10UpGradeService payIncomeAndUserV10UpGradeService;
    /**
     * spring 线程池
     */
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    VipCardService vipCardService;

    @Autowired
    private PropcardService propcardService;

    /**
     * 微信创建订单
     * @param order  订单信息
     * @param realIp
     * @param user
     * @return 支付请求接口响应
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public MchPayRequest placeOrder(com.webdrp.entity.Order order, String realIp, Member user) throws WeixinException {
        MchPayRequest mchPayRequest = null;
            if(Objects.nonNull(order)||Objects.nonNull(user)||!realIp.isEmpty()){
                //判断是不是继续支付
                    if (!StringUtils.isEmpty(order.getWechatOrderId())){
                        this.closeOrder(order);
                        logger.debug("关闭旧的订单！订单号为："+order.getWechatOrderId());
                    }
                    //创建支付订单
                    com.webdrp.entity.Order temp = orderMapper.queryById(order.getId());
                    String wehcatOrderNo =  this.generateOutTradeNo(temp.getId());
                    mchPayRequest = wechatPayService.createJSPayRequest(user.getOpenid(), temp.getName(), wehcatOrderNo, temp.getSubPrice(),notifyUrl, realIp,order.getId().toString());
                    temp.setWechatOrderId(wehcatOrderNo);
                    orderMapper.updateWechatOrderId(temp);

            }else{
                logger.error("订单/用户/绝对IP为空");
                throw new BusinessException("订单/用户/绝对IP为空");
            }
            return mchPayRequest;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public MchPayRequest placeOrderYS(com.webdrp.entity.Order order, String realIp, Member user) throws WeixinException {
        MchPayRequest mchPayRequest = null;
            if(Objects.nonNull(order)||Objects.nonNull(user)||!realIp.isEmpty()){
                //判断是不是继续支付
                    if (!StringUtils.isEmpty(order.getWechatOrderId())){
                        this.closeOrder(order);
                        logger.debug("关闭旧的订单！订单号为："+order.getWechatOrderId());
                    }
                    //创建支付订单
                    com.webdrp.entity.Order temp = orderMapper.queryById(order.getId());
                    String wehcatOrderNo =  this.generateOutTradeNo(temp.getId());
                    mchPayRequest = wechatXyPayService.createJSPayRequest(user.getOpenid1(), temp.getName(), wehcatOrderNo, temp.getSubPrice(),notifyUrlYs, realIp,order.getId().toString());
                    temp.setWechatOrderId(wehcatOrderNo);
                    orderMapper.updateWechatOrderId(temp);

            }else{
                logger.error("订单/用户/绝对IP为空");
                throw new BusinessException("订单/用户/绝对IP为空");
            }
            return mchPayRequest;
    }

    /**
     * 第三方创建订单
     *
     * @param order
     * @return
     * @throws WeixinException
     */
    @Override
    public PosPrepayRe placeOrderSaoBei(com.webdrp.entity.Order order) {
        //创建支付订单
        com.webdrp.entity.Order temp = orderMapper.queryById(order.getId());
        String wehcatOrderNo =  this.generateOutTradeNo(temp.getId());
        temp.setWechatOrderId(wehcatOrderNo);
        orderMapper.updateWechatOrderId(temp);
        PosPrepayRe re = PrePayUtil.posPrepayReByOrder(temp,saoBeiNotify);
        return re;
    }

    /**
     * 获取JSSDK的配置信息
     * @param requestURI 请求路径的URI
     * @param jssdkapis  需要的微信权限
     * @return JSSDK JSON格式配置信息
     */
    @Override
    public String jssdkConfig(String requestURI, JSSDKAPI... jssdkapis) {
        logger.debug(requestURI);
        JSSDKConfigurator jssdk = new JSSDKConfigurator(wechatService.getTicketManager(TicketType.jsapi));
        for (JSSDKAPI j :jssdkapis) {
            jssdk.apis(j);
        }
        //打开调试模式
//        jssdk.debugMode();
        String config = null; // 生成json串
        try {
            String url = domain + requestURI;
            config = jssdk.toJSONConfig(url);
            config = JSONObject.parse(config).toString();
            logger.debug("generate js sdk config success : " + config);
        } catch (WeixinException e) {
            e.printStackTrace();
            logger.error("generate js sdk config error : " + config);
        }
        return config;
    }

    /**
     * 小野云商的
     *
     * @param requestURI
     * @param jssdkapis
     * @return
     */
    @Override
    public String jssdkConfig2(String requestURI, JSSDKAPI... jssdkapis) {
        logger.debug(requestURI);
        JSSDKConfigurator jssdk = new JSSDKConfigurator(oneWechatService.getTicketManager(TicketType.jsapi));
        for (JSSDKAPI j :jssdkapis) {
            jssdk.apis(j);
        }
        //打开调试模式
//        jssdk.debugMode();
        String config = null; // 生成json串
        try {
            String url = domain + requestURI;
            config = jssdk.toJSONConfig(url);
            config = JSONObject.parse(config).toString();
            logger.debug("generate js sdk config success : " + config);
        } catch (WeixinException e) {
            e.printStackTrace();
            logger.error("generate js sdk config error : " + config);
        }
        return config;
    }

    /**
     * 微信订单支付回调
     * @param input 回调输入流
     * @return 用户支付结果
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public XmlResult payNotify(InputStream input) throws IOException {
        XmlResult xmlResult = null;
        String content = StringUtil.newStringUtf8(IOUtil.toByteArray(input));
        Order order = ListsuffixResultDeserializer.deserialize(content,Order.class);
        //验证签名
        String sign = order.getSign();
        String valid_sign = wechatPayService.getWeixinSignature().sign(order);
        logger.info("微信签名:sign={},vaild_sign={}", sign, valid_sign);
        if (!sign.equals(valid_sign)) {
            xmlResult = new XmlResult(Consts.FAIL, "签名错误");
            logger.debug("pay notify sign check error");
            logger.error("微信支付签名错误！");
        }
        // 如果支付结果成功
        if (order.getReturnCode().equals("SUCCESS")){
            String orderInfoIdStr = order.getAttach();
            int orderInfoId = Integer.parseInt(orderInfoIdStr);
            com.webdrp.entity.Order orderinfo = orderMapper.findById(orderInfoId);
            if (order.getResultCode().equals("SUCCESS")) {
                logger.debug("支付通知签名成功 pay notify code check success");
                // 更新状态为已支付
                if (orderinfo != null && orderinfo.getWechatOrderId().equals(order.getOutTradeNo()) &&
                        Math.abs(orderinfo.getSubPrice() - order.getFormatTotalFee()) < 0.1d) {
                    // 防止重复的通知
                    if (orderinfo.getStatus().intValue() == OrderStatus.TODO.intValue()||orderinfo.getStatus().intValue() == OrderStatus.YES.intValue()) {
                        // 更新订单状态
                        orderinfo.setStatus(OrderStatus.COMFIRM);
                        // 更新订单项状态
                        orderMapper.updateOrderStatus(orderinfo);
                        //如果有道具，更新道具
                        taskExecutor.execute(new PropcardThread(orderinfo, propcardService));
                        taskExecutor.execute(new RebateThread(orderinfo, payIncomeAndUserV10UpGradeService));
                        logger.debug("Order 更新完成");
                    } else {
                        logger.debug("微信支付重复通知！wechat order is checked state : {}", orderinfo.getStatus());
                    }
                }
            }else{
                logger.debug("支付通知签名失败");
            }
            xmlResult = new XmlResult(Consts.SUCCESS, "");
        }else{
            // 如果支付结果失败
            xmlResult = new XmlResult(Consts.FAIL, "参数格式校验错误");
            logger.debug("参数格式校验错误");
        }
        return xmlResult;
    }


    /**
     * 关闭微信订单
     * @param order 订单信息
     * @return 订单关闭结果
     */
    @Override
    public MerchantResult closeOrder(com.webdrp.entity.Order order) throws WeixinException {
        return wechatPayService.closeOrder(order.getWechatOrderId());
    }

    /**
     * 生成订单号
     * @param orderInfoId 订单
     * @return 订单号
     */
    private String generateOutTradeNo(int orderInfoId){
        return "d" + orderInfoId + "t" + (new Date().getTime());
    }

    private String generateOutTradeNo2(int orderInfoId){
        return  orderInfoId + "" + (new Date().getTime());
    }

    /**
     * 订单退款处理
     * 暂时为人工手动处理
     * @param orderInfo 订单信息
     * @param user      操作人员信息
     * @param refundFee 退款金额
     * @param tips      退款审批备注
     */
    @Override
    public void refund(com.webdrp.entity.Order orderInfo, User user, double refundFee, String tips) {

    }

    /**
     * 微信公众号H5网页对发起支付事件的回调
     * 仅用于更新状态，实际确定按微信支付通知为准
     * @param orderId 订单id
     * @param status      用户支付事件处理结果
     */
    @Override
    public void webPayNotify(Integer orderId, String status) {

    }

    /**
     * 扫呗的支付回调
     *
     * @param re
     */
    @Override
    public void saoBeiNotify(OrderResponse re) {
        logger.debug("扫呗新版本支付回调=========="+re.toString());

        String parm =
//                "pay_ver=" + re.getPay_ver()
                 "pay_type=" + re.getPay_type()
//                + "&service_id="+ re.getService_id()

                + "&terminal_id=" + re.getTerminal_id()
                + "&terminal_trace=" + re.getTerminal_trace()
                + "&terminal_time=" + re.getTerminal_time()
                + "&total_fee=" + re.getTotal_fee()
                + "&merchant_no=" + re.getMerchant_no()
                + "&attach=" + re.getAttach()
//					 +"&notify_url="+posPrePay.getNotify_url()
//					 +"&order_body="+posPrePay.getOrder_body()
//					 +"&sub_appid="+posPrePay.getSub_appid()
//					 +"&open_id="+posPrePay.getOpen_id()
                + "&access_token=" +SaoBeiKey.access_token;
        String sign = MD5.sign(parm, "utf-8");
        System.out.println(sign);

        if (re.getTerminal_id().equals(SaoBeiKey.terminal_id) &&re.getMerchant_no().equals(SaoBeiKey.merchant_no)){
            if (re.getReturn_code().equals("01") && re.getResult_code().equals("01")){
                Integer orderId = Integer.parseInt(re.getAttach());

                com.webdrp.entity.Order orderinfo = orderMapper.findByWechatOrderIdAndOrderId(orderId,re.getTerminal_trace());
                if (re.getUser_id().equals(orderinfo.getOpenId())){
                    if (orderinfo.getStatus().intValue() == OrderStatus.TODO.intValue()||orderinfo.getStatus().intValue() == OrderStatus.YES.intValue()) {
                        // 更新订单状态
                        orderinfo.setStatus(OrderStatus.COMFIRM);
                        // 更新订单项状态
                        orderMapper.updateOrderStatus(orderinfo);
                        // 开卡
                        return;
                    }
                }else{
                    logger.error("订单 openID 不正确！");
                }
            }else{
                logger.error("支付返回失败！");
            }
        }else{
            logger.error("支付的账号密码终端号等不对！");
        }
        return ;
    }

    /**
     * 进行返佣金六级分销
     */
    private static class RebateThread implements Runnable {
        private com.webdrp.entity.Order order;
        private PayIncomeAndUserV10UpGradeService payIncomeAndUserV10UpGradeService;

        public RebateThread(com.webdrp.entity.Order order, PayIncomeAndUserV10UpGradeService payIncomeAndUserV10UpGradeService) {
            this.order = order;
            this.payIncomeAndUserV10UpGradeService = payIncomeAndUserV10UpGradeService;
        }
        @Override
        public void run() {
            if ( Objects.nonNull(order)){
                try {
                    payIncomeAndUserV10UpGradeService.payIncomeV10AndUserUpGrade(order);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }
    }

    public static class PropcardThread implements Runnable {
        private com.webdrp.entity.Order order;
        private PropcardService propcardService;

        public PropcardThread(com.webdrp.entity.Order order, PropcardService propcardService) {
            this.order = order;
            this.propcardService = propcardService;
        }
        @Override
        public void run() {
            if ( Objects.nonNull(order)){
                try {
                    propcardService.dealPropcard(order);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }
    }

}
