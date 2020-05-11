package com.webdrp.controller.sys;

import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.OrderDetail;
import com.webdrp.service.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "后台接口系统端订单详情")
@RestController
@RequestMapping("/sys/orderDetail")
public class OrderDetailController{

    @Autowired
    OrderDetailService orderDetailService;


    @PostMapping("/insert")
    @ApiOperation("插入订单明细")
    public Result insert(@RequestHeader(value = "token") String token, OrderDetail orderDetail) {
        //orderDetailService.insert(orderDetail);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新订单明细")
    public Result update(@RequestHeader(value = "token") String token, OrderDetail orderDetail) {
        orderDetailService.update(orderDetail);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除订单明细")
    public Result delete(@RequestHeader(value = "token") String token, OrderDetail orderDetail) {
        orderDetailService.delete(orderDetail);
        return Result.success();
    }

    @PostMapping("/detail")
    @ApiOperation("订单明细详情")
    public Result detail(@RequestHeader(value = "token") String token, OrderDetail orderDetail) {
        OrderDetail res = orderDetailService.detail(orderDetail);
        return Result.success().addAttribute("detail", res);
    }


    @RequestMapping(value = "/findByPage", method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有")
    public Result loadAll(@RequestHeader(value = "token") String token, OrderDetail orderDetail, Pager pager) {
        List<OrderDetail> list = orderDetailService.loadAll(orderDetail, pager);
        return Result.success().addAttribute("pager", pager).addAttribute("list", list);
    }

}
