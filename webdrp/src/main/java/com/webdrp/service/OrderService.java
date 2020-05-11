package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Order;
import com.webdrp.common.Pager;
import com.webdrp.entity.Member;
import com.webdrp.entity.ShoppingCart;
import com.webdrp.entity.dto.LogisticsDto;
import com.webdrp.entity.dto.OrderDto;
import com.webdrp.entity.vo.OrderVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface OrderService extends BaseService<Order> {

    @Override
    List<Order> loadAll(Order order, Pager pager);

    /**
     * 插入订单信息
     *
     * @param order
     */
    void insert(Order order);


    /**
     * 订单明细
     *
     * @param order
     * @return
     */
    OrderVo detail(Order order);

    Order findByWechatIdAndOrderId( Integer id,  Integer richUserId);

    List<OrderVo> loadAllAndDetail(OrderDto order, Pager pager);

    Order queryById(Integer id);

    /**
     *
     * @param packageId 套餐ID
     * @param member 用户
     * @param amount 数量
     * @param telephone 手机号码
     * @param takeName 名字
     * @param address 地址
     * @return
     */
    Order createOrder(Integer packageId, Member member, int amount, String takeName, String telephone, String address);

//    Order createOrderYS(Order order, Integer cid, Integer csid, RichUser richUser,int amount,Integer cardType,String buyName,String buyIdCard,String telephone);

    Order zfbcreateOrder(Order order, Integer cid, Integer csid, Member member, int amount);

    void wxCancelOrder(Integer orderId, Member member);

    Double sumOrderMoney(Order order);

    LogisticsDto getLogisticsMsg(String number);

    LogisticsDto logisticsMsgAPI(String number);

    Integer createOrderByCart(List<ShoppingCart> list, Member member);

    HSSFWorkbook exportOrderData(OrderDto orderDto);

    void batchUpdateWuliuMsg(HttpServletRequest request, MultipartFile multipartFile);

    void updatezfbOrder(Order order);

    Order findByzfbOrderId(String zfbOrderId);

    /**
     * 统计订单相关
     * @param orderDto 开始时间  结束时间为必填
     * @return
     */
    int findCountOrderData(OrderDto orderDto);

    /**
     * 统计订单总金额
     * @param orderDto 开始时间  结束时间为必填
     * @return
     */
    double findSumOrderPrice(OrderDto orderDto);

    /**
     * 扫呗创建订单
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
    Order createOrderSaoBei(Order order, Integer cid, Integer csid, Member member, int amount, Integer cardType, String buyName, String buyIdCard, String telephone);

    /**
     * 更新订单状态
     * @param order
     */
    void updateOrderStatus(Order order);

    void updateType(Order order);

    /**
     * 根据购物车ID创建订单
     * @param cartIds
     * @param member
     * @param takeName
     * @param takeTel
     * @param address
     * @return
     */
    Order createOrder(Integer[] cartIds, Member member, String takeName, String takeTel, String address);
}
