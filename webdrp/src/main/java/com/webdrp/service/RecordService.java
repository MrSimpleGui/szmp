package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Order;
import com.webdrp.entity.Record;
import com.webdrp.entity.Member;
import com.webdrp.entity.dto.RecordDto;
import com.webdrp.entity.vo.RecordVo;

import java.util.List;

/**
 * Created by yuanming on 2018/8/13.
 */
public interface RecordService extends BaseService<Record> {

    Double sumRecord(int status);

    //发起提现
    void addRecordToUser(Order order, Member member, Double money, Integer grade, Integer recordType)throws Exception;

    void toUserRealWallet(Record record);

    Double sumRecordByRichUserId(Integer richUserId);

    /**
     * C 端发起提现接口
     * @param member
     * @param zfbName
     * @param zfb
     * @param money
     */
    void  addRecord(Member member, String zfbName, String zfb, Double money);

    void rejectoUserRealWallet(Record record);

    List<RecordVo> sumStatusRecord(RecordDto recordDto);

    List<RecordVo> loadAll(RecordVo recordVo, Pager pager);
}
