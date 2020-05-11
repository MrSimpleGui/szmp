package com.webdrp.entity.vo;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.MenuCommodity;
import lombok.Data;

@Data
public class MenuCommodityVo extends MenuCommodity {
    private Commodity commodity;
}
