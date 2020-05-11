package com.webdrp.util;

import java.util.Base64;

/**
 * base64编码解码
 *
 */
public class Base64EncodeUtil {

    //base64解码
    public static String encodeBase64String(String encodeString){
        if (encodeString==null || encodeString.length()==0){
            return "";
        }
        return new String(Base64.getEncoder().encode(encodeString.getBytes()));
    }
    //base64编码
    public static String decodeBase64(String base64String){
        return new String(Base64.getDecoder().decode(base64String.getBytes()));
    }

    public static void main(String[] args) {
        String baseString = "1234567890";
        String base64String = encodeBase64String(baseString);
        System.out.println("Base64EncodeUtil.getBase64String="+base64String);
        System.out.println("Base64EncodeUtil.deCodingBase64="+decodeBase64(base64String));

    }




}
