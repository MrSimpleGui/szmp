package com.webdrp.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.webdrp.common.Result;
import com.webdrp.err.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * @Author: zhang yuan ming
 * @Date: create in 下午12:30 2018/4/22
 * @mail: zh878998515@gmail.com
 * @Description: 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * JSON数据请求异常
     * @param rex
     * @return
     */
    @ExceptionHandler(JSONRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result runtimeException(Exception rex){
        logger.error("runtimeException json");
        return Result.fail("非法请求，请登录！",1000000);
    }
    /**
     * 没有此用户
     * @param rex
     * @return
     */
    @ExceptionHandler(NoSuchUserError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result noSuchUserError(Exception rex){
        rex.printStackTrace();
        logger.error("没有此用户",rex.getMessage());
        return Result.fail(rex.getMessage(),4000000);
    }
  /**
     * 没有此用户
     * @param rex
     * @return
     */
    @ExceptionHandler(PasswordError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result passwordError(Exception rex){
        rex.printStackTrace();
        logger.error("密码错误",rex.getMessage());
        return Result.fail("密码错误！",4000001);
    }

 /**
     * 没有此用户
     * @param rex
     * @return
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result permissionException(Exception rex){
        rex.printStackTrace();
        logger.error("权限不足",rex.getMessage());
        return Result.fail("权限不足！",4000002);
    }


    @ExceptionHandler(LoginTimeoutRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result loginTimeout(Exception rex){
        rex.printStackTrace();
        logger.error("登录超时",rex.getMessage());
        return Result.fail("登录超时！",10086);
    }
    @ExceptionHandler(AppLoginTimeoutRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result apploginTimeout(Exception rex){
        rex.printStackTrace();
        logger.error("登录超时",rex.getMessage());
        return Result.apptokenTimeOut("登录超时！",1000000);
    }


 /**
     * 没有此用户
     * @param rex
     * @return
     */
    @ExceptionHandler(NoSuchObjectError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result noSuchObjectError(Exception rex){
        rex.printStackTrace();
        logger.error("没有这个对象",rex.getMessage());
        return Result.fail(rex.getMessage(),4000002);
    }

    /**
     * 没有此用户
     * @param rex
     * @return处理下空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result nullPointerException(Exception rex){
        rex.printStackTrace();
        logger.error("空指针异常",rex.getMessage());
        return Result.fail("空指针异常",400);
    }



    /**
     * 没有此用户
     * @param rex
     * @return处理下空指针异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result exception(Exception rex){
        rex.printStackTrace();
        logger.error("系统异常",rex.getMessage());
        return Result.fail("系统异常",500);
    }

    /**
     * 自定义业务异常
     *
     *
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result exception(BusinessException b){
        b.printStackTrace();
        logger.error("异常",b.getMessage());
        return Result.fail(b.getMessage(),500);
    }



    @ExceptionHandler(WeixinException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result weixinExcetion(WeixinException we){
        we.printStackTrace();
        return Result.fail("微信支付异常",500);
    }

    @ExceptionHandler(NoUserDataError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result runtimeNoUserDataError(Exception rex){
        rex.printStackTrace();
        logger.error("没有此用户",rex.getMessage());
        return Result.fail(rex.getMessage(),4000000);
    }
    @ExceptionHandler(CannotLoginError.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result cannotLoginError(Exception rex){
        rex.printStackTrace();
        logger.error("不允许登录",rex.getMessage());
        return Result.fail(rex.getMessage(),4000001);
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result paramter(Exception rex){
        rex.printStackTrace();
        logger.error("参数异常",rex.getMessage());
        return Result.fail("参数异常",400);
    }


//    /**
//     * 页面异常
//     * @param rex
//     * @param response
//     * @return
//     */
//    @ExceptionHandler(ViewRunTimeException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public String exception(Exception rex, HttpServletResponse response){
//        logger.error("runtimeException view");
//        return "redirect:manage/loginPage";
//    }

}
