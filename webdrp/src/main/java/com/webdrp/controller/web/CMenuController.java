package com.webdrp.controller.web;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Menu;
import com.webdrp.err.BusinessException;
import com.webdrp.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/wechat/api")
public class CMenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/findFirstMenu",method = RequestMethod.GET)
    @ApiOperation("获取一级菜单")
    @ResponseBody
    public Result findFirstMenu(Pager pager){
        List<Menu> list = menuService.findFirstMenu(pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @RequestMapping(value = "/findSecondMenu",method = RequestMethod.GET)
    @ApiOperation("获取二级菜单")
    @ResponseBody
    public Result findSecondMenu(Menu menu, Pager pager){
        if(Objects.isNull(menu) || Objects.isNull(menu.getPid())){
            throw new BusinessException("pid不能为空");
        }
        List<Menu> list = menuService.findSecondMenu(menu, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @RequestMapping(value = "/findFunctionMenu",method = RequestMethod.GET)
    @ApiOperation("获取功能性菜单")
    @ResponseBody
    public Result findFunctionMenu(){
        List<Menu> list = menuService.findFunctionMenu();
        return Result.success().addAttribute("list",list);
    }



}
