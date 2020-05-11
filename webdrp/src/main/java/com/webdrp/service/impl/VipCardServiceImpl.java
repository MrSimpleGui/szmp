package com.webdrp.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.util.Json;
import com.webdrp.common.Result;
import com.webdrp.service.VipCardService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 21:13 2019-10-22
 * @mail: zh878998515@gmail.com
 * @Description:会员卡
 */
@Service
public class VipCardServiceImpl implements VipCardService {

    @Value("${xiaoye.openvip.url}")
    String openUrl;

    @Value("${auth.token}")
    String token;



    /**
     * 开通会员
     *
     * @param tel
     * @param realName
     * @param idCard
     */
    @Override
    public Result openVip(String tel, String realName, String idCard) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        // 数据
        map.add("tel", tel);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Result> entity = restTemplate.postForEntity(openUrl + "/open/user/openVip",request,Result.class);
        HttpStatus statusCode = entity.getStatusCode();
        System.out.println("statusCode.is2xxSuccessful()"+statusCode.is2xxSuccessful());

        Result body = entity.getBody();
        System.out.println("entity.getBody()"+body.isStatus());
        return body;
    }

    /**
     * 获取会员卡号
     *
     * @return
     */
    @Override
    public String getVipCard() {
        return null;
    }

    /**
     * 获取一定数量的会员卡
     *
     * @param number
     * @return
     */
    @Override
    public List<String> getVipCard(Integer number) {
        return null;
    }

    @Override
    public Result checkoutUserCanBuy(String tel) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(openUrl + "/open/user/checkUser?tel="+tel)
                .get()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Result result = JSON.parseObject(response.body().string(),Result.class);
        return result;
    }
}
