package com.webdrp.controller.wx;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:01 2020-03-12
 * @mail: zh878998515@gmail.com
 * @Description:微信支付成功回调
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;
import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.common.Result;
import com.webdrp.entity.*;
import com.webdrp.err.BusinessException;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.*;
import com.webdrp.util.CusAccessObjectUtil;
import com.webdrp.util.saobei.OrderResponse;
import com.webdrp.util.saobei.PosPrepayRe;
import com.webdrp.util.saobei.SaoBeiResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/wechat/v1")
public class WechatPayNotifyController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    ConfigService configService;

    @Autowired
    MemberService memberService;

    @Autowired
    VipCardService vipCardService;


    /**
     * 微信支付成功 时的回调通知  微信后台回调
     * @param input 订单回调
     * @return &ltxml&gt<br>
     *         &ltreturn_code&gtSUCCESS/FAIL&lt/return_code&gt<br>
     *         &ltreturn_msg&gt如非空,为错误 原因签名失败参数格式校验错误&lt/return_msg&gt<br>
     *         &lt/xml&gt
     * @throws IOException
     * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7">支付结果通知</a>
     */
    @RequestMapping("/pay/notify")
    public String payNotify(InputStream input) throws IOException {
        XmlResult xmlResult =  paymentService.payNotify(input);
        logger.debug("支付成功回调");
        return XmlStream.toXML(xmlResult);
    }

    /**
     * 云商微信支付成功 时的回调通知  微信后台回调
     * @param input 订单回调
     * @return &ltxml&gt<br>
     *         &ltreturn_code&gtSUCCESS/FAIL&lt/return_code&gt<br>
     *         &ltreturn_msg&gt如非空,为错误 原因签名失败参数格式校验错误&lt/return_msg&gt<br>
     *         &lt/xml&gt
     * @throws IOException
     * @see <a href="http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7">支付结果通知</a>
     */
    @RequestMapping("/pay/notifyYs")
    public String payNotifyYs(InputStream input) throws IOException {
        XmlResult xmlResult =  paymentService.payNotify(input);
        logger.debug("支付成功回调");
        return XmlStream.toXML(xmlResult);
    }

    @RequestMapping("/pay/saoBeiNotify")
    @ResponseBody
    public SaoBeiResponse saoBeiNotify(HttpServletRequest request) throws IOException {
        String content = StringUtil.newStringUtf8(IOUtil.toByteArray(request.getInputStream()));
        OrderResponse orderResponse = JSON.parseObject(content,OrderResponse.class);
        paymentService.saoBeiNotify(orderResponse);
        return SaoBeiResponse.SUCCESS;
    }

    @ApiOperation("处理前端支付成功，支付失败回调")
    @PostMapping("/notify/success")
    @SystemControllerLog(description = "根据订单信息进行支付")
    public Result notifySuccess(){
        return Result.success();
    }

}
