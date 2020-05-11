package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class CommodityType {



    public static final Integer Normal = 0;
    public static final Integer JICHA = 1;
    public static final Integer SECONDBUY = 2;
    public static final Integer ONE = 3;
    public static final Integer TWO = 4;
    public static final Integer THREE = 5;


    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(Normal,"普通产品");
            map.put(JICHA,"级差");
            map.put(SECONDBUY,"复购");
            map.put(ONE,"一级分销");
            map.put(TWO,"二级分销");
            map.put(THREE,"三级分销");
        }
    }

    public static String getCommodityToTypeString(Integer ctype){
        if (ctype < 0 || ctype > 5){
            return "无此类型商品";
        }
        return map.get(ctype);
    }
}
