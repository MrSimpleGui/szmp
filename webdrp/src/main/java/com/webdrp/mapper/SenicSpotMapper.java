package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.SenicSpot;

public interface SenicSpotMapper extends BaseMapper<SenicSpot> {

    SenicSpot findBySenicId(String senicId);
}
