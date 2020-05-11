package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.CommodityVoIndex;
import com.webdrp.entity.vo.MenuCommodityVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CommodityMapper;
import com.webdrp.mapper.MenuCommodityMapper;
import com.webdrp.mapper.MenuMapper;
import com.webdrp.service.MenuCommodityService;
import com.webdrp.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuCommodityServiceImpl extends BaseServiceImpl<MenuCommodity, MenuCommodityMapper> implements
        MenuCommodityService {

    @Autowired
    private MenuCommodityMapper menuCommodityMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private MenuMapper menuMapper;

    public void add(MenuCommodity menuCommodity) {
        try {
            List<MenuCommodity> list = menuCommodityMapper.findAll(menuCommodity);
            if(list.size() != 0){
                throw new BusinessException("该商品已在该菜单中");
            }
            menuCommodity.beforeCreate();
            menuCommodityMapper.insert(menuCommodity);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }


    /**
    * @Description 通过菜单id查找
    * @Param menu pager
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/9 0009 上午 11:42
    **/
    public List<CommodityVoIndex> findCommodityByMenuId(Menu menu, Pager pager){
        if(Objects.nonNull(menu.getPublish()) && menu.getPublish().equals(0)){
            return findIndexCommodity(menu, pager);
        }
        Menu selectMenu = menuMapper.findById(menu.getId());
        //父菜单，调用findbypid接口
        if(Objects.isNull(selectMenu.getPid())){
            return findCommodityByPid(selectMenu.getId(), pager);
        }
        MenuCommodity menuCommodity = new MenuCommodity();
        menuCommodity.setMenuId(menu.getId());
        List<CommodityVoIndex> list = new ArrayList<>();
        long countAll = menuCommodityMapper.count(menuCommodity);
        pager = getPager(countAll,pager);
        List<MenuCommodity> commodityList = menuCommodityMapper.loadAll(menuCommodity, pager);
        if(commodityList.size() != 0){
            List<Integer> commodityIdList = commodityList.stream()
                    .map(MenuCommodity::getCommodityId).collect(Collectors.toList());
            list = commodityMapper.findVoIndexByIds(commodityIdList);
        }
        return list;
    }

    @Override
    public List<CommodityVoIndex> findCommodityByPid(Integer pid, Pager pager) {
        //获取子菜单
        List<Menu> subMenuList = menuMapper.findMenuByPidWithPublish(pid, 1);
        return findCommodity(subMenuList, pager);
    }

    /**
    * @Description 获取首页商品
    * @Param menu pager
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/9 0009 上午 11:28
    **/
    public List<CommodityVoIndex> findIndexCommodity(Menu menu, Pager pager){
        //获取该下菜单的所有子菜单
        List<Menu> subMenuList = menuMapper.findMenuByPidWithPublish(menu.getId(), menu.getPublish());
        return findCommodity(subMenuList, pager);
    }

    private List<CommodityVoIndex> findCommodity(List<Menu> subMenuList, Pager pager){
        List<CommodityVoIndex> list = new ArrayList<>();
        if(subMenuList.size() != 0){
            List<Integer> meunIdList = subMenuList.stream().map(Menu::getId).collect(Collectors.toList());
            long countAll = menuCommodityMapper.countByMenuId(meunIdList);
            pager = getPager(countAll, pager);
            List<Integer> commodityIdList = menuCommodityMapper.findByMenuIds(meunIdList, pager);
            if(commodityIdList.size() != 0){
                //查找出所以商品信息
                list = commodityMapper.findVoIndexByIds(commodityIdList);
            }
        }
        return list;
    }

    @Override
    public List<MenuCommodityVo> findAllVoByMenuId(MenuCommodity menuCommodity, Pager pager) {
        long countAll = menuCommodityMapper.count(menuCommodity);
        pager = getPager(countAll,pager);
        List<MenuCommodityVo> list = menuCommodityMapper.findMenuCommodityByMenuId(menuCommodity, pager);
        return list;
    }

    @Override
    public void insertBatch(String commodityIds, Integer menuId) {
        try {
            //解析commodityIds
            List<MenuCommodity> list = new ArrayList<>();
            List<Integer> commodityIdList = StringUtils.strToInteger(commodityIds);
            //批量查询，查看是否已存在
            List<Integer> idList = menuCommodityMapper.selectBatch(commodityIdList, menuId);
            if(idList.size() != 0){
                throw new BusinessException("部分商品已在该菜单中！");
            }
            commodityIdList.forEach(item->{
                MenuCommodity menuCommodity = new MenuCommodity();
                menuCommodity.setCommodityId(item);
                menuCommodity.setMenuId(menuId);
                menuCommodity.beforeCreate();
                list.add(menuCommodity);
            });
            menuCommodityMapper.insertBatch(list);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }

    }

    @Override
    public void deleteBatch(String commodityIds, Integer menuId) {
        try {
            //解析commodityIds
            List<MenuCommodity> list = new ArrayList<>();
            List<Integer> commodityIdList = StringUtils.strToInteger(commodityIds);
            commodityIdList.forEach(item->{
                MenuCommodity menuCommodity = new MenuCommodity();
                menuCommodity.setCommodityId(item);
//                menuCommodity.setMenuId(menuId);
                menuCommodity.beforeDelete();
                list.add(menuCommodity);
            });
            menuCommodityMapper.deleteBatch(list, menuId);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }
}
