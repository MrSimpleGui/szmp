package com.webdrp.util;

import java.util.UUID;

/**
 * Created by yuanming on 2018/8/4.
 * uuid生成工具类
 */
public class UUIDUtils {

    public static String getUUID(){
        return  UUID.randomUUID().toString().replace("-","");
    }


    public static void main(String[] args) {
        System.out.println("args = [" + getUUID() + "]");
    }
}
