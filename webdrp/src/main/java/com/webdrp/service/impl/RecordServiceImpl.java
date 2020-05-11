package com.webdrp.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.RecordStatus;
import com.webdrp.constant.RecordType;
import com.webdrp.entity.Order;
import com.webdrp.entity.Record;
import com.webdrp.entity.Member;
import com.webdrp.entity.dto.RecordDto;
import com.webdrp.entity.vo.RecordVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.RecordMapper;
import com.webdrp.mapper.MemberMapper;
import com.webdrp.service.RecordService;
import com.webdrp.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/13.
 */
@Service
public class RecordServiceImpl extends BaseServiceImpl<Record,RecordMapper>  implements RecordService {

    @Autowired
    RecordMapper recordMapper;


    @Autowired
    MemberMapper memberMapper;


    @Autowired
    AlipayServiceImpl alipayService;

    Logger logger = LoggerFactory.getLogger(LogService.class);


    @Override
    public Double sumRecord(int status) {
        return recordMapper.sumRecord(status);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void addRecordToUser(Order order, Member member, Double money, Integer grade, Integer recordType)throws Exception {
        //店家出账到平台用户
    }


    @Override
    public void toUserRealWallet(Record record){
        Record temp = recordMapper.findById(record.getId());
        //获取用户支付宝用户名密码
        try {
            AlipayFundTransUniTransferResponse response = alipayService.PayForUser(temp);
            if (response.isSuccess()){
                System.out.println("转账成功 response = [" +response.toString() + "]");
                temp.setStatus(RecordStatus.YES);
                temp.setNote("成功提现");
                temp.setOrderId(response.getOrderId());
            }else{
                temp.setStatus(RecordStatus.ERROR);
                temp.setNote(response.getSubMsg());
                System.out.println("转账失败 response = [" +response.toString() + "]");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw  new BusinessException("转账失败,发起请求失败！");
        }
        recordMapper.update(temp);
        return;
    }

    @Override
    public Double sumRecordByRichUserId(Integer richUserId) {
        return recordMapper.sumRecordByRichUserId(richUserId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public synchronized void addRecord(Member member, String zfbName, String zfb, Double money) {
        Member member1 = memberMapper.findById(member.getId());

        if (Objects.nonNull(member1)){
            //针对想搞事的
            if (money > 0){
                if (member1.getWallet() < money){
                    throw new BusinessException("提现失败，超出可用金额！");
                }
                double pub = money * 0.1; //扣 %0的手续费
                if (member1.getGrade().intValue() > 1){
//                    pub = money*0.03;
                }
                if (member1.getGrade().intValue() == 0){
                    throw new BusinessException("粉丝不能提现！");
                }
                double mmm = money - pub;
                member1.setWallet(member1.getWallet()-money);
                memberMapper.updateWallet(member1);
                Record record = new Record();
                record.setMoney(mmm);
                record.setNote("");
                record.setOpenid(member.getOpenid());
                record.setPhone(zfb);
                record.setrType(RecordType.ZHIFUBAO);
                record.setTargetRichUserId(member.getId());
                record.setZfb(zfb);
                record.setZfbName(zfbName);
                record.setPoundage(pub);
                //UUID唯一标示码生成 转账的时候使用
                record.setZfbOrderNo(UUIDUtils.getUUID());
                record.setStatus(RecordStatus.TODO);
                recordMapper.insert(record);
            }else{
                logger.debug("[addRecord]居然有人想提现负数，想搞事？");
                throw new BusinessException("提现不能为负数");
            }
            member1.setZfb(zfb);
            member1.setZfbname(zfbName);
            memberMapper.update(member1);
        }else{
            return;
        }
    }

    /**
     * 拒绝提现
     * @param record
     */
    @Override
   // @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void rejectoUserRealWallet(Record record) {
        Record temp = recordMapper.findById(record.getId());
        temp.setStatus(RecordStatus.ERROR);
        temp.setNote("拒绝提现！");
        recordMapper.update(temp);
    }


    /**
     * 统计
     * @param recordDto
     * @return
     */
    public List<RecordVo> sumStatusRecord(RecordDto recordDto){
        List<RecordVo> recordVos = recordMapper.sumRecordStatus(recordDto);
        return recordVos;
    }

    @Override
    public List<RecordVo> loadAll(RecordVo recordVo, Pager pager) {
        //
        long countAll = recordMapper.count(recordVo);
        pager = getPager(countAll,pager);
        return recordMapper.findVoByPage(recordVo, pager);
    }

}
