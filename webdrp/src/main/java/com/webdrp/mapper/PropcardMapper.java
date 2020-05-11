package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Propcard;
import com.webdrp.entity.vo.PropcardVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PropcardMapper extends BaseMapper<Propcard> {

    Propcard findByUserAndStyle(@Param("entity") Propcard propcard);

    List<PropcardVo> findVoByMemberId(Integer richUserId);
}
