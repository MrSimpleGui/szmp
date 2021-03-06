package com.webdrp.common;

/**
 * Created by yuanming on 2017/12/9.
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Macx on 2017/12/1.
 * 对http请求的结果进行封装
 */
@ApiModel(value = "操作结果")
public class Result {

    public static Result Success = success(null);

    public static Result False = fail("接口未实现",1000);

    private Map<String,Object> collections;

    @ApiModelProperty(value = "结果说明 true = 成功  false = 失败  ",allowableValues = "true,false",required = true)//参数说明  参数肯定会存在
    private boolean status;
    @ApiModelProperty("提示信息")
    private String message;

    @ApiModelProperty("错误信息")
    private String errorMsg;

    @ApiModelProperty("错误代码")
    private int errorCode;

    @ApiModelProperty("操作返回的数据结果")
    private Object data;

    public Result(){

    }

    public static Result tokenTimeOut(String errMessage,int errCode){
        return new Result(true,errMessage,errCode);
    }

    public static Result apptokenTimeOut(String errMessage,int errCode){
        return new Result(false,errMessage,errCode);
    }

    //成功快捷构造
    public static Result success(final Object data){
        return new Result(true,data);
    }

    public static Result successMs(String message){
        return new Result(true,message);
    }

    public static Result success(){
        return new Result(true);
    }
    //失败快捷构造
    public static Result fail(String errorMsg,int errorCode){
        return new Result(false,errorMsg,errorCode);
    }


    public Result addAttribute(String k,Object value){
        if (Objects.isNull(collections)){
            this.collections = new HashMap<>();
        }
        this.collections.put(k,value);
        this.setData(collections);
        return this;
    }

    //通用结果返回
    public Result(boolean status) {
        this.status = status;
    }



    public Result(boolean status, String errorMsg, int errorCode) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public Result(boolean status, Object data) {
        this.status = status;
        this.data = data;
    }
    //操作成功构造函数
    public Result(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}