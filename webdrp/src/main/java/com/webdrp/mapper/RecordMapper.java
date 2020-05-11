package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Income;
import com.webdrp.entity.Record;
import com.webdrp.entity.dto.RecordDto;
import com.webdrp.entity.vo.RecordVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuanming on 2018/8/13.
 */
@Component
public interface RecordMapper extends BaseMapper<Record>{

    Double sumRecord(int status);

    Double sumRecordByRichUserId(Integer richUserId);

    void updateRecodeStatus(Record record);

    List<RecordVo> sumRecordStatus(RecordDto recordDto);

    List<RecordVo> findVoByPage(@Param("entity") RecordVo recordVo, @Param("pager") Pager pager);
}
