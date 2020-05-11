package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.CommodityVoIndex;
import com.webdrp.entity.vo.MenuCommodityVo;

import java.util.List;

public interface MenuCommodityService extends BaseService<MenuCommodity> {

    List<CommodityVoIndex> findCommodityByMenuId(Menu menu, Pager pager);

    List<CommodityVoIndex> findCommodityByPid(Integer pid, Pager pager);

    List<MenuCommodityVo> findAllVoByMenuId(MenuCommodity menuCommodity, Pager pager);

    void insertBatch(String commodityIds, Integer menuId);

    void deleteBatch(String commodityIds, Integer menuId);
}
