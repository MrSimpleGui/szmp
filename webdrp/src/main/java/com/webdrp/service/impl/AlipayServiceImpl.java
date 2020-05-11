package com.webdrp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.webdrp.constant.CommodityType;
import com.webdrp.constant.OrderStatus;
import com.webdrp.constant.OrderType;
import com.webdrp.constant.RecordStatus;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Order;
import com.webdrp.entity.OrderDetail;
import com.webdrp.entity.Record;
import com.webdrp.entity.vo.OrderVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.OrderMapper;
import com.webdrp.service.OrderDetailService;
import com.webdrp.service.OrderService;
import com.webdrp.service.PropcardService;
import com.webdrp.util.UUIDUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 上午12:37 2018/8/30
 * @mail: zh878998515@gmail.com
 * @Description:支付宝打款
 */
@Service
public class AlipayServiceImpl {



    @Autowired
    PayIncomeAndUserV10UpGradeService payIncomeAndUserV10UpGradeService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${alipay.appid}")
    private String appid;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Value("${alipay.publickey}")
    private String alipaypublickey;

    @Value("${alipay.privatekey}")
    private String alipayprivatekey;

    //支付宝支付成功后后台通知的URL
    @Value("${alipay.notify.url}")
    private String notifyUrl;

    //支付宝支付成功后重定向的URL
     @Value("${alipay.success.url}")
    private String successUrl;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private PropcardService propcardService;

    private AlipayClient alipayClient;

    public AlipayClient getAlipayClient() throws AlipayApiException {
        String gateway = "https://openapi.alipay.com/gateway.do";
        String charset = "UTF-8";
        String sign_type = "RSA2";
//        String path = "";
//        try{
//            path = ResourceUtils.getURL("classpath:").getPath();
//        } catch(FileNotFoundException e){
//            e.printStackTrace();
//            throw new BusinessException("获取项目路径失败！");
//        }

        String path = System.getProperty("user.dir");
        path = path + "/";
        String app_cert_path = path + "appCertPublicKey.crt";
        String alipay_cert_path = path + "alipayCertPublicKey_RSA2.crt";;
        String alipay_root_cert_path = path + "alipayRootCert.crt";
        if (Objects.isNull(alipayClient)){
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(gateway);
            certAlipayRequest.setAppId(appid);
            certAlipayRequest.setPrivateKey(alipayprivatekey);
            certAlipayRequest.setFormat("json");
            certAlipayRequest.setCharset(charset);
            certAlipayRequest.setSignType(sign_type);
            certAlipayRequest.setCertPath(app_cert_path);
            certAlipayRequest.setAlipayPublicCertPath(alipay_cert_path);
            certAlipayRequest.setRootCertPath(alipay_root_cert_path);
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            setAlipayClient(alipayClient);
        }

        return alipayClient;
    }

