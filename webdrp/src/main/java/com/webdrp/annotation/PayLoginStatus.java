package com.webdrp.annotation;

/**
 * Created by hello on 2017/7/5.
 */
public interface PayLoginStatus {
    int LoggedIn = 4;
    int RedirectToDoLogin = 5;
    int RedirectToDoRegister = 6;
    String State = "LoginStatus1";
    //微信用户

    // 上家id

    String PUserId = "lastUserId1";
    // 上家id


    String PUserIdDefault = "100000";
}
