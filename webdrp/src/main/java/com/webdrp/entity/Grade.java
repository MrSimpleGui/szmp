package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yuanming on 2018/8/22.
 */
public class Grade  extends BaseBean{

    @ApiModelProperty("等级排序")
    private Integer sort;

    @ApiModelProperty("等级名称")
    private String name;

    @ApiModelProperty("等级ID 对应用户表中的grade")
    private Integer rank;

    @ApiModelProperty("团队人数（满足数量升级，建议会员才有）")
    private Integer teamNumber;

    @ApiModelProperty("直推人数（满足数量升级，建议粉丝会员有）")
    private Integer recommendNumber;

    @ApiModelProperty("指定分佣模式（粉丝购买指定分佣模式产品时可升级，可不设置）")
    private String specificMode;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public Integer getRecommendNumber() {
        return recommendNumber;
    }

    public void setRecommendNumber(Integer recommendNumber) {
        this.recommendNumber = recommendNumber;
    }

    public String getSpecificMode() {
        return specificMode;
    }

    public void setSpecificMode(String specificMode) {
        this.specificMode = specificMode;
    }
}
