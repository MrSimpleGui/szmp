package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class AgentType {

    //0系统处理中，1报名成功，2报名失败
    public static final Integer AgentShop = 0;
    public static final Integer AgentProvince = 1;
    public static final Integer AgentCity = 2;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(AgentShop,"商城商家");
            map.put(AgentProvince,"省级代理");
            map.put(AgentCity,"市级代理");
        }
    }

    public static String getString(Integer applicationStatus){
        if (applicationStatus < 0 || applicationStatus > 2){
            return "无代理类别";
        }
        return map.get(applicationStatus);
    }
}
