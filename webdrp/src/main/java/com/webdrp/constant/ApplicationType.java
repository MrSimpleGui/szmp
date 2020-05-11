package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class ApplicationType {

    public static final Integer HKMAO = 0;
    public static final Integer DAILI = 1;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(HKMAO,"港澳游");
            map.put(DAILI,"代理");
        }
    }

    public static String getCommodityToTypeString(Integer atype){
        if (atype < 0 || atype > 1){
            return "无此类型申请表";
        }
        return map.get(atype);
    }
}
