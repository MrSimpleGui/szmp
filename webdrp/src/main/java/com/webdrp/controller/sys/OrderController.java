package com.webdrp.controller.sys;

import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.OrderStatus;
import com.webdrp.constant.OrderType;
import com.webdrp.entity.Order;
import com.webdrp.entity.dto.OrderDto;
import com.webdrp.entity.vo.OrderVo;
import com.webdrp.service.OrderService;
import com.webdrp.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Api(tags = "后台接口系统端订单")
@RestController
@RequestMapping("/sys/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/insert")
    @ApiOperation("插入订单[别用]")
    public Result insert(@RequestHeader(value = "token") String token, Order order) {
        //orderService.insert(order);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新订单")
    public Result update(@RequestHeader(value = "token") String token, Order order) {
        orderService.update(order);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除订单")
    public Result delete(@RequestHeader(value = "token") String token, Order order) {
        orderService.delete(order);
        return Result.success();
    }

    @PostMapping("/detail")
    @ApiOperation("订单详情")
    public Result detail(@RequestHeader(value = "token") String token, Order order) {
        Order res = orderService.detail(order);
        return Result.success().addAttribute("detail", res);
    }


    @GetMapping(value = "/findByPage")
    @ApiOperation("分页带条件查询所有")
    public Result loadAll(@RequestHeader(value = "token") String token, OrderDto orderDto, Pager pager) {
        List<OrderVo> list = orderService.loadAllAndDetail(orderDto, pager);
        return Result.success().addAttribute("pager", pager).addAttribute("list", list);
    }


    @GetMapping("/status")
    @ApiOperation("订单状态")
    public Result orderStatus(@RequestHeader(value = "token") String token) {
        return Result.success().addAttribute("orderStatus", OrderStatus.map);
    }

    @GetMapping("/type")
    @ApiOperation("支付类型")
    public Result orderType(@RequestHeader(value = "token") String token) {
        return Result.success().addAttribute("orderType", OrderType.map);
    }


    @ApiOperation("订单数量")
    @GetMapping("/sumOrderNum")
    public Result sumOrderNum(@RequestHeader(value = "token") String token) {
        Order order = new Order();
        long num = orderService.count(order);
        return Result.success(num);
    }

    @ApiOperation("已支付订单总数量")
    @GetMapping("/sumOrderCountWasPay")
    public Result sumOrderCountWasPay(@RequestHeader(value = "token") String token) {
        Order order = new Order();
        order.setStatus(OrderStatus.COMFIRM);
        long num = orderService.count(order);
        return Result.success(num);
    }


    @ApiOperation("订单总金额")
    @GetMapping("/sumOrderPrice")
    public Result sumOrderPrice(@RequestHeader(value = "token") String token) {
        Order order = new Order();
        order.setStatus(OrderStatus.COMFIRM);
        double summoney = orderService.sumOrderMoney(order);
        return Result.success(summoney);
    }

    @ApiOperation(value = "今天已支付订单数量",notes = "不给时间直接查今天截止到当前，给时间的话就查时间段内的,时间格式为2018-09-12 12:12:12 ")
    @GetMapping("/getDayOrderNum")
    public Result dayOrderNum(@RequestHeader(value = "token") String token,String startTime,String endTime ){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("num",orderService.findCountOrderData(orderDto));
    }

    @ApiOperation(value = "今微信已支付订单数量",notes = "不给时间直接查今天截止到当前，给时间的话就查时间段内的,时间格式为2018-09-12 12:12:12  ")
    @GetMapping("/getWxDayOrderNum")
    public Result getWxDayOrderNum(@RequestHeader(value = "token") String token,String startTime,String endTime){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        orderDto.setType(OrderType.WECHAT);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("num",orderService.findCountOrderData(orderDto));
    }

    @ApiOperation(value = "今天支付宝已支付订单数量",notes = "不给时间直接查今天截止到当前，给时间的话就查时间段内的,时间格式为2018-09-12 12:12:12  ")
    @GetMapping("/getZfbDayOrderNum")
    public Result getZfbDayOrderNum(@RequestHeader(value = "token") String token,String startTime,String endTime){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        orderDto.setType(OrderType.ZHIFUBAO);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("num",orderService.findCountOrderData(orderDto));
    }

    @ApiOperation(value = "今天营业额",notes = "不给时间直接查今天截止到当前，给时间的话就查时间段内的,时间格式为2018-09-12 12:12:12")
    @GetMapping("/getDayOrderSumPrice")
    public Result getDayOrderSumPrice(@RequestHeader(value = "token") String token,String startTime,String endTime){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("sumPrice",orderService.findSumOrderPrice(orderDto));
    }

    @ApiOperation(value = "今天微信营业额",notes = "介绍同上")
    @GetMapping("/getWxDayOrderSumPrice")
    public Result getWxDayOrderSumPrice(@RequestHeader(value = "token") String token,String startTime,String endTime){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        orderDto.setType(OrderType.WECHAT);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("sumPrice",orderService.findSumOrderPrice(orderDto));
    }

    @ApiOperation(value = "今天支付宝营业额",notes = "介绍同上")
    @GetMapping("/getZfbDayOrderSumPrice")
    public Result getAlipayDayOrderSumPrice(@RequestHeader(value = "token") String token,String startTime,String endTime){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(OrderStatus.COMFIRM);
        orderDto.setType(OrderType.ZHIFUBAO);
        String dateTemp = DateUtils.dateToYYYYMMDD();
        if (StringUtils.isEmpty(startTime)){
            startTime = dateTemp + " 00:00:00";
        }
        if (StringUtils.isEmpty(endTime)){
            endTime = DateUtils.dateToString(new Date());
        }
        orderDto.setStartTime(startTime);
        orderDto.setEndTime(endTime);
        return Result.success().addAttribute("sumPrice",orderService.findSumOrderPrice(orderDto));
    }


    @ApiOperation(value = "导出订单数据",notes = "给startTime,endTime,wuliuId为空时导出所有未发货订单，wuliuId为非空时导出所有已发货订单")
    @GetMapping("/export")
    public void exportOrderData(@RequestHeader(value = "token") String token,HttpServletResponse response, OrderDto orderDto) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        //修复代码
        String startTime = orderDto.getStartTime();
        String endTime = orderDto.getEndTime();
        Date date = new Date();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        String start = DateUtils.Replace(startTime);
        String end = DateUtils.Replace(endTime);
        now = DateUtils.Replace(now);
        if (start.isEmpty()){
            start = "不限";
        }
        if (end.isEmpty()){
            end = "不限";
        }

        if (start.isEmpty() && end.isEmpty()){

        }
        String fileName = "订单-时间-"+start+"至"+end+"-导出时间"+now+".xls";

        if (StringUtils.isEmpty(orderDto.getWuliuId())){
            fileName = "未发货" + fileName;
        }else{
            fileName = "已发货" + fileName;
        }

        fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
        HSSFWorkbook wb = orderService.exportOrderData(orderDto);
        wb.write(outputStream);
        wb.close();
        outputStream.flush();
        outputStream.close();
        logger.debug("订单导出完成");
    }

    @ApiOperation("导入订单更新物流单号")
    @PostMapping("/batchUpdateWuliuMsg")
    public Result batchUpdateWuliuMsg(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam("file") MultipartFile multipartFile) {

        orderService.batchUpdateWuliuMsg(request, multipartFile);
        return Result.success();
    }
}
