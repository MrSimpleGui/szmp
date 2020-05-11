package com.webdrp.service.impl;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Log;
import com.webdrp.mapper.LogMapper;
import com.webdrp.util.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:54 2018/2/18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Service
public class LogService extends BaseServiceImpl<Log,LogMapper> {

    Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    LogMapper logMapper;

    //获取所有接口访问次数以及平均响应时间
    public List<Log> findLogCountAvgByDate(String startDate){
        String endDate="";
        if (Objects.isNull(startDate)){
            startDate = DateUtils.dateToYYYYMMDD()+" 00:00:00";
            endDate = DateUtils.dateToYYYYMMDD()+" 23:59:59";
        }else{
            endDate = startDate+" 23:59:59";
            startDate += " 00:00:00";

        }
        return logMapper.findLogCountAvgByDate(startDate,endDate);
    }


}
