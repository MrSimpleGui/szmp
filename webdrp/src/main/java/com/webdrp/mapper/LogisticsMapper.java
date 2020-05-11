package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Analysis;
import com.webdrp.entity.Logistics;
import org.springframework.stereotype.Component;

/**
 * Created by yuanming on 2018/8/15.
 */
@Component
public interface LogisticsMapper extends BaseMapper<Logistics>{

    Logistics findByNumber(String number);
}
