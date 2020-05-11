package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Spot;

import java.util.List;

public interface SpotService extends BaseService<Spot> {

    Spot findByCardNumAndTel(Spot spot);

    List<Spot> findByRichUserId(Integer richuserId);
}
