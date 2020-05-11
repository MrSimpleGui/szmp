package com.webdrp.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanming on 2018/8/17.
 */
public class IncomeType {

    //直推，建点,感恩，分润，商品分销

    public static final Integer ZHITUI = 0;

    public static final  Integer JIANDIAN= 1;

    public static final  Integer GANEN = 2;

    public static final Integer FENRUN = 3;

    public static  final Integer FENXIAO = 4;

    public static  final Integer TongJi = 5;
    public static  final Integer XITONG = 6;
    public static  final Integer TEAM_ORDER = 7;
    public static  final Integer TEAM_MANAGER = 8;
    public static final Integer SIDE_WAY = 9;

    public static final Map<Integer,String> map = new HashMap();

    static{
        {
            map.put(ZHITUI,"佣金");
            map.put(JIANDIAN,"佣金");
            map.put(GANEN,"佣金");
            map.put(FENRUN,"佣金");
            map.put(FENXIAO,"分润");
            map.put(TongJi,"佣金");
            map.put(XITONG,"系统");
            map.put(TEAM_ORDER,"标记算单");
            map.put(TEAM_MANAGER,"团队管理奖");
            map.put(SIDE_WAY,"平级管理奖");
//            map.put(ZHITUI,"直推佣金");
//            map.put(JIANDIAN,"建点佣金");
//            map.put(GANEN,"感恩佣金");
//            map.put(FENRUN,"分润佣金");
//            map.put(FENXIAO,"商品分润");
//            map.put(TongJi,"同级佣金");
//            map.put(XITONG,"系统");
//            map.put(TEAM_ORDER,"标记算单");

        }
    }
    public static String getIncomeTypeString(Integer incomeType){
        if (incomeType < 0 || incomeType > 9){
            return "无此类型收入记录";
        }
        return map.get(incomeType);
    }


}
