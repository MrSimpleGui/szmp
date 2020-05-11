package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.MerchantAnalysis;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 00:46 2019-11-15
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface MerchantAnalysisService extends BaseService<MerchantAnalysis> {

    MerchantAnalysis findByRichUserId(Integer richUserId);

    List<MerchantAnalysis> findByManagerId(Integer managerId);

    void initMerchant(Integer richUserId);

    /**
     * 统计经理层级别的人团队的总单量
     * @param managerId
     * @return
     */
    int countByManagerId(Integer managerId);

    /**
     * 聚合大区经理下面的城市经理的总人数
     * @param managerId
     * @return
     */
    int countManagerTeamUser(Integer managerId);

    /**
     * 带的人自己今今天成交
     * @param managerId
     * @return
     */
    int countDayByManagerId(Integer managerId);
}
