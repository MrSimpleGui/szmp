package com.webdrp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    /**
    * @Description 将string转为integer List
    * @Param str
    * @Return
    * @Author Mr.Simple
    * @Date 2020/3/26 0026 下午 2:29
    **/
    public static List<Integer> strToInteger(String str){
        List<Integer> list = new ArrayList<>();
        list = Arrays.asList(str.split(","))
                .stream().map(s->Integer.parseInt(s)).collect(Collectors.toList());
        return list;
    }
}
