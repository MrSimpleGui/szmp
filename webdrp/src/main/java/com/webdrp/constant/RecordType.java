package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/16.
 */
public class RecordType {


    public static final Integer WECHAT = 0;
    public static final Integer ZHIFUBAO = 1;
    public static final Map<Integer,String> map = new HashMap();
    static{
        {
            map.put(WECHAT,"微信");
            map.put(ZHIFUBAO,"支付宝");
        }
    }

    public static String getString(Integer type){
        if (type < 0 || type > 1){
            return "无此状态，系统异常";
        }
        return map.get(type);
    }


}
