package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

public class PermissionWeight {

    //1前端URL，2前端Button，3后台URL
    public static final Integer NO_KNOW = 0;
    public static final Integer VIEW = 1;
    public static final Integer ADD = 2;
    public static final Integer UPDATE = 3;
    public static final Integer DELETE = 4;
    public static final Map<Integer,String> map = new HashMap();
    static{
        {
            map.put(NO_KNOW,"一般权限");
            map.put(VIEW,"查看权限");
            map.put(ADD,"增加权限");
            map.put(UPDATE,"更新权限");
            map.put(DELETE,"删除权限");
        }
    }

    //获取权限的中文名称
    public static String getString(Integer permissionWeigth){
        if (permissionWeigth < 0 || permissionWeigth > 5){
            return "未知权限级别";
        }
        return map.get(permissionWeigth);
    }
}
