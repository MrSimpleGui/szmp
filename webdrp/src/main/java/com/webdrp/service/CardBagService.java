package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.CardBag;
import com.webdrp.entity.Cardbaglog;
import com.webdrp.entity.Cardbaguse;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:14 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface CardBagService extends BaseService<CardBag> {

    /**
     * 发卡给某人，或者送卡给某人
     * @param cardbaglog
     */
    void giveCardToUser(Cardbaglog cardbaglog);

    void useCard(Cardbaguse cardbaguse);

    CardBag findByRichUserId(Integer userId);
}
