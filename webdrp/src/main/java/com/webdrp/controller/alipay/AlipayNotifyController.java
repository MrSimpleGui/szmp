package com.webdrp.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.webdrp.service.impl.AlipayServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝回调通知
 */

@Controller
@RequestMapping("/alipay/pay")
public class AlipayNotifyController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AlipayServiceImpl alipayService;


    @RequestMapping("/notify")
    public void paynotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("AlipayNotifyController【paynotify】支付宝订单回调");
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        logger.debug("[AlipayNotifyController【paynotify】支付宝订单回调 out_trade_no="+out_trade_no+";;trade_no = "+trade_no+""+trade_status);
        boolean status = false;
        try {
            status = alipayService.nofityOrderZfbApp(trade_status,out_trade_no,params);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().print("fail");
        }
        if (status){
            response.getWriter().print("success");
        }else{

            response.getWriter().print("fail");
        }
        response.getWriter().flush();
    }

}
