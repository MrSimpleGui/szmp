package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Menu;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.MenuCommodityVo;
import com.webdrp.err.BusinessException;
import com.webdrp.service.MenuCommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(tags = "后台接口 菜单商品相关")
@RestController
@RequestMapping("/sys/menucommodity")
public class MenuCommodityController extends BaseController<MenuCommodity, Integer> {

    @Autowired
    private MenuCommodityService menuCommodityService;

    @Override
    public BaseService<MenuCommodity> getService() {
        return menuCommodityService;
    }

    public Result delete(@RequestHeader(value="token") String token, Integer id) {
        MenuCommodity menuCommodity = new MenuCommodity();
        menuCommodity.setId(id);
        getService().delete(menuCommodity);
        return Result.success();
    }

    public Result loadAll(@RequestHeader(value="token") String token , MenuCommodity t, Pager pager){
        List<MenuCommodityVo> list = menuCommodityService.findAllVoByMenuId(t, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @RequestMapping(value = "/insertBatch",method = RequestMethod.GET)
    @ApiOperation("批量插入商品")
    @ResponseBody
    public Result insertBatch(@RequestHeader(value="token") String token, String commodityIds, Integer menu){
        if(Objects.isNull(commodityIds) || Objects.isNull(menu)){
            throw new BusinessException("参数不能为空");
        }
        menuCommodityService.insertBatch(commodityIds, menu);
        return Result.success();
    }

    @RequestMapping(value = "/deleteBatch",method = RequestMethod.GET)
    @ApiOperation("批量删除商品")
    @ResponseBody
    public Result deleteBatch(@RequestHeader(value="token") String token, String commodityIds, Integer menu){
        if(Objects.isNull(commodityIds) || Objects.isNull(menu)){
            throw new BusinessException("参数不能为空");
        }
        menuCommodityService.deleteBatch(commodityIds, menu);
        return Result.success();
    }
}
