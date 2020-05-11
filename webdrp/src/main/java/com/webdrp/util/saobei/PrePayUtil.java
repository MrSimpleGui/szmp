package com.webdrp.util.saobei;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;


import com.webdrp.common.SaoBeiKey;
import com.webdrp.entity.Order;
import com.webdrp.util.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class PrePayUtil {
	
	public static PosPrepayRe posPrePayRe(PosPrepay posPrePay,Integer orderId) {
		String access_token= SaoBeiKey.access_token;
//		String host = "http://test.lcsw.cn:8045/lcsw";
		String host = "https://pay.lcsw.cn/lcsw";
		String prePay_url=  host +  "/pay/100/jspay";
		PosPrepayRe posPrePayRe = new PosPrepayRe();
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("pay_ver", posPrePay.getPay_ver());
			jsonParam.put("pay_type", posPrePay.getPay_type());
			jsonParam.put("service_id", posPrePay.getService_id());
			jsonParam.put("merchant_no", posPrePay.getMerchant_no());
			jsonParam.put("terminal_id", posPrePay.getTerminal_id());
			jsonParam.put("terminal_trace", posPrePay.getTerminal_trace());
			jsonParam.put("terminal_time", posPrePay.getTerminal_time());
			jsonParam.put("total_fee", posPrePay.getTotal_fee());
			jsonParam.put("order_body", posPrePay.getOrder_body());
			jsonParam.put("sub_appid", posPrePay.getSub_appid());
			jsonParam.put("open_id", posPrePay.getOpen_id());
			jsonParam.put("notify_url", "http://xyrc.yyezl.com/wechat/v1/pay/saoBeiNotify");
			jsonParam.put("attach", orderId);

			String parm = "pay_ver=" + posPrePay.getPay_ver() + "&pay_type=" + posPrePay.getPay_type() + "&service_id="
					+ posPrePay.getService_id() + "&merchant_no=" + posPrePay.getMerchant_no() + "&terminal_id="
					+ posPrePay.getTerminal_id() + "&terminal_trace=" + posPrePay.getTerminal_trace()
					+ "&terminal_time=" + posPrePay.getTerminal_time() + "&total_fee=" + posPrePay.getTotal_fee()
//					 +"&notify_url="+posPrePay.getNotify_url()
//					 +"&order_body="+posPrePay.getOrder_body()
//					 +"&sub_appid="+posPrePay.getSub_appid()
//					 +"&open_id="+posPrePay.getOpen_id()
					+ "&access_token=" +access_token;
			String sign = MD5.sign(parm, "utf-8");
			jsonParam.put("key_sign", sign);
//			System.out.println(prePay_url + "");
			String xmlText = tojson(prePay_url, jsonParam.toJSONString());
			posPrePayRe = (PosPrepayRe) JSON.parseObject(xmlText, PosPrepayRe.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posPrePayRe;
	}


    public static String tojson(String gateway,String jsonParam)throws Exception {
		
		String xmlText = "";
		
		CloseableHttpClient httpclient = HttpClients.custom().build();
		try {
			
			   HttpPost httpPost = new HttpPost(gateway); 
             httpPost.addHeader("charset", "UTF-8");
             System.out.println(jsonParam.toString());
			 StringEntity stentity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题    
			 stentity.setContentEncoding("UTF-8");    
			 stentity.setContentType("application/json");
			 httpPost.setEntity(stentity);
		     CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						xmlText = xmlText + text;
					}
				}
				
				EntityUtils.consume(entity);
				System.out.println(xmlText);
			} finally {
				
				response.close();
			}
		} finally {
			
			httpclient.close();
	    }
		
	    return xmlText;
 }


 	public static PosPrepayRe posPrepayReByOrder(Order order,String saoBeiNotify){
		PosPrepay posPrepay = new PosPrepay();
		posPrepay.setPay_ver("100");
		posPrepay.setPay_type("010");
		posPrepay.setService_id("012");
		posPrepay.setMerchant_no(SaoBeiKey.merchant_no);
		posPrepay.setTerminal_id(SaoBeiKey.terminal_id);
		posPrepay.setTerminal_trace(order.getWechatOrderId()); 			 // 商户系统的订单号
		posPrepay.setTerminal_time(DateUtils.dateToStringYyyyMMddHHmmss(new Date()));
		Double totalFee = order.getSubPrice()*100;
		int totalFeeInt = totalFee.intValue();
		posPrepay.setTotal_fee(""+totalFeeInt);
		posPrepay.setOrder_body("小野民宿尊享卡一年有效期");
		posPrepay.setSub_appid("wxaca469a0592f29d7");
		posPrepay.setOpen_id(order.getOpenId());
		posPrepay.setNotify_url(saoBeiNotify);
		PosPrepayRe post = posPrePayRe(posPrepay,order.getId());
		return post;
	}


	public static void main(String[] args) {
//		pay_ver	String	3	Y	版本号，当前版本100
//		pay_type	String	3	Y	支付方式，010微信，020支付宝，060qq钱包，090口碑，100翼支付，140和包支付（仅限和包通道）
//		service_id	String	3	Y	接口类型，当前类型012
//		merchant_no	String	15	Y	商户号
//		terminal_id	String	8	Y	终端号

//		terminal_trace	String	32	Y	终端流水号，填写商户系统的订单号

//		terminal_time	String	14	Y	终端交易时间，yyyyMMddHHmmss，全局统一时间格式
//		total_fee	String	12	Y	金额，单位分
//		order_body	String	128	N	订单描述

//		notify_url	String	256	N	外部系统通知地址
//		attach	String	128	N	附加数据，原样返回
//		goods_detail	String	2048	N	订单包含的商品列表信息，Json格式。pay_type为010，020，090时，可选填此字段
//		goods_tag	String	32	N	订单优惠标记，代金券或立减优惠功能的参数（字段值：cs和bld）

//		sub_appid	String	16	N	公众号appid，公众号支付时使用的appid（若传入，则open_id需要保持一致）
//		open_id	String	128	N	用户标识（微信openid，支付宝userid），pay_type为010及020时需要传入

//		key_sign	String	32	Y	签名字符串,拼装所有必传参数+令牌，UTF-8编码，32位md5加密转换
		PosPrepay posPrepay = new PosPrepay();
		posPrepay.setPay_ver("100");
		posPrepay.setPay_type("010");
		posPrepay.setService_id("012");
		posPrepay.setMerchant_no("811000002000012"); // 商户号
		posPrepay.setTerminal_id("30060913"); 		 // 终端号

		posPrepay.setTerminal_trace("1234567843456213"); 			 // 商户系统的订单号
		posPrepay.setTerminal_time(DateUtils.dateToStringYyyyMMddHHmmss(new Date()));
		posPrepay.setTotal_fee("1");
		posPrepay.setOrder_body("一年有效期");
		posPrepay.setSub_appid("wxaca469a0592f29d7");
		posPrepay.setOpen_id("oHCWzs1YBnb6k3MLwonQZJzf_x2A");
		posPrepay.setNotify_url("http://xyrc.yyezl.com/wechat/v1/pay/saoBeiNotify");
		PosPrepayRe post = posPrePayRe(posPrepay,12);

		System.out.println(JSON.toJSON(post));

	}
		
		
		
	
}
