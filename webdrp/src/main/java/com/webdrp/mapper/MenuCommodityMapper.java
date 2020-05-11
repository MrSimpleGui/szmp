package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.MenuCommodityVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuCommodityMapper extends BaseMapper<MenuCommodity> {

    List<Integer> findCommodityIdByMenuId(Integer menuId);

    int insertBatch(@Param("list") List<MenuCommodity> list);

    int deleteBatch(@Param("list") List<MenuCommodity> list, @Param("menuId") Integer menuId);

    int deleteBatchByMenuId(@Param("list") List<MenuCommodity> list);

    List<Integer> selectBatch(@Param("list")List<Integer> commodityIdList, @Param("menuId") Integer menuId);

    List<MenuCommodityVo> findMenuCommodityByMenuId(@Param("entity") MenuCommodity menuCommodity,
                                                    @Param("pager") Pager pager);

    long countByMenuId(@Param("menuIdList") List<Integer> menuIdList);

    List<Integer> findByMenuIds(@Param("menuIdList") List<Integer> menuIdList, @Param("pager") Pager pager);

    void deleteByCommodityId(MenuCommodity menuCommodity);
}
