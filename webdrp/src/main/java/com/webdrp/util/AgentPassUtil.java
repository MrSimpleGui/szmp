package com.webdrp.util;

public class AgentPassUtil {

    /**
     * 上架密码加密方案
     * @param password
     * @return
     */
    public static String getPasswordEncode(String password){
        String base64sString = Base64EncodeUtil.encodeBase64String(password);
        return Sha256decode.getSHA256Str(base64sString);
    }
}
