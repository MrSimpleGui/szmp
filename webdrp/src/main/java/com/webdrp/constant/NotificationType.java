package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class NotificationType {
    public static final Integer WECHAT = 0;
    public static final Integer ZHONGXIN = 1;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(WECHAT,"微信通知");
            map.put(ZHONGXIN,"运营中心");
        }
    }

    public static String getIncomeTypeString(Integer notificationType){
        if (notificationType < 0 || notificationType > 1){
            return "无此类型收入记录";
        }
        return map.get(notificationType);
    }
}
