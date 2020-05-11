package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;

import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.CommodityStyleImage;
import com.webdrp.entity.Image;
import com.webdrp.entity.vo.CommodityStyleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Component
public interface CommodityStyleMapper extends BaseMapper<CommodityStyle> {

    /**
     * 通过商品的id查找商品款式
     * @param commodityStyle
     * @return
     */
    List<CommodityStyle> loadCommodityStyleByCommodityId(CommodityStyle commodityStyle);

    /**
     * 根据id删除商品款式
     * @param commodityStyle
     * @return
     */
    int deleteById(CommodityStyle commodityStyle);


    /**
     * 根据商品的id删除商品款式
     * @param commodityStyle
     * @return
     */
    int deleteByCommodityId(CommodityStyle commodityStyle);


    int addCommodityStyleImage(@Param("commodityStyleId")Integer commodityStyleId, @Param("imageId")Integer imageId);

    int deleteCommodityStyleImageByCommodityStyleId(CommodityStyle commodityStyle);

    /**
     * 通过商品款式的id查找关联图片
     * @param id
     * @return
     */
    List<Image> findCommodityStyleImageByCommodityStyleId(Integer id);

    CommodityStyleVo findAllById(Integer id);

    Double findLowestPriceByCommodityId(Integer commodityId);
}
