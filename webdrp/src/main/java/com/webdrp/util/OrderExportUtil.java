package com.webdrp.util;

import com.webdrp.entity.Order;
import com.webdrp.entity.vo.OrderVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderExportUtil {

    private static List<String> ORDER_DATA = new ArrayList<>();

    static {
        ORDER_DATA.add("订单序号");
        ORDER_DATA.add("微信序号");
        ORDER_DATA.add("微信昵称");
        ORDER_DATA.add("总价格");
        ORDER_DATA.add("一级标题");
        ORDER_DATA.add("二级标题");
        ORDER_DATA.add("物流单号");
        ORDER_DATA.add("地址");
        ORDER_DATA.add("电话");
        ORDER_DATA.add("收货人");
        ORDER_DATA.add("用户订单号");
        ORDER_DATA.add("说明");
        ORDER_DATA.add("款式名称");
        ORDER_DATA.add("数量");
        ORDER_DATA.add("款式总价");
    }

    public static HSSFWorkbook excelOrderExport(List<OrderVo> orderVos) throws Exception {
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("订单数据表");
        //创建HSSFRow对象
        HSSFRow row = sheet.createRow(0);
        //创建第一行
        for (int i = 0; i < ORDER_DATA.size(); i++) {
            //创建HSSFCell对象
            HSSFCell cell = row.createCell(i);
            //设置单元格的值
            cell.setCellValue(ORDER_DATA.get(i));
            if (i == 4 || i == 5 || i == 11 || i== 12) {
                sheet.setColumnWidth(i, 80 * 256);
            }
            else {
                sheet.setColumnWidth(i, 21 * 256);
            }
        }

        for (int i = 0; i < orderVos.size(); i++) {
            HSSFRow tempRow = sheet.createRow(i + 1);
            //订单序号
            HSSFCell orderId = tempRow.createCell(0);
            orderId.setCellValue(orderVos.get(i).getId());

            //微信序号
            HSSFCell ruchUserId = tempRow.createCell(1);
            ruchUserId.setCellValue(orderVos.get(i).getRichUserId());

            //微信昵称
            HSSFCell nickName = tempRow.createCell(2);
            nickName.setCellValue(orderVos.get(i).getNickName());

            //总价格
            HSSFCell subPrice = tempRow.createCell(3);
            subPrice.setCellValue(orderVos.get(i).getSubPrice());

            //一级标题
            HSSFCell name = tempRow.createCell(4);
            name.setCellValue(orderVos.get(i).getName());


            //二级标题
            HSSFCell nameItem = tempRow.createCell(5);
            nameItem.setCellValue(orderVos.get(i).getNameItem());

            //物流单号
            HSSFCell wuliuId = tempRow.createCell(6);
            wuliuId.setCellValue(orderVos.get(i).getWuliuId());

            //地址
            HSSFCell address = tempRow.createCell(7);
            address.setCellValue(orderVos.get(i).getAddress());

            //电话
            HSSFCell phone = tempRow.createCell(8);
            phone.setCellValue(orderVos.get(i).getPhone());

            //收货人
            HSSFCell takeName = tempRow.createCell(9);
            takeName.setCellValue(orderVos.get(i).getTakeName());


            //
            HSSFCell userOrder = tempRow.createCell(10);
            userOrder.setCellValue(orderVos.get(i).getUserOrder());

            //说明
            HSSFCell detailName = tempRow.createCell(11);
            detailName.setCellValue(orderVos.get(i).getDetailName());
            //款式名称
            HSSFCell detailNameItem = tempRow.createCell(12);
            detailNameItem.setCellValue(orderVos.get(i).getDetailNameItem());
            //数量
            HSSFCell count = tempRow.createCell(13);
            count.setCellValue(orderVos.get(i).getDetailCount());

            //款式总价
            HSSFCell detailSubPrice = tempRow.createCell(14);
            if (Objects.isNull(orderVos.get(i).getDetailSubPrice())){
                System.out.println("orderVos . ID = "+orderVos.get(i).getId());
            }

            detailSubPrice.setCellValue(orderVos.get(i).getDetailSubPrice());

        }

        //输出Excel文件
        /*FileOutputStream output = new FileOutputStream("d://workbook1.xls");
        wb.write(output);
        output.flush();
        output.close();*/
        return wb;
    }
//
//    public static void main(String[] args) throws Exception {
//
//        List<OrderVo> orderVos = new ArrayList<>();
//        OrderVo orderVo1 = new OrderVo();
//        orderVo1.setId(1);
//        orderVo1.setRichUserId(1);
//        orderVo1.setOpenId("123213");
//        orderVo1.setAddress("cao");
//        orderVo1.setSubPrice(123.3);
//        orderVo1.setStatus(12);
//        orderVo1.setName("避孕套");
//        orderVo1.setJiandian(123.9);
//        orderVo1.setRebate(123.9);
//
//        OrderVo orderVo2 = new OrderVo();
//        orderVo2.setId(2);
//        orderVo2.setRichUserId(1);
//        orderVo2.setOpenId("123213");
//        orderVo2.setAddress("fuck");
//        orderVo2.setName("棒棒");
//        orderVo2.setSubPrice(123.3);
//        orderVo2.setStatus(12);
//
//        orderVo2.setJiandian(123.9);
//        orderVo2.setRebate(123.9);
//        orderVos.add(orderVo1);
//        orderVos.add(orderVo2);
//        excelOrderExport(orderVos);
//    }

}
