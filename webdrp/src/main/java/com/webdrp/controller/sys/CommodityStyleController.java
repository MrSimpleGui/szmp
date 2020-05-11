package com.webdrp.controller.sys;

import com.webdrp.common.Pager;
import com.webdrp.common.Result;

import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.dto.CommodityStyleDto;
import com.webdrp.entity.vo.CommodityStyleCommissionVo;
import com.webdrp.entity.vo.CommodityStyleVo;

import com.webdrp.service.CommodityStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "后台接口系统端商品款式")
@RestController
@RequestMapping("/sys/commodityStyle")
public class CommodityStyleController{

    @Autowired
    CommodityStyleService commodityStyleService;

    @PostMapping("/insert")
    @ApiOperation("插入商品款式")
    public Result insert(@RequestHeader(value = "token") String token, CommodityStyleDto commodityStyleDto) {
        commodityStyleService.insert(commodityStyleDto);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商品款式")
    public Result update(@RequestHeader(value = "token") String token,  CommodityStyleDto commodityStyleDto) {
        commodityStyleService.update(commodityStyleDto);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除商品款式")
    public Result delete(@RequestHeader(value = "token") String token, CommodityStyle commodityStyle){
        commodityStyleService.delete(commodityStyle);
        return Result.success();
    }


    @RequestMapping(value = "/findByPage",method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有")
    @ResponseBody
    public Result loadAll(@RequestHeader(value="token") String token , CommodityStyle commodityStyle, Pager pager){
//        List<CommodityStyle> list = commodityStyleService.loadAll(commodityStyle,pager);
        List<CommodityStyleCommissionVo> list = commodityStyleService.loadAllVo(commodityStyle,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    /**
     * 获取商品款式详情
     * @param token
     * @param commodityStyle
     * @return
     */
    @ApiOperation("获取商品详情")
    @PostMapping("/detail")
    public Result detail(@RequestHeader(value="token") String token , CommodityStyle commodityStyle){
        CommodityStyleVo commodityStyleVo = commodityStyleService.detail(commodityStyle);
        return Result.success().addAttribute("detail",commodityStyleVo);
    }
}
