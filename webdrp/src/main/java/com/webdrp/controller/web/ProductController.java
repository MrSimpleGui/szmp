package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.CommodityType;
import com.webdrp.entity.Commodity;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShareImage;
import com.webdrp.entity.vo.CommodityAllVo;
import com.webdrp.service.CommodityService;
import com.webdrp.service.ShareImageService;
import com.webdrp.service.impl.QRServiceImpl;
import com.webdrp.util.MdRenderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.WebServiceClient;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:17 2020-02-18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "ToC 产品相关")
@RestController
@RequestMapping("/wechat/api")
public class ProductController {

    @Autowired
    CommodityService commodityService;

    @Value("${wechat.product.detail}")
    public String productDetailUrl;

    @ApiOperation("商品列表 type是类型")
    @GetMapping("/goodsList")
    public Result goodsList(Pager pager, @RequestParam Integer type){
//        if (type.intValue()==CommodityType.JICHA || type.intValue()==CommodityType.SECONDBUY){
            Commodity commodity = new Commodity();
            commodity.setPublish(1);
//            commodity.setcType(type);
            List<CommodityAllVo> list = commodityService.findCommodityAllByPage(commodity,pager);
            return Result.success().addAttribute("list",list).addAttribute("pager",pager);
//        }
//        return Result.success();
    }
    @ApiOperation("商品详情")
    @GetMapping("/goodsInfo")
    public Result goodsInfo(@RequestParam Integer id){
        CommodityAllVo product = commodityService.findVoById(id);
//        最大价格最小价格
        List<Double> list = new ArrayList<>();
        double minPrice = 0.00;
        double maxPrice = 0.00;
        product.getCommodityStyleVoList().forEach(item->{
            list.add(item.getPrice());
        });
        minPrice = Collections.min(list);
        maxPrice = Collections.max(list);

        // markDown转换成html
        product.setContext(MdRenderUtils.renderMdToHtmlString(product.getContext()));
        if(Objects.nonNull(product.getService())){
            product.setService(MdRenderUtils.renderMdToHtmlString(product.getService()));
        }
        if(Objects.nonNull(product.getSpecification())){
            product.setSpecification(MdRenderUtils.renderMdToHtmlString(product.getSpecification()));
        }

        return Result.success().addAttribute("product",product).addAttribute("minPrice",minPrice).addAttribute("maxPrice",maxPrice);
    }



}
