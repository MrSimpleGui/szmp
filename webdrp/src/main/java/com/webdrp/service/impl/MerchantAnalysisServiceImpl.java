package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.MerchantAnalysis;
import com.webdrp.entity.Member;
import com.webdrp.mapper.MerchantAnalysisMapper;
import com.webdrp.service.MerchantAnalysisService;
import com.webdrp.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 00:47 2019-11-15
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Service
public class MerchantAnalysisServiceImpl extends BaseServiceImpl<MerchantAnalysis, MerchantAnalysisMapper>  implements MerchantAnalysisService {

    @Autowired
    MerchantAnalysisMapper merchantAnalysisMapper;

    @Autowired
    MemberService memberService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public MerchantAnalysis findByRichUserId(Integer richUserId) {
        return merchantAnalysisMapper.findByRichUserId(richUserId);
    }

    @Override
    public List<MerchantAnalysis> findByManagerId(Integer managerId) {
        return merchantAnalysisMapper.findByManagerId(managerId);
    }

    @Override
    public void initMerchant(Integer richUserId) {
        Member member = memberService.findOne(richUserId);
        MerchantAnalysis merchantAnalysis = merchantAnalysisMapper.findByRichUserId(member.getId());
        if (Objects.isNull(merchantAnalysis)){
            merchantAnalysis = new MerchantAnalysis();
            merchantAnalysis.setManagerId(member.getJid());
            merchantAnalysis.setRichUserId(member.getId());
            merchantAnalysis.setDayCount(0);
            merchantAnalysis.setSumCount(0);
            merchantAnalysis.setVipCount(0);
            merchantAnalysis.setTeamCount(0);

            if (Objects.isNull(member.getJid())){
                logger.error("大区经理为空！");
                return;
            }

            merchantAnalysisMapper.insert(merchantAnalysis);
        }
    }

    /**
     * 统计经理层级别的人团队的总单量
     *
     * @param managerId
     * @return
     */
    @Override
    public int countByManagerId(Integer managerId) {
        return merchantAnalysisMapper.countByManagerId(managerId);
    }

    /**
     * 聚合大区经理下面的城市经理的总人数
     *
     * @param managerId
     * @return
     */
    @Override
    public int countManagerTeamUser(Integer managerId) {
        return merchantAnalysisMapper.countManagerTeamUser(managerId);
    }

    /**
     * 带的人自己今今天成交
     *
     * @param managerId
     * @return
     */
    @Override
    public int countDayByManagerId(Integer managerId) {
        return merchantAnalysisMapper.countDayByManagerId(managerId);
    }


}
