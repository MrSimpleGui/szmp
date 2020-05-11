package com.webdrp.util;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by yuanming on 2017/8/20.
 * 七牛工具类
 */

public class QiNiuUtils {


    /**
     * AccessKey 公钥
     * SecretKey 私钥
     * Bucket 空间名
     * BucketDomain 空间对应的域名
     * UploadAPI 七牛提供的上传接口 https://developer.qiniu.com/kodo/manual/1671/region-endpoint
     */
  //  私人
    private static final String AccessKey = "eQuMfKPTED-kfHOAz-mPA1JF_oZebjaMcDdfrnRF";
    private static final String SecretKey = "TJR0LPz_hW02Pd2G8TDEQ-ZuqgwLKvePQ00obN9V";
    private static final String Bucket = "dropvote";
    private static final String BucketDomain = "http://qn.yywluo.cn";
    private static final String UploadAPI = "http://upload-z2.qiniu.com";
//公司
//    private static final String AccessKey = "6DBzCtWrUs0QytoDuY_tR98SIaSfwNMvUV5Mg2OR";
//    private static final String SecretKey = "0g5sS_KqjWUUeq2hCEJsyOo5oXzKASClSlYh6CxN";
//    private static final String Bucket = "lvpaitest";
//    private static final String BucketDomain = "http://p7ui3rmvk.bkt.clouddn.com";
//    private static final String UploadAPI = "http://upload-z2.qiniu.com";

    /**
     * 获取七牛云上传的token
     * @return
     */
    public static String GetToken(){
        Auth auth = Auth.create(AccessKey, SecretKey);
        long expireSeconds = 3600;
        StringMap policy = new StringMap();
        policy.put("saveKey", "img/$(etag)$(ext)");
        String upToken = auth.uploadToken(Bucket, null, expireSeconds, policy);
        System.out.println("Qiniu upToken: " + upToken);
        return upToken;
    }

    /**
     * 获取上传的路径
     * @return
     */
    public static String getUploadAPI(){
        return UploadAPI;
    }
    /**
     * 获取访问的路径域名
     * @return
     */
    public static String getBucketDomain(){
        return BucketDomain;
    }



}
