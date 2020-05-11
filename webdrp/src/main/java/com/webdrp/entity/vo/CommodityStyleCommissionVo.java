package com.webdrp.entity.vo;

import com.webdrp.entity.CommodityStyle;
import lombok.Data;
import java.util.List;

@Data
public class CommodityStyleCommissionVo extends CommodityStyle {
    private List<CommissionResultVo> resultVoList;
}
