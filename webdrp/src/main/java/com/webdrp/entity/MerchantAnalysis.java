package com.webdrp.entity;

import com.webdrp.common.BaseBean;

/**
 * @Author: zhang yuan ming
 * @Date: create in 23:12 2019-11-13
 * @mail: zh878998515@gmail.com
 * @Description:代理商统计
 */
public class MerchantAnalysis extends BaseBean {

    // 城市经理ID
    private Integer richUserId;
    // 大区经理ID
    private Integer managerId;
    // 今日人数
    private Integer dayCount;
    // 总单量
    private Integer sumCount;
    // 团队人数
    private Integer teamCount;
    // 会员人数
    private Integer vipCount;

    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
    }
}
