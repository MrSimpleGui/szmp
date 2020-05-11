package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanming on 2018/8/16.
 */
public class RecordStatus {


    public static final Integer TODO = 0;
    public static final Integer YES = 1;
    public static final Integer ERROR = 2;

    public static final Map<Integer,String> map = new HashMap();
    static{
        {
            map.put(TODO,"系统处理中");
            map.put(YES,"提现成功");
            map.put(ERROR,"提现失败");
        }
    }

    public static String getString(Integer status){
        if (status < 0 || status > 2){
            return "无此状态，系统异常";
        }
        return map.get(status);
    }


}
