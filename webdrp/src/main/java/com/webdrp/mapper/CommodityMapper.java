package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityImage;
import com.webdrp.entity.Image;
import com.webdrp.entity.vo.CommodityAllVo;
import com.webdrp.entity.vo.CommodityVo;
import com.webdrp.entity.vo.CommodityVoIndex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CommodityMapper extends BaseMapper<Commodity> {

    //添加商品图
    void addCommodityImage(@Param("commodityId") Integer commodityId, @Param("imageId") Integer imageId);

    /**
     * 删除商品图片关联表数据
     *
     * @param commodity
     */
    void deleteCommodityImageByCommodityId(Commodity commodity);


    /**
     * 通过商品id查找关联图片
     *
     * @param id
     * @return
     */

    List<Image> findCommodityImageByCommodityId(Integer id);


    List<CommodityAllVo> findCommodityVo(@Param(value = "entity") Commodity commodity);

    long findCommodityAllCount(@Param(value = "entity") Commodity commodity);
//  获取所有的商品，带分页
    List<CommodityAllVo> findCommodityAllByPage(@Param(value = "entity") Commodity commodity,@Param(value = "pager") Pager pager);

    CommodityAllVo findVoById(@Param("id") Integer commodityId);

    List<Commodity> findCommodityByVentorId(Commodity commodity);
    //带缓存
    List<Commodity> findCommodityByVentorId1(Integer ventorId);

    List<Commodity> findCommodityByIds(@Param("commodityIdList")List<Integer> commodityIdList);

    List<CommodityVoIndex> findVoIndexByIds(@Param("commodityIdList") List<Integer> commodityIdList);

    List<CommodityVoIndex> findVoIndexByClassId(@Param(value = "entity") Commodity commodity,@Param(value = "pager") Pager pager);
}
