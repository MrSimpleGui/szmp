package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Spot;
import com.webdrp.mapper.SpotMapper;
import com.webdrp.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotServiceImpl extends BaseServiceImpl<Spot,SpotMapper> implements SpotService {

    @Autowired
    SpotMapper spotMapper;


    @Override
    public Spot findByCardNumAndTel(Spot spot) {
        return spotMapper.findByCardNumAndTel(spot);
    }

    @Override
    public List<Spot> findByRichUserId(Integer richuserId) {
        return spotMapper.findByRichUserId(richuserId);
    }
}
