package com.webdrp.controller.sys;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.CommodityType;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Provider;
import com.webdrp.entity.dto.CommodityDto;

import com.webdrp.entity.dto.LogisticsDto;
import com.webdrp.entity.vo.CommodityVo;
import com.webdrp.service.CommissionModeService;
import com.webdrp.service.CommodityService;
import com.webdrp.service.OrderService;
import com.webdrp.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Api(tags = "后台接口系统端商品")
@RestController
@RequestMapping("/sys/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    OrderService orderService;

    /*@Override
    public BaseService<Commodity> getService() {
        return commodityService;
    }*/

    /*
    @ApiOperation("单个添加【测试失败】")
    @PostMapping("/v1/add")
    public Result addCommodity(@RequestHeader(value="token")  String token, CommodityVo commodityA) throws Exception {
        commodityService.addCommodity(commodityA);
        return Result.fail("未实现",400);
    }*/

    @Autowired
    ProviderService providerService;

    // 模式
    @Autowired
    CommissionModeService commissionModeService;

    @PostMapping("/insert")
    @ApiOperation("插入商品")
    public Result insert(@RequestHeader(value = "token") String token, CommodityDto commodityDto) {
        //兼容邮费问题
        if (Objects.isNull(commodityDto.getExpress())){
            commodityDto.setExpress(0.00);
        }
        if(Objects.isNull(commodityDto.getcType())){
            commodityDto.setcType(0);
        }
        if (Objects.nonNull(commodityDto.getVentorId())&&commodityDto.getVentorId()!=0){
            Provider provider = providerService.findOne(commodityDto.getVentorId());
            commodityDto.setVentorName(provider.getPrividername());
        }

        commodityService.insert(commodityDto);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商品")
    public Result update(@RequestHeader(value = "token") String token, CommodityDto commodityDto) {
        if (Objects.nonNull(commodityDto.getVentorId())&&commodityDto.getVentorId()!=0){
            Provider provider = providerService.findOne(commodityDto.getVentorId());
            commodityDto.setVentorName(provider.getPrividername());
        }
        commodityService.update(commodityDto);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除商品")
    public Result delete(@RequestHeader(value = "token") String token, Commodity commodity) {
        commodityService.delete(commodity);
        return Result.success();
    }

    @PostMapping("/publish")
    @ApiOperation("上下架")
    public Result publish(@RequestHeader(value = "token") String token, Commodity commodity) {
        commodityService.publish(commodity);
        return Result.success();
    }

    @RequestMapping(value = "/findByPage", method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有")
    @ResponseBody
    public Result loadAll(@RequestHeader(value = "token") String token, Commodity commodity, Pager pager) {
        List<Commodity> list = commodityService.loadAll(commodity, pager);
        return Result.success().addAttribute("pager", pager).addAttribute("list", list);
    }

    @ApiOperation("获取商品销售类型")
    @GetMapping("/getCommodityType")
    public Result getCommodityType() {
        List<CommissionMode> modes = commissionModeService.findAll(new CommissionMode());
        Map<Integer,String> map = new HashMap<>();
        modes.stream().forEach(item->{
            map.put(item.getId(),item.getName());
        });
        return Result.success(map);
    }

    /**
     * 获取商品详情
     *
     * @param token
     * @param commodity
     * @return
     */
    @ApiOperation("获取商品详情")
    @PostMapping("/detail")
    public Result detail(@RequestHeader(value = "token") String token, Commodity commodity) {
        CommodityVo commodityVo = commodityService.detail(commodity);
        return Result.success().addAttribute("detail", commodityVo);
    }

}
