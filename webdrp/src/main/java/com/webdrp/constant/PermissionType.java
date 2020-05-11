package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

//权限类型说明
public class PermissionType {
    //1前端URL，2前端Button，3后台URL
    public static final Integer WEB_PAGE = 0;
    public static final Integer WEB_PAGE_BUTTON = 1;
    public static final Integer BACK_URL = 2;
    public static final Map<Integer,String> map = new HashMap();
    static{
        {
            map.put(WEB_PAGE,"前端URL");
            map.put(WEB_PAGE_BUTTON,"前端按钮");
            map.put(BACK_URL,"后端URL");
        }
    }

    //获取权限的中文名称
    public static String getString(Integer permissionType){
        if (permissionType < 0 || permissionType > 2){
            return "无此权限类型，系统异常";
        }
        return map.get(permissionType);
    }
}
