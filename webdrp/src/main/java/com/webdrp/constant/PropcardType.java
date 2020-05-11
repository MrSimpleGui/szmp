package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class PropcardType {
    public static final Integer RENAME = 0;   //改名卡
    public static final Integer RESUPER = 1;    //改级卡
    public static final Map<Integer,String> map = new HashMap();

    static {

        map.put(RENAME,"改名卡");
        map.put(RESUPER,"改级卡");

    }

    public static String getString(Integer status){
        return map.get(status);
    }
}
