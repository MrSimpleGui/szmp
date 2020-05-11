package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class CommissionRuleType {
    public static final Integer PROPORTION = 0;   //百分比
    public static final Integer AMOUNT = 1;    //金额
    public static final Map<Integer,String> map = new HashMap();

    static {

        map.put(PROPORTION,"百分比");
        map.put(AMOUNT,"金额");


    }

    public static String getString(Integer status){
        return map.get(status);
    }
}
