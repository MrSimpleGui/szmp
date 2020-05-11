package com.webdrp.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午11:11 2018/4/25
 * @mail: zh878998515@gmail.com
 * @Description:时间工具类
 */
public class DateUtils {

    public static String dateToStringYyyyMMddHHmmss(Date date){
        if(Objects.isNull(date)){
            date = new Date(System.currentTimeMillis());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    public static String dateToString(Date date){
        if(Objects.isNull(date)){
            date = new Date(System.currentTimeMillis());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    //2018-09-23
    public static String dateToYYYYMMDD(){
        Date date = new Date();
        if(Objects.isNull(date)){
            date = new Date(System.currentTimeMillis());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串时间只要年月日
     * @param date
     * @return
     */
    public static String dateToYYYYMMDD(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date source =  simpleDateFormat.parse(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return "1970-01-01";
        }
    }


    //获取当前时间前一天
    public static String getLastDateYYYYMMDD(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 计算当前时间和传入的日期字符串之间的分钟差值
     * @author 靳锐阳
     * @param dateString 日期字符串 yyyy-MM-dd hh:mm:ss
     * @return 分钟差值 (如果出现异常则返回Integer的最大值)
     */
    public static int  diffInMinutes(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateString);
            Date now = new Date();
            return (int) ((now.getTime() - date.getTime())/(1000 * 60));
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }


    public static void main(String[] args) {
//        System.out.println("args = [" + getLastDateYYYYMMDD() + "]");
        System.out.println(dateToStringYyyyMMddHHmmss(new Date()));
    }

    public static String Replace(String startTime) {
        if (Objects.isNull(startTime)){
            return "";
        }
        //去掉空格去掉冒号
        startTime = startTime.replace(" ","");
        startTime = startTime.replace(":","");
        return startTime;
    }
}
