package com.webdrp.annotation;

/**
 * Created by hello on 2017/7/5.
 */
public interface LoginStatus {
    //授权成功登陆状态
    int LoggedIn = 1;
    int RedirectToDoLogin = 2;
    int RedirectToDoRegister = 3;
    int UpdateUnionId = 4;
    String State = "LoginStatus";
    //微信用户

    // 上家id

    String PUserId = "lastUserId";
    // 上家id


    String PUserIdDefault = "100000";
}
