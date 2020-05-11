package com.webdrp.entity.vo;

import com.webdrp.entity.Member;

/**
 * @Author: zhang yuan ming
 * @Date: create in 20:45 2019-11-14
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class MemberVo {

    private Member member;

    // 团队人数
    private int teamCount;

    // 累计销售
    private int sumCount;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }
}
