package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuanming on 2018/8/9.
 */
@Component
public interface LogMapper extends BaseMapper<Log> {

    List<Log> findLogCountAvgByDate(@Param("start") String startDate,@Param("end") String endDate);

    int findLogCountUserByDate(@Param("start") String startDate,@Param("end") String endDate);

}
