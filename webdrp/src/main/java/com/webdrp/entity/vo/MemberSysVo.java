package com.webdrp.entity.vo;

import com.webdrp.entity.MerchantAnalysis;
import com.webdrp.entity.Member;

/**
 * @Author: zhang yuan ming
 * @Date: create in 20:45 2019-11-14
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class MemberSysVo extends Member {

    private MerchantAnalysis merchantAnalysis;
    // 大区经理
    private Member topUser;

    public MerchantAnalysis getMerchantAnalysis() {
        return merchantAnalysis;
    }

    public void setMerchantAnalysis(MerchantAnalysis merchantAnalysis) {
        this.merchantAnalysis = merchantAnalysis;
    }

    public Member getTopUser() {
        return topUser;
    }

    public void setTopUser(Member topUser) {
        this.topUser = topUser;
    }
}
