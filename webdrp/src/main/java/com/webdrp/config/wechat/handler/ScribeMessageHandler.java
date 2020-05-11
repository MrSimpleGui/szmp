package com.webdrp.config.wechat.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.mp.event.ScribeEventMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Author: zhang yuan ming
 * @Date: create in 上午11:14 2018/3/19
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public class ScribeMessageHandler  extends MessageHandlerAdapter<ScribeEventMessage> {
    private static final String SubscribeEvent ="subscribe";
    private static final String UnSubscribeEvent ="unsubscribe";

    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    RichUserService richUserService;

//    @Autowired
//    @Qualifier("wechatService")   //两个公众号情况这样查找
//    WeixinProxy weixinProxy;

    /**
     * 处理关注事件

     * @param message 微信消息
     * @return
     */
    private WeixinResponse doSubscribe(ScribeEventMessage message) {
        //二维码
        //如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
        //如果用户已经关注公众号，在用户扫描后会自动进入会话，微信也会将带场景值扫描事件推送给开发者。
        // 区分：（名片推荐|直接关注）（经过测试数据是一样的）|扫码关注
//        logger.debug("关注事件日志doSubscribe=="+request.toString());
        logger.debug("关注事件日志message==",message.toString());
        //假如是关注
//        if (message.getEventType().equals(SubscribeEvent)){
//            RichUser accountUser = null;
//            try {
//                RichUser wechatUser = richUserService.findByOpenId(message.getFromUserName());
//                if (Objects.nonNull(wechatUser)){
//                    wechatUser.setIsSubscribe(1);
//                    richUserService.update(wechatUser);
//                }else {
//                    try {
//                        RichUser wechatUser1 = new RichUser();
//                        wechatUser1.setIsSubscribe(1);
//                        User wechatUserInfo = weixinProxy.getUser(message.getFromUserName());
//                        wechatUser1.setOpenid(wechatUserInfo.getOpenId());
//                        wechatUser1.setCity(wechatUserInfo.getCity());
//                        wechatUser1.setCountry(wechatUserInfo.getCountry());
//                        wechatUser1.setProvince(wechatUserInfo.getProvince());
//                        wechatUser1.setHeadImg(wechatUserInfo.getHeadimgurl());
//                        wechatUser1.setNickname(wechatUserInfo.getNickName());
//                        wechatUser1.setSex(wechatUserInfo.getGender());
//                        richUserService.add(wechatUser1);
//                    }catch (Exception ex){
//                        ex.printStackTrace();
//                        logger.error("ScribeMessageHandler = 关注插入用户信息失败");
//                    }
//                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
        TextResponse textResponse = new TextResponse("希望你在许多个被出行填满的日子里，时间不再会悄然离去，" +
                "我们帮你记得它一步一步的脚印。");
        return textResponse;
    }

    /**
     * 处理取关事件
     * @param message 微信消息
     * @return
     */
    private WeixinResponse doUnSubscribe( ScribeEventMessage message) {
//        if (message.getEventType().equals(UnSubscribeEvent)){
//            try {
//                RichUser wechatUser = richUserService.findByOpenId(message.getFromUserName());
//                if (Objects.nonNull(wechatUser)){
//                    //设置为取消关注
//                    wechatUser.setIsSubscribe(0);
//                    richUserService.update(wechatUser);
//                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
        TextResponse textResponse = new TextResponse("感谢一路有你");
        return textResponse;
    }

//    @Override
//    protected WeixinResponse doHandle0(ScribeEventMessage scribeEventMessage) {
//
//    }

    /**
     * 处理请求
     *
     * @param request 微信请求
     * @param message 微信消息
     * @return
     */
    @Override
    public WeixinResponse doHandle0(WeixinRequest request, ScribeEventMessage message) throws WeixinException {
        String eventType = message.getEventType();
//        System.out.println("微信消息请求"+request.toString());
        switch (eventType){
            // 用户关注后返回的微信响应
            case SubscribeEvent:
                return doSubscribe(message);
            // 用户取关后返回的微信响应
            case UnSubscribeEvent:
                return doUnSubscribe(message);
            default:
                logger.warn("scribeEventType unknown and not handler it:"+eventType);
                return null;
        }
    }
}
