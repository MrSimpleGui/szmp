package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.dto.CommodityDto;
import com.webdrp.entity.vo.CommodityAllVo;
import com.webdrp.entity.vo.CommodityVo;
import com.webdrp.entity.vo.CommodityVoIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by yuanming on 2018/8/10.
 */
public interface CommodityService extends BaseService<Commodity> {

    // List<Commodity> loadAll(Commodity commodity, Pager pager);


    /**
     * 删除商品
     *
     * @param commodity
     */
    void delete(Commodity commodity);

    /**
     * 添加商品
     *
     * @param commodityDto
     */
    void insert(CommodityDto commodityDto);

    /**
     * 更新商品
     * @param commodityDto
     */
    void update(CommodityDto commodityDto);

    /**
     * 上下架
     * @param commodity
     */
    void publish(Commodity commodity);

    /**
     * 商品详情
     * @param commodity
     * @return
     */
    CommodityVo detail(Commodity commodity);

    List<CommodityAllVo> findCommodityVo(Commodity commodity);

    CommodityAllVo findVoById(Integer commodityId);

    long findCommodityAllCount(Commodity commodity);
    //  获取所有的商品，带分页
    List<CommodityAllVo> findCommodityAllByPage(Commodity commodity, Pager pager);
    //通过供应商模型进行获取商品
    List<Commodity> findCommodityByVentorId(Commodity commodity);

    List<CommodityVoIndex> findIndexCommodity(Pager pager);

}
