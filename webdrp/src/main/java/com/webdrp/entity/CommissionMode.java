package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionMode extends BaseBean {

    @ApiModelProperty("分佣模式名称")
    private String name;
}
