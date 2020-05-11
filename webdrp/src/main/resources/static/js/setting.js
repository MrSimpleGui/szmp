console.log($.md5('123456'))


$("#updatePassword").click(function () {
    var username = $("#username").val();
    var password = $("#password").val();

    if (''==username){
        $.toast("用户名不能为空", "text");
        $("#username").focus();
        return;
    }

    if (username.length > 18){
        $.toast("用户名建议不超过18位数", "text");
        $("#username").focus();
        return;
    }

    if ('' == password){
        $.toast("密码不能为空", "text");
        $("#password").focus();
        return;
    }

    if (password.length < 6){
        $.toast("密码不能小于6位数", "text");
        $("#password").focus();
        return;
    }

    if (password.length > 18){
        $.toast("密码不能大于位数", "text");
        $("#password").focus();
        return;
    }



    var passwordMd5 = $.md5(password);

    $.post(ctxPath + "wechat/api/setting", { "username": username,"password":passwordMd5},
        function(data){
           if (data.status){
               $.toast("设置成功,可凭帐号登录手机APP", "text");
           }else{
               $.toast(data.errorMsg, "text");
           }
        }, "json");
})
