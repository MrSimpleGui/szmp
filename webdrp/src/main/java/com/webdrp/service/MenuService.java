package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Menu;
import com.webdrp.entity.vo.MenuVo;

import java.util.List;

public interface MenuService extends BaseService<Menu> {

    List<Menu> findFirstMenu(Pager pager);

    List<Menu> findSecondMenu(Menu menu, Pager pager);

    List<Menu> findFunctionMenu();

    void publish(Menu menu);

    List<Menu> findAllFirstMenu(Menu menu);
}
