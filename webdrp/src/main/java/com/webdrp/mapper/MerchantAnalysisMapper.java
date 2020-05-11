package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.MerchantAnalysis;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:18 2019-11-13
 * @mail: zh878998515@gmail.com
 * @Description:
 */

@Component
public interface MerchantAnalysisMapper extends BaseMapper<MerchantAnalysis> {

    /**
     * 根据用户获取这个用户的统计数据
     * @param richUserId
     * @return
     */
    MerchantAnalysis findByRichUserId(Integer richUserId);

    /**
     * 根据管理人ID查询团队
     * @param managerId
     * @return
     */
    List<MerchantAnalysis> findByManagerId(Integer managerId);

    /**
     * 统计经理人的团队订单数量
     * @param managerId
     * @return
     */
    int countByManagerId(Integer managerId);

    /**
     * 当天成交
     * @param managerId
     * @return
     */
    int countDayByManagerId(Integer managerId);

    /**
     * 统计大区团队人数
     * @param managerId
     * @return
     */
    int countManagerTeamUser(Integer managerId);
}
