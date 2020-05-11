package com.webdrp.err;

/**
 * Created by yuanming on 2018/8/4.
 */
public class LoginForBidden extends RuntimeException {

    /***
     * 拒绝登录
     * @param message
     */
    public LoginForBidden(String message) {
        super(message);
    }
}
