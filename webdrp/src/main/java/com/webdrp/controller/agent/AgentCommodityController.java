package com.webdrp.controller.agent;

import com.webdrp.annotation.AgentUserAnnotation;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.CommodityPublish;
import com.webdrp.constant.CommodityType;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.Provider;
import com.webdrp.service.CommodityService;
import com.webdrp.service.CommodityStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "agent  旅游线路列表")
@RestController
@RequestMapping("/agent/commodity")
public class AgentCommodityController {
    @Autowired
    CommodityService commodityService;

    @Autowired
    CommodityStyleService commodityStyleService;

    @ApiOperation("获取旅游线路列表，带分页")
    @GetMapping("/list")
    public Result getList(@RequestHeader("agentauth") String token,@AgentUserAnnotation Provider provider, Pager pager){
        Commodity commodity = new Commodity();
        commodity.setcType(CommodityType.Normal);
        commodity.setPublish(CommodityPublish.YES);
        List<Commodity> list =  commodityService.loadAll(commodity,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @ApiOperation("获取线路对应的排期列表，不带统计")
    @GetMapping("/style/list")
    public Result getStyleList(@RequestHeader("agentauth") String token,@AgentUserAnnotation Provider provider, @RequestParam Integer cid, Pager pager){
        //筛选是否是港澳
        CommodityStyle commodityStyle = new CommodityStyle();
        commodityStyle.setCommodityId(cid);
        List<CommodityStyle> list =  commodityStyleService.loadAll(commodityStyle,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @ApiOperation("获取某个团能收到的利润工单")
    @GetMapping("/style/{styleid}/list")
    public Result getStyleListRecord(@RequestHeader("agentauth") String token,@AgentUserAnnotation Provider provider, @PathVariable("styleid") Integer styleid, Pager pager){

        return Result.success();
    }


}
