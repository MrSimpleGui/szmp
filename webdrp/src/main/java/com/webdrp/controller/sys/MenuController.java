package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.MenuVo;
import com.webdrp.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "后台接口 菜单相关")
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController<Menu, Integer> {

    @Autowired
    private MenuService menuService;

    @Override
    public BaseService<Menu> getService() {
        return menuService;
    }

    public Result loadAll(@RequestHeader(value="token") String token, Menu menu, Pager pager){
//        List<MenuVo> list = menuService.loadAllVo(menu, pager);
        List<Menu> list = getService().loadAll(menu, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    public Result delete(@RequestHeader(value="token") String token,Integer id) {
        Menu menu = new Menu();
        menu.setId(id);
        menuService.delete(menu);
        return Result.success();
    }

    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    @ApiOperation("发布")
    @ResponseBody
    public Result publish(@RequestHeader(value="token") String token,Menu menu) {
        //发布菜单
        menuService.publish(menu);
        return Result.success();
    }

    @RequestMapping(value = "/findAllFirstMenu",method = RequestMethod.GET)
    @ApiOperation("获取所有一级菜单")
    @ResponseBody
    public Result findAllFirstMenu(@RequestHeader(value="token") String token,Menu menu) {
        //发布菜单
        List<Menu> list = menuService.findAllFirstMenu(menu);
        return Result.success().addAttribute("list",list);
    }



}
