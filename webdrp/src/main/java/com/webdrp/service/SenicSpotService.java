package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Role;
import com.webdrp.entity.SenicSpot;

public interface SenicSpotService extends BaseService<SenicSpot> {

    SenicSpot findBySenicId(String senicId);
}
