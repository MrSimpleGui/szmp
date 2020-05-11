package com.webdrp.controller.web;

import com.webdrp.common.*;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Config;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.CommodityVoIndex;
import com.webdrp.err.BusinessException;
import com.webdrp.service.MenuCommodityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wechat/api")
public class CMenuCommodityController {

    @Autowired
    private MenuCommodityService menuCommodityService;



//    @ApiOperation("根据menuId查询菜单下的商品")
//    @GetMapping("findCommodityByMenuId")
//    public Result findCommodityByMenuId(@RequestHeader(value="token") String token, Integer menuId,
//                                        Pager pager){
//        if(menuId == 0){
//            throw new BusinessException("菜单id不能为0!");
//        }
//        List<Commodity> list = menuCommodityService.findCommodityByMenuId(menuId);
//        return Result.success().addAttribute("list",list);
//    }

    @ApiOperation("根据menuId查询菜单下的商品")
    @GetMapping("findCommodityByMenuId")
    public Result findCommodityByMenuId(Menu menu, Pager pager){
        List<CommodityVoIndex> list = menuCommodityService.findCommodityByMenuId(menu,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @ApiOperation("获取父菜单下的商品")
    @GetMapping("findCommodityByPid")
    public Result findCommodityByPid(Integer pid, Pager pager){
        List<CommodityVoIndex> list = menuCommodityService.findCommodityByPid(pid, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

}
