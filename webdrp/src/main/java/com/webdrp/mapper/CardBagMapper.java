package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.CardBag;
import org.springframework.stereotype.Component;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:14 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public interface CardBagMapper extends BaseMapper<CardBag> {

    CardBag findByRichUserId(Integer richUserId);

    void updateNumber(CardBag cardBag);

    Integer findNumberByRichUserId(Integer richUserId);
}
