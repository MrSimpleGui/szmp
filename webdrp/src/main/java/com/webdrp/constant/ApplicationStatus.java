package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class ApplicationStatus {
    //0系统处理中，1报名成功，2报名失败
    public static final Integer TODO = 0;
    public static final Integer SUCCESS = 1;
    public static final Integer FALSE = 2;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(TODO,"系统处理中");
            map.put(SUCCESS,"报名成功");
            map.put(FALSE,"报名失败");
        }
    }

    public static String getString(Integer applicationStatus){
        if (applicationStatus < 0 || applicationStatus > 2){
            return "无此申请状态";
        }
        return map.get(applicationStatus);
    }
}