    public void setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    /**
     * 支付宝回调订单成功
     * @param trade_status
     * @param out_trade_no
     * @param params
     * @return
     * @throws AlipayApiException
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public boolean nofityOrder(String trade_status,String out_trade_no,Map params) throws AlipayApiException {
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        logger.debug("【nofityOrder】支付宝订单回调");
        boolean verify_result = AlipaySignature.rsaCheckV1(params, alipaypublickey,"utf-8", "RSA2");
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED")){
                logger.debug("【nofityOrder】支付宝返回状态为=TRADE_FINISHED"+trade_status);
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                Order order = orderService.findByzfbOrderId(out_trade_no);
                if (order.getStatus().intValue()==OrderStatus.TODO.intValue()||order.getStatus().intValue()==OrderStatus.YES.intValue()){
                    logger.debug("【nofityOrder】】更新订单的状态为Yes");
                    order.setStatus(OrderStatus.COMFIRM);
                    orderMapper.updateOrderStatus(order);
                    //极差返佣金
                    if (order.getOrderType().intValue()==CommodityType.JICHA.intValue()){
                        // taskExecutor.execute(new RebateJianDianThread(order,payIncomeAndUserV5UpGradeService));
                    }
                    return true;
                }
                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                logger.debug("【nofityOrder】支付宝返回状态为="+trade_status);
                Order order = orderService.findByzfbOrderId(out_trade_no);
                if (order.getStatus().intValue()==OrderStatus.TODO.intValue()||order.getStatus().intValue()==OrderStatus.YES.intValue()){
                    logger.debug("【nofityOrder】执行订单状态更新为yes="+trade_status);
                    order.setStatus(OrderStatus.COMFIRM);
                    orderMapper.updateOrderStatus(order);
                    //极差返佣金
                    if (order.getOrderType().intValue()==CommodityType.JICHA.intValue()) {
                        //taskExecutor.execute(new RebateJianDianThread(order, payIncomeAndUserV5UpGradeService));
                    }else if(order.getOrderType().intValue()==CommodityType.THREE.intValue()){
                        logger.error("[三级分销商品返佣金]");
                    }
                    return true;
                }
                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }
            return true;
        }else{//验证失败
           return false;
        }
    }

    /**
     * 新版本支付回调
     * @param trade_status
     * @param out_trade_no
     * @param params
     * @return
     * @throws AlipayApiException
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public boolean nofityOrderZfbApp(String trade_status, String out_trade_no, Map<String, String> params) throws AlipayApiException {
        logger.debug("【nofityOrderZfbApp】支付宝小程序订单回调="+params);
        // 项目当前路径
        String path = System.getProperty("user.dir");
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        String publicKeyCertPath = path + "/alipayCertPublicKey_RSA2.crt";
        boolean verify_result = AlipaySignature.rsaCertCheckV1(params, publicKeyCertPath, "utf-8","RSA2");
        if(verify_result){
            // 返回成功
            if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                logger.debug("【nofityOrder】支付宝返回状态为="+trade_status);
                Order order = orderService.findByzfbOrderId(out_trade_no);
                if (order.getStatus().intValue()==OrderStatus.TODO.intValue()||order.getStatus().intValue()==OrderStatus.YES.intValue()){
                    logger.debug("【nofityOrder】执行订单状态更新为yes="+trade_status);
                    order.setStatus(OrderStatus.COMFIRM);
                    orderMapper.updateOrderStatus(order);
                    taskExecutor.execute(new PaymentServiceImpl.PropcardThread(order, propcardService));
                    //极差返佣金
                    if (order.getOrderType().intValue()==CommodityType.JICHA.intValue()) {
                        taskExecutor.execute(new RebateThread(order, payIncomeAndUserV10UpGradeService));
                    }else if(order.getOrderType().intValue()==CommodityType.THREE.intValue()){
                        logger.error("[三级分销商品返佣金]");
                    }
                    return true;
                }
                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }
            logger.debug("验证成功！");
            return true;
        }else{//验证失败
            logger.debug("验证失败！");
            return false;
        }
    }


    public String getOutTradeNo(Integer orderId){
        return "z"+orderId+"t"+System.currentTimeMillis();
    }
    /**
     * 手机网页吊起支付
     * @param order
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public String createZfbOrder(Order order){
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(order.getName()+order.getNameItem());
        model.setSubject(order.getName()+order.getNameItem());

        String zfbOutTradeNo = getOutTradeNo(order.getId());
        order.setZfbOrderId(zfbOutTradeNo);
        orderService.updatezfbOrder(order);

        model.setOutTradeNo(zfbOutTradeNo);
        model.setTimeoutExpress("30m");//设置订单最晚支付时间
        model.setTotalAmount(""+order.getSubPrice());

        model.setProductCode("QUICK_WAP_WAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(successUrl);
        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = getAlipayClient().pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * APP支付
     * @param order
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public String createZfbAppOrder(Order order){
        // 实例化客户端
        AlipayClient alipayClient = null;
        try {
            alipayClient = getAlipayClient();
        } catch (AlipayApiException e){
            e.printStackTrace();
            throw new BusinessException("支付调起失败！");
        }
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();


        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        model.setBody(order.getName());
        model.setSubject(order.getName());

        String zfbOutTradeNo = getOutTradeNo(order.getId());
        order.setZfbOrderId(zfbOutTradeNo);
        orderService.updatezfbOrder(order);
        // 更新支付类型为支付宝支付
        order.setType(OrderType.ZHIFUBAO);
        orderService.updateType(order);

        model.setOutTradeNo(zfbOutTradeNo);
        model.setTimeoutExpress("30m");//设置订单最晚支付时间
        model.setTotalAmount(""+order.getSubPrice());

        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
//        request.setNotifyUrl("商户外网可以访问的异步地址");
        request.setNotifyUrl(notifyUrl);
        String form = "";
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            form = response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "{\"error_code\":\"-1\"}";
        }

        return form;
    }


    /**
     * 根据提现单子进行更新提现
     * @param record
     * @return
     * @throws AlipayApiException
     */
    public AlipayFundTransUniTransferResponse PayForUser(Record record) throws AlipayApiException {
        logger.debug("转账给客户# ",record.toString());
        logger.debug("原来打款状态"+record.getStatusString());
        if (record.getStatus().intValue()==RecordStatus.YES.intValue()){
            throw new BusinessException("已成功打款，不能再继续打款");
        }

        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\""+record.getZfbOrderNo()+"\"," +
                "\"trans_amount\":"+record.getMoney()+"," +
                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                "\"order_title\":\"销售提成代发\"," +
                "\"payee_info\":{" +
                "\"identity\":\""+record.getZfb()+"\"," +
                "\"identity_type\":\"ALIPAY_LOGON_ID\"," +
                "\"name\":\""+record.getZfbName()+"\"," +
                "    }," +
                "\"remark\":\"销售提成代发\"," +
                "\"business_params\":\"{\\\"payer_show_name\\\":\\\"小野民宿\\\"}\"," +
                "  }");
        AlipayFundTransUniTransferResponse response = getAlipayClient().certificateExecute(request);
        return  response;
    }

    /**
     * 支付宝订单查询
     * 文档 https://docs.open.alipay.com/api_28/alipay.fund.trans.order.query
     * 错误代码 AlipayFundTransOrderQueryResponse error_code
     ORDER_NOT_EXIST	                转账订单不存在	        转账订单不存在的原因,可能是转账还在处理中,也可能是转账处理失败,导致转账订单没有落地。商户首先需要检查该订单号是否确实是自己发起的, 如果确认是自己发起的转账订单,请不要直接当作转账失败处理,请隔几分钟再尝 试查询,或者通过相同的 out_biz_no 再次发起转账。如果误把还在转账处理中的订单直接当转账失败处理,商户自行承担因此而产生的所有损失。
     NO_ORDER_PERMISSION	            商家没有该笔订单的操作权限	请重新检查一下查询条件是否正确。商户只允许查询自己发起的转账订单,如果查询的转账订单不属于该商户就会报该错误。
     INVALID_PARAMETER	                参数有误。	            请检查请求参数的长度正确性和合法性，out_biz_no与order_id不能同时为空
     ILLEGAL_ACCOUNT_STATUS_EXCEPTION	账户状态异常	            请检查一下账户状态是否正常，如果账户不正常请联系支付宝小二。联系方式：https://support.open.alipay.com/alipay/support/index.htm
     SYSTEM_ERROR	                    系统繁忙	                支付宝系统繁忙或者处理超时，请稍后重试。
     ORDER_ID_INVALID	                非法的支付宝转账单据号	    当请求参数order_id不为空时，检查长度是否符合要求。当前order_id长度仅支持30或32位。
     */
    public void query(String outBizNo,String orderId) throws AlipayApiException {
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"3142321423432\"," +
                "\"order_id\":\"20160627110070001502260006780837\"" +
                "  }");
        AlipayFundTransOrderQueryResponse response = getAlipayClient().execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    /**
     * 进行返佣金现成类
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
}
