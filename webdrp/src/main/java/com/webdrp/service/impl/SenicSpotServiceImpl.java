package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.SenicSpot;
import com.webdrp.mapper.SenicSpotMapper;
import com.webdrp.service.SenicSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenicSpotServiceImpl extends BaseServiceImpl<SenicSpot,SenicSpotMapper> implements SenicSpotService {

    @Autowired
    SenicSpotMapper senicSpotMapper;

    @Override
    public SenicSpot findBySenicId(String senicId) {
        return senicSpotMapper.findBySenicId(senicId);
    }
}
