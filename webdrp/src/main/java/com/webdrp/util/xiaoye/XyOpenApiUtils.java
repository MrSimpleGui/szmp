package com.webdrp.util.xiaoye;

import com.alibaba.fastjson.JSON;
import com.webdrp.common.Result;
import okhttp3.*;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: zhang yuan ming
 * @Date: create in 14:48 2020-04-22
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class XyOpenApiUtils {

//    public static String auth = "Basic YTQ5N2RkNTZjZTc3NDM1MmE0ZDNhYTA2ZmQ0YTM4N2Y6ZjVlMWM5NDM2MDUwNDEyNDlkMjZjYzVkMTVmYTZlYjk=";
//
//    public static String host = "http://test.xiaoyeok.com";
    /**
     * 检测是否可以购买
     * @throws IOException
     */
    public static Result checkUserCanBuy(String host,String auth,String tel) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(host + "/openapi/agent/checkCanBuy?tel="+tel)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("authorization", auth)
                .build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        System.out.println(body);
        return JSON.parseObject(body,Result.class);
    }

    /**
     * 开通
     * @param tel
     * @return
     * @throws IOException
     */
    public static Result openVip(String host,String auth,String tel) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HashMap<String,String> paramsMap=new HashMap<>();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }


        Request request = new Request.Builder()
                .url(host+"/openapi/agent/openVip?tel="+tel+"&idCard=&name=")
                .post(builder.build())
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("authorization", auth)
                .build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
        return JSON.parseObject(body,Result.class);
    }


    public static void main(String[] args) throws IOException {
        String auth = "Basic YTQ5N2RkNTZjZTc3NDM1MmE0ZDNhYTA2ZmQ0YTM4N2Y6ZjVlMWM5NDM2MDUwNDEyNDlkMjZjYzVkMTVmYTZlYjk=";
        String host = "http://test.xiaoyeok.com";
        Result result = openVip(host,auth,"13798958173");
        System.out.println(JSON.toJSON(result));
    }


}
