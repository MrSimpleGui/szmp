package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Income;
import com.webdrp.entity.Spot;

import java.util.List;

public interface SpotMapper extends BaseMapper<Spot> {

    Spot findByCardNumAndTel(Spot spot);

    List<Spot> findByRichUserId(Integer richuserId);
}
