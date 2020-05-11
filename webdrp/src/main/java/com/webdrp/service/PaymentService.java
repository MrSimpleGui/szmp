package com.webdrp.service;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.jssdk.JSSDKAPI;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.MerchantResult;
import com.webdrp.entity.Order;
import com.webdrp.entity.Member;
import com.webdrp.entity.User;
import com.webdrp.util.saobei.OrderResponse;
import com.webdrp.util.saobei.PosPrepayRe;

import java.io.IOException;
import java.io.InputStream;

/**
 * 微信公众号支付业务接口
 */
public interface PaymentService {

    /**
     * 微信创建订单
     * @param order 订单信息
     * @return 支付请求接口响应
     */
    MchPayRequest placeOrder(Order order, String realIp, Member user) throws WeixinException;

    /**
     * 小野云商创建支付
     * @param order
     * @param realIp
     * @param user
     * @return
     * @throws WeixinException
     */
    MchPayRequest placeOrderYS(Order order, String realIp, Member user) throws WeixinException;

    /**
     * 第三方创建订单
     * @param order
     * @return
     * @throws WeixinException
     */
    PosPrepayRe placeOrderSaoBei(Order order);
    /**
     * 获取JSSDK的配置信息
     * 支付的
     * @param requestURI 请求路径的URI
     * @param jssdkapis 需要的微信权限
     * @return JSSDK JSON格式配置信息
     */

     String jssdkConfig(String requestURI, JSSDKAPI... jssdkapis);


    /**
     * 小野云商的
     * @param requestURI
     * @param jssdkapis
     * @return
     */
     String jssdkConfig2(String requestURI, JSSDKAPI... jssdkapis);



    /**
     * 微信订单支付回调
     * @param input 回调输入流
     * @return 用户支付结果
     */
    XmlResult payNotify(InputStream input) throws IOException;

    /**
     * 关闭微信订单
     * @param order 订单信息
     * @return 订单关闭结果
     */
    MerchantResult closeOrder(Order order) throws WeixinException;

    /**
     * 订单退款处理
     * 暂时为人工手动处理
     *
     * @param orderInfo 订单信息
     * @param user     操作人员信息
     * @param refundFee 退款金额
     * @param tips      退款审批备注
     */
    void refund(Order orderInfo, User user, double refundFee, String tips);

    /**
     * 微信公众号H5网页对发起支付事件的回调
     * 仅用于更新状态，实际确定按微信支付通知为准
     * @param orderId 订单id
     * @param status 用户支付事件处理结果
     */
    void webPayNotify(Integer orderId, String status);

    /**
     * 扫呗的支付回调
     */
    void saoBeiNotify(OrderResponse re);

    /**
     * 根据退款记录的状态获取退款记录
     * 分页
     * @param status 状态
     * @param currentResult 请求的页码
     * @param showCount 每页显示数量
     * @return 退款记录信息
     */
    //List<RefundRecordShow> listRefund(Integer status, Integer currentResult, Integer showCount);

    /**
     *
     * @param id 通过订单id获取退款的退款
     * @return
     */
    //Refundrecord findRefundrecordById(Integer id) throws Exception;
}

