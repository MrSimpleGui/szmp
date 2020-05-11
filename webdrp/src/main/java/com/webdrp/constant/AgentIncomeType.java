package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class AgentIncomeType {

    //1旅游 线路分润 2.报单产品  3特产商城
    public static final Integer AgentShop = 1;
    public static final Integer AgentBaoDan = 2;
    public static final Integer AgentTeChan = 3;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(AgentShop,"线路分润");
            map.put(AgentBaoDan,"报单产品");
            map.put(AgentTeChan,"特产商城");
        }
    }

    public static String getString(Integer applicationStatus){
        if (applicationStatus < 1 || applicationStatus > 3){
            return "无此收入类型";
        }
        return map.get(applicationStatus);
    }
}
