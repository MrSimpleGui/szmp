package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.CommissionMode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommissionModeMapper extends BaseMapper<CommissionMode> {

    List<Integer> findModeIdList();

    List<CommissionMode> findByIds(@Param("list") List<Integer> list);
}
