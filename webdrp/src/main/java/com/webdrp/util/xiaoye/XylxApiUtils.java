package com.webdrp.util.xiaoye;

import com.alibaba.fastjson.JSON;
import com.webdrp.common.Result;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;

public class XylxApiUtils {

    /**
     * 检测是否可以购买
     * @throws IOException
     */
    public static Result verifyOrder(String host, String auth, Integer orderId, String phone) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HashMap<String,String> paramsMap=new HashMap<>();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            //追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        Request request = new Request.Builder()
                .url(host + "/sys/order/verifyOrder?orderId=" + orderId + "&phone=" + phone)
                .post(builder.build())
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("authorization", auth)
                .build();
        Response response = client.newCall(request).execute();
        String body = response.body().string();
//        System.out.println(body);
        return JSON.parseObject(body,Result.class);
    }
}
