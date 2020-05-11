package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RichUserStatus {

    public static final Integer NORMAL = 0;
    public static final Integer REJECT = 1;
    public static final Map<Integer,String> map = new HashMap();
    static{
        {
            map.put(NORMAL,"正常");
            map.put(REJECT,"禁用");
        }
    }

    public static String getString(Integer type){
        if (Objects.isNull(type)){
            return "无此状态，系统异常";
        }
        if (type < 0 || type > 1){
            return "无此状态，系统异常";
        }
        return map.get(type);
    }
}
