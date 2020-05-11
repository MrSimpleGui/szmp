package com.webdrp.constant;

import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.Map;

public class CommodityPublish {

    public static final Integer YES = 1;

    public static final Integer NO = 0;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(YES,"已发布");
            map.put(NO,"未发布");
        }
    }

    public static String getString(Integer publish){
        if (publish <0 ||publish > 1){
            return "无此状态";
        }
        return map.get(publish);
    }

}
