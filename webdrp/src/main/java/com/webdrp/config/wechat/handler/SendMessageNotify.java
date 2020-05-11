package com.webdrp.config.wechat.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.tuple.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:33 2018/3/18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public class SendMessageNotify {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 统一api
     */
    @Autowired
    private com.foxinmy.weixin4j.mp.WeixinProxy wechatService;

    /**
     * 发送客服消息
     * @param openId 用户oepnid
     * @param message  消息openid
     */
    public void sendMessageNotify(String openId,String message){

        Text text = new Text(message);

        NotifyMessage notifyMessage = new NotifyMessage(openId,text);
        try {
            ApiResult apiResult = wechatService.sendNotify(notifyMessage);
            logger.debug("推送消息返回："+apiResult.toString());
        } catch (WeixinException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
