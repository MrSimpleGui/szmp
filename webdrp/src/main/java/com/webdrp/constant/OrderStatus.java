package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanming on 2018/8/16.
 */
public class OrderStatus {

    public static final Integer TODO = 0;   //未支付
    public static final Integer YES = 1;    //支付确认中
    public static final Integer COMFIRM = 2;  //已支付
    public static final Integer CANCEL = 3;  //已取消。。。
    public static final Integer CheckFail = 4;  //支付确认失败
    public static final Map<Integer,String> map = new HashMap();

    static {

        map.put(TODO,"未支付");
        map.put(YES,"支付确认中");
        map.put(COMFIRM,"已支付");
        map.put(CANCEL,"已取消");
        map.put(CheckFail,"支付确认失败");

    }

    public static String getString(Integer status){
        return map.get(status);
    }
}
