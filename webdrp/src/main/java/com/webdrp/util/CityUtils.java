package com.webdrp.util;

/**
 * @Author: zhang yuan ming
 * @Date: create in 20:38 2020-02-20
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class CityUtils {

    public static String getCityFromAddress(String str){
        int startIndex = str.indexOf(" ",1)+1;
        int endIndex = str.indexOf(" ",startIndex);
        str = str.substring(startIndex,endIndex);
        return str;
    }

    public static void main(String[] args) {
        String ss = "湖北省 武汉市 武昌区2131231231313";
        String ss1 = "澳门特别行政区 澳门半岛 花地玛堂区恶魔你呃呃呃呃呃呃呃呃";
        System.out.println(getCityFromAddress(ss));
        System.out.println(getCityFromAddress(ss1));
    }
}
