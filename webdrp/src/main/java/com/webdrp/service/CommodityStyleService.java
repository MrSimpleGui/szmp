package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.common.Pager;
import com.webdrp.entity.dto.CommodityStyleDto;
import com.webdrp.entity.vo.CommodityStyleCommissionVo;
import com.webdrp.entity.vo.CommodityStyleVo;

import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 */
public interface CommodityStyleService extends BaseService<CommodityStyle> {

    List<CommodityStyle> loadAll(CommodityStyle commodityStyle, Pager pager);

    List<CommodityStyleCommissionVo> loadAllVo(CommodityStyle commodityStyle, Pager pager);

    /**
     * 添加款式
     * @param commodityStyleDto
     */
    void insert(CommodityStyleDto commodityStyleDto);

    /**
     * 删除款式
     * @param commodityStyle
     */
    void delete(CommodityStyle commodityStyle);

    /**
     * 更新款式
     * @param commodityStyleDto
     */
    void update(CommodityStyleDto commodityStyleDto);


    /**
     * 商品款式详情
     * @param commodityStyle
     * @return
     */
    CommodityStyleVo detail(CommodityStyle commodityStyle);

}
