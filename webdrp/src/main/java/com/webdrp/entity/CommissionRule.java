package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionRule extends BaseBean {

    @ApiModelProperty("等级rank值")
    private Integer gradeRank;

    @ApiModelProperty("分佣模式id")
    private Integer modeId;

    @ApiModelProperty("分佣模式类型 0表示百分比 1表示固定金额")
    private Integer type;

    @ApiModelProperty("一级分佣百分比/金额")
    private Double firstLevel;

    @ApiModelProperty("二级分佣百分比/金额")
    private Double secondLevel;

    @ApiModelProperty("团队奖百分比（占商品总价的百分比）/金额")
    private Double teamReward;

    @ApiModelProperty("平级分成百分比/金额")
    private Double sidewayCommission;

    @ApiModelProperty("平级分成人数")
    private Integer sidewayNumber;

    @ApiModelProperty("一二级佣金百分比（佣金除以去掉快递费的订单总价）/金额")
    private Double commissionProp;

}

