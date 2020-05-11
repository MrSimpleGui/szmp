package com.webdrp.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.*;
import com.webdrp.constant.CommodityType;
import com.webdrp.entity.*;
import com.webdrp.entity.dto.LogisticsDto;
import com.webdrp.entity.dto.OrderDto;
import com.webdrp.entity.vo.CommodityStyleVo;
import com.webdrp.entity.vo.OrderVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.*;
import com.webdrp.service.OrderService;
import com.webdrp.util.OrderExportUtil;
import org.apache.http.HttpEntity;
import com.webdrp.constant.OrderStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yuanming on 2018/8/10.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderMapper> implements OrderService {


    @Value("${logistics.appcode}")
    private String appCode;

    //https://market.aliyun.com/products/57126001/cmapi00037173.html?spm=5176.11065268.1996646101.searchclickresult.56405b48kbNBTe


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    CommodityStyleMapper commodityStyleMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    LogisticsMapper logisticsMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    /**
     * 插入订单信息
     *
     * @param order
     */
    @Override
    public void insert(Order order) {
        order.setStatus(OrderStatus.TODO);//等待支付
        orderMapper.insert(order);
    }

    /**
     * 更新订单信息
     *
     * @param order
     */
    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }


    /**
     * 删除订单
     *
     * @param order
     * @return
     */
    @Override
    public void delete(Order order) {
        //先删除订单下的订单明细
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId());
        orderDetail.beforeDelete();
        orderDetailMapper.deleteByOrderId(orderDetail);
        //再删除订单
        order.beforeDelete();
        orderMapper.delete(order);
    }


    /**
     * @param order
     * @return
     */
    @Override
    public OrderVo detail(Order order) {
        OrderVo res = orderMapper.findById(order.getId());
        if (Objects.isNull(res)) {
            throw new BusinessException("无该订单！");
        }
        return res;
    }

    @Override
    public Order findByWechatIdAndOrderId(Integer id, Integer richUserId) {
        return orderMapper.findByWechatIdAndOrderId(id, richUserId);
    }

    @Override
    public List<OrderVo> loadAllAndDetail(OrderDto orderDto, Pager pager) {
        long countAll = orderMapper.findAllAndDetailCount(orderDto);
        pager = getPager(countAll, pager);
        return orderMapper.findAllAndDetail(orderDto, pager);
    }

    @Override
    public Order queryById(Integer id) {
        return orderMapper.queryById(id);
    }

    /**
     * 用户订单ID
     * @return
     */
    public String getOrderId(){
        return "W2020T" + System.currentTimeMillis();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Order  createOrder(Integer packageId, Member member, int amount, String takeName, String telephone, String address) {
        Order order = new Order();
        if (amount <= 0) {
            amount = 1;
        }
        CommodityStyle commodityStyle = commodityStyleMapper.findById(packageId);
        if (commodityStyle.getStock().intValue() <= 0){
            throw new BusinessException("库存不足");
        }
        if (commodityStyle.getStock().intValue() < amount){
            throw new BusinessException("库存不足");
        }

        List<Image> imageList = commodityStyleMapper.findCommodityStyleImageByCommodityStyleId(packageId);

        Commodity commodity = commodityMapper.findById(commodityStyle.getCommodityId());
        order.setOpenId(member.getOpenid());                 // 支付使用的open ID
        order.setRichUserId(member.getId());

        order.setName(commodity.getName());
        order.setNameItem(commodityStyle.getName() + " x" + amount);
        // 商品类型
        order.setOrderType(commodity.getcType());
        order.setStatus(OrderStatus.TODO);
        //设置用户可以看见的参数
        order.setUserOrder(getOrderId());
        order.setType(OrderType.WECHAT);
        double subPrice = commodityStyle.getPrice() * amount;
        // 假如有邮费，计算邮费
        if (Objects.nonNull(commodity.getExpress())){
            if (commodity.getExpress().doubleValue() > 0){
                subPrice += commodity.getExpress();
            }
        }
        order.setSubPrice(subPrice);
        // 收获地址信息
        order.setTakeName(takeName);
        order.setAddress(address);
        order.setPhone(telephone);

        orderMapper.insert(order);

        // 订单详情 假如有购物车再改
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId());
        orderDetail.setNameItem(commodityStyle.getName());
        orderDetail.setName(commodity.getName());
        orderDetail.setPrice(commodityStyle.getPrice());
        orderDetail.setSubPrice(commodityStyle.getPrice()*amount);
        orderDetail.setNickName(member.getNickname());
        orderDetail.setRichUserId(member.getId());
        orderDetail.setCount(amount);
        orderDetail.setImageUrl(imageList.get(0).getFullUrl());
        orderDetail.setCommodityStyleId(commodityStyle.getId());
        orderDetail.setCommodityId(commodityStyle.getCommodityId());
        orderDetailMapper.insert(orderDetail);

        if (Objects.nonNull(commodity.getExpress())){
            if (commodity.getExpress().doubleValue() > 0){
                // 邮费落订单详情
                OrderDetail orderDetail1 = new OrderDetail();
                orderDetail1.setOrderId(order.getId());
                orderDetail1.setNameItem("快递费");
                orderDetail1.setName("快递费");
                orderDetail1.setPrice(commodity.getExpress());
                orderDetail1.setSubPrice(commodity.getExpress());
                orderDetail1.setNickName(member.getNickname());
                orderDetail1.setRichUserId(member.getId());
                orderDetail1.setCount(1);
                // 邮费默认图
                orderDetail1.setImageUrl("https://qn.xiaoyeok.com/img/FiJiuRUN4V6l4AWcc0L4InAORXPx.jpeg");
                orderDetail1.setCommodityStyleId(commodityStyle.getId());
                orderDetail1.setCommodityId(commodityStyle.getCommodityId());
                orderDetailMapper.insert(orderDetail1);
            }
        }

        // 扣库存
        commodityStyle.setStock(commodityStyle.getStock() - amount);
        commodityStyleMapper.update(commodityStyle);

        return order;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Order zfbcreateOrder(Order order, Integer cid, Integer csid, Member member, int amount) {
        if (amount <= 0){
            amount = 1;
        }
        Commodity commodity = commodityMapper.findById(cid);
        order.setRichUserId(member.getId());
        Member temp = memberMapper.findById(member.getId());
        CommodityStyleVo commodityStyle = commodityStyleMapper.findAllById(csid);
        order.setAddress(temp.getAddress());
        order.setName(commodity.getName());
        order.setNameItem(" 款式 " + commodityStyle.getName() + "x"+amount);
        order.setOrderType(commodity.getcType());
        order.setPhone(temp.getPhone());
        order.setSubPrice(commodityStyle.getPrice()*amount);
        order.setTakeName(temp.getRealName());
        order.setStatus(OrderStatus.TODO);
        //设置用户可以看见的参数
        order.setUserOrder("Z1001T" + System.currentTimeMillis());
        //
        if ((int) commodity.getcType() == (int) com.webdrp.constant.CommodityType.ONE) {
            order.setRebate(commodity.getRebate());
        }
        if ((int) commodity.getcType() == (int) com.webdrp.constant.CommodityType.TWO || commodity.getcType() == CommodityType.THREE) {
            order.setRebate(commodity.getRebate());
            order.setJiandian(commodity.getJiandian());
        }
        order.setType(OrderType.ZHIFUBAO);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setRichUserId(temp.getId());
        orderDetail.setOpenId(temp.getOpenid());
        orderDetail.setCommodityId(commodity.getId());
        orderDetail.setCommodityStyleId(commodityStyle.getId());
        orderDetail.setCount(amount);
        orderDetail.setName(commodity.getName() + " | " + commodityStyle.getName());
        orderDetail.setNickName(member.getNickname());

        if (Objects.nonNull(commodityStyle.getImages()) && commodityStyle.getImages().size() > 0) {
            orderDetail.setImageUrl(commodityStyle.getImages().get(0).getFullUrl());
        }
        orderDetail.setPrice(commodityStyle.getPrice());
        orderDetail.setNameItem("" + commodityStyle.getName());
        orderDetail.setSubPrice(commodityStyle.getPrice()*amount);
        double subPrice = order.getSubPrice();
        //邮费
        if (Objects.nonNull(commodity.getExpress())){
            if (commodity.getExpress()>0){
                subPrice += commodity.getExpress();
            }
        }
        order.setSubPrice(subPrice);
        orderMapper.insert(order);
        orderDetail.setOrderId(order.getId());
        orderDetailMapper.insert(orderDetail);
        return order;
    }

    /**
     * 微信端取消订单
     *
     * @param orderId
     */
    @Override
    public void wxCancelOrder(Integer orderId, Member member) {
        if (Objects.isNull(member)) {
            return;
        }

        Order demo = orderMapper.findById(orderId);
        if ((int) demo.getRichUserId() != (int) member.getId()) {
            throw new BusinessException("非法取消订单！");
        }
        Order order = new Order();
        order.setId(orderId);
        order.setRichUserId(member.getId());
        order.setStatus(OrderStatus.CANCEL);
        orderMapper.cancelOrderByOrderIdAndRichUserId(order);
    }

    /**
     * 查询订单的总金额流水
     *
     * @param order
     * @return
     */
    @Override
    public Double sumOrderMoney(Order order) {
        return orderMapper.sumOrderMoney(order);
    }

    /**
     * 根据物流单号获取物流信息
     *
     * @param number
     * @param number 物流单号
     * @return
     */
    @Override
    public LogisticsDto getLogisticsMsg(String number) {

        LogisticsDto logisticsDto = null;
        try {
            Logistics logistics = logisticsMapper.findByNumber(number);
            if (logistics != null) {
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                String updateTime = logistics.getUpdateTime();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date temp = sdf.parse(updateTime);

                long diff = date.getTime() - temp.getTime();
                long hour = diff % nd / nh;
                if (hour >= 3) {
                    logisticsDto = logisticsMsgAPI(number);
                    logistics.setResult(JSONObject.toJSONString(logisticsDto));
                    logisticsMapper.update(logistics);
                } else {
                    logisticsDto = JSONObject.parseObject(logistics.getResult(), LogisticsDto.class);
                }

            } else {
                logisticsDto = logisticsMsgAPI(number);
                Logistics temp = new Logistics();
                temp.setNumber(number);
                temp.setResult(JSONObject.toJSONString(logisticsDto));
                logisticsMapper.insert(temp);
            }
            return logisticsDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取物流信息失败");
        }
    }

    //https://market.aliyun.com/products/57126001/cmapi00037173.html?spm=5176.11065268.1996646101.searchclickresult.56405b48kbNBTe
    public LogisticsDto logisticsMsgAPI(String number) {
        LogisticsDto logisticsDto = null;
        try {
            String host = "http://expressln.market.alicloudapi.com/kdi";
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(host + "?no=" + number);
            httpGet.setHeader("Authorization", "APPCODE " + appCode);
            CloseableHttpResponse closeableHttpResponse = (CloseableHttpResponse) httpClient.execute(httpGet);
            //获取response的body
            if (closeableHttpResponse != null && closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity, "utf-8");
                logisticsDto = JSONObject.parseObject(response, LogisticsDto.class);
            } else {
                throw new BusinessException("获取物流信息失败");
            }
            return logisticsDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取物流信息失败");
        }
    }

    @Override
    public Integer createOrderByCart(List<ShoppingCart> list, Member member) {
        return 1;
    }

    /**
     * 导出订单信息
     *
     * @return
     */
    public HSSFWorkbook exportOrderData(OrderDto orderDto) {
        try {
//            String startTime = orderDto.getStartTime();
//            String endTime = orderDto.getEndTime();
//            Date date = new Date();
//            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//            if (StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//                orderDto.setStartTime(now);
//            }
//            if (StringUtils.isEmpty(endTime) && !StringUtils.isEmpty(startTime)) {
//                orderDto.setEndTime(now);
//            }
            List<OrderVo> orderVos = orderMapper.exportOrderData(orderDto);
            HSSFWorkbook wb = OrderExportUtil.excelOrderExport(orderVos);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出订单数据失败");
        }
    }


    /**
     * 批量更新物流信息
     *
     * @param request
     * @param multipartFile
     * @throws IOException
     */
    public void batchUpdateWuliuMsg(HttpServletRequest request, MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty())
                throw new BusinessException("文件为空");
            long size = multipartFile.getSize();
            String name = multipartFile.getOriginalFilename();
            if (StringUtils.isEmpty(name) || size == 0) {
                throw new BusinessException("文件为空");
            }
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);
            Workbook wb = null;
            if(fileName.endsWith(".xlsx"))
                wb = new XSSFWorkbook(multipartFile.getInputStream());
            else
                wb = new HSSFWorkbook(multipartFile.getInputStream());
            if (wb == null)
                throw new BusinessException("文件为空");
            List<Order> orders = new ArrayList<>();//乘装excel数据
            Sheet sheet = wb.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();//获取数据的行数
            System.out.println(rowCount);
            for (int i = 1; i <= rowCount; i++) {
                int j = 0;
                Row row = sheet.getRow(i);
                Order order = new Order();
                Cell orderId = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                orderId.setCellType(CellType.STRING);
                order.setId(Integer.parseInt(orderId.getStringCellValue()));
                j = j + 6;//物流id是第六个
                Cell wuliuId = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                wuliuId.setCellType(CellType.STRING);
                order.setWuliuId(wuliuId.getStringCellValue());
//                if (Objects.nonNull(order.getId()))
                orders.add(order);
            }

            /*for(Order order : orders){
                System.out.println(order.getId() + "   " + order.getWuliuId());
                orderMapper.update(order);
            }*/
            orderMapper.batchUpdateWuliuId(orders);

        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("更新失败");
        }

    }

    @Override
    public void updatezfbOrder(Order order) {
        orderMapper.updatezfbOrder(order);
    }

    @Override
    public Order findByzfbOrderId(String zfbOrderId) {
        return orderMapper.findByzfbOrderId(zfbOrderId);
    }

    @Override
    public int findCountOrderData(OrderDto orderDto) {
        return orderMapper.findCountOrderData(orderDto);
    }

    @Override
    public double findSumOrderPrice(OrderDto orderDto) {
        return orderMapper.findSumOrderPrice(orderDto);
    }

    /**
     * 扫呗创建订单
     *
     * @param order
     * @param cid
     * @param csid
     * @param member
     * @param amount
     * @param cardType
     * @param buyName
     * @param buyIdCard
     * @param telephone
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Order createOrderSaoBei(Order order, Integer cid, Integer csid, Member member, int amount, Integer cardType, String buyName, String buyIdCard, String telephone) {

        return new Order();
    }

    /**
     * 更新订单状态
     *
     * @param order
     */
    @Override
    public void updateOrderStatus(Order order) {
        orderMapper.updateOrderStatus(order);
    }

    @Override
    public void updateType(Order order) {
        orderMapper.updateType(order);
    }

    /**
     * 根据购物车ID创建订单
     *
     * @param cartIds
     * @param member
     * @param takeName
     * @param takeTel
     * @param address
     * @return
     */
    @Override
    public Order createOrder(Integer[] cartIds, Member member, String takeName, String takeTel, String address) {
        List<Integer> ids  = Arrays.asList(cartIds);
        List<ShoppingCart> list = shoppingCartMapper.findByCardIds(ids);
        double subPrice = 0.0d;
        for (ShoppingCart item:list){
            subPrice = subPrice + (item.getPrice() * item.getInventory());
        }

        Order order = new Order();
        order.setOpenId(member.getOpenid());  // 微信支付使用的open ID
        order.setRichUserId(member.getId());
        order.setName("购物车订单");
        order.setNameItem("订单*"+cartIds.length);
        // 商品类型
        order.setOrderType(1); // 默认
        order.setUserOrder(getOrderId());  //设置用户可以看见的订单参数
        order.setType(OrderType.WECHAT);
        order.setStatus(OrderStatus.TODO);
        order.setRichUserId(member.getId());
        order.setSubPrice(subPrice);
        // 收获地址信息
        order.setTakeName(takeName);
        order.setAddress(address);
        order.setPhone(takeTel);
        orderMapper.insert(order);
        for (ShoppingCart item:list){
            // 订单详情 假如有购物车再改
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setNameItem(item.getCommodityStyleName());
            orderDetail.setName(item.getName());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setSubPrice(item.getPrice()*item.getInventory());
            orderDetail.setNickName(member.getNickname());
            orderDetail.setRichUserId(member.getId());
            orderDetail.setCount(item.getInventory());
            orderDetail.setImageUrl(item.getImageUrl());
            orderDetail.setCommodityStyleId(item.getCommodityStyleId());
            orderDetail.setCommodityId(item.getCommodityId());
            orderDetailMapper.insert(orderDetail);
        }
        return order;
    }

}

