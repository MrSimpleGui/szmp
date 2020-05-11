package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.MenuVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.MenuCommodityMapper;
import com.webdrp.mapper.MenuMapper;
import com.webdrp.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuCommodityMapper menuCommodityMapper;

    public void delete(Menu menu) {
        try {
            //需要级联删除子菜单和菜单商品
            Menu sub = new Menu();
            sub.setPid(menu.getId());
            List<Menu> subMenus = menuMapper.findAll(sub);
            List<Menu> allMenus = new ArrayList<>();
            //如果存在子菜单，先删除商品
            if(subMenus.size() != 0){
                List<MenuCommodity> deleteList = new ArrayList<>();
                //删除商品
                subMenus.forEach(item->{
                    allMenus.add(item);
                    MenuCommodity menuCommodity = new MenuCommodity();
                    menuCommodity.setMenuId(item.getId());
                    menuCommodity.beforeDelete();
                    deleteList.add(menuCommodity);
                });
                menuCommodityMapper.deleteBatchByMenuId(deleteList);
            }
            //删除菜单
            allMenus.add(menu);
            allMenus.forEach(item->{
                item.beforeDelete();
            });
            menuMapper.deleteBatchByIds(allMenus);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }

    }

    /**
    * @Description 获取已发布的一级菜单
    * @Param pager
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/9 0009 上午 11:38
    **/
    @Override
    public List<Menu> findFirstMenu(Pager pager) {
        try {
            Menu menu = new Menu();
            menu.setPublish(1);
            long countAll = menuMapper.count(menu);
            pager = getPager(countAll,pager);
            //获取一级菜单
            return menuMapper.findFirstMenu(menu, pager);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }

    @Override
    public List<Menu> findSecondMenu(Menu menu, Pager pager) {
        try {
            menu.setPublish(1);
            long countAll = menuMapper.count(menu);
            pager = getPager(countAll,pager);
            return menuMapper.findSecondMenu(menu, pager);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }

    @Override
    public List<Menu> findFunctionMenu() {
        try {
            return menuMapper.findFunctionMenu();
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }

    @Override
    public void publish(Menu menu) {
        try {
            List<Integer> menuIdList = new ArrayList<>();
            menuIdList.add(menu.getId());
            //判断是否是父菜单
            Menu selectMenu = menuMapper.findById(menu.getId());
            //父菜单，级联处理
            if(selectMenu.getPid() == null){
                //获取所有子菜单
                List<Menu> subList = menuMapper.findMenuByPidWithPublish(selectMenu.getPid(),
                        menu.getPublish() == 1 ? 0 : 1);
                if(subList.size() != 0){
                    menuIdList.addAll(subList.stream().map(Menu::getId).collect(Collectors.toList()));
                }
            }
            menuMapper.publishByMenuIds(menuIdList, menu.getPublish());
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }

    /**
    * @Description 获取全部一级菜单，不分页
    * @Param
    * @Return
    * @Author Mr.Simple
    * @Date 2020/4/10 0010 下午 4:21
    **/
    @Override
    public List<Menu> findAllFirstMenu(Menu menu) {
        return menuMapper.findAllFirstMenu(menu);
    }
}
