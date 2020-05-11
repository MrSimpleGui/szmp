package com.webdrp.service;

import com.webdrp.common.Result;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 21:10 2019-10-22
 * @mail: zh878998515@gmail.com
 * @Description:卡获取
 */

public interface VipCardService {

    /**
     * 开通会员
     * @param tel
     * @param realName
     * @param idCard
     */
    Result openVip(String tel, String realName, String idCard);

    /**
     * 获取会员卡号
     * @return
     */
    String getVipCard();

    /**
     * 获取一定数量的会员卡
     * @param number
     * @return
     */
    List<String> getVipCard(Integer number);

    /**
     * 检测是否可以购卡
     * @param tel
     * @return
     * @throws IOException
     */
    Result checkoutUserCanBuy(String tel) throws IOException;

}
