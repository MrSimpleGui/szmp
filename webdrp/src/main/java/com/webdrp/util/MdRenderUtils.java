package com.webdrp.util;

import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zhang yuan ming
 * @Date: create in 03:05 2019-07-31
 * @mail: zh878998515@gmail.com
 * @Description:markDown渲染工具类
 */
public class MdRenderUtils {

    public static Parser parser = Parser.builder().build();
    public static  HtmlRenderer renderer = HtmlRenderer.builder().build();

    public static String renderMdToHtmlString(String mdString){
        mdString = mdString.replaceAll("\n\n","\n");
        Node document = parser.parse(mdString);
        String html = renderer.render(document);
        html = html.replaceAll("::: hljs-center","<center>");
        html = html.replaceAll(":::","</center>");
        return html;
    }


    public static void main(String[] args) {
//        String html = "#### 购买须知\n" +
//                "* 会员10大优点\n" +
//                "* 会员10大缺点\n" +
//                "\n" +
//                "#### 退改政策\n" +
//                "1.  开通会员是否可以退改\n" +
//                "2.  20天不能退\n" +
//                "\n" +
//                "#### Ordered\n" +
//                "1. 可以享受多少服务，多少服务都可以拉\n" +
//                "\n" +
//                "## What about images?\n" +
//                "![Yes](http://img.redocn.com/sheji/20141219/zhongguofengdaodeliyizhanbanzhijing_3744115.jpg)";
//
//
//
//        for (int i = 0;i < 100;i++){
//            String html1 = renderMdToHtmlString(html);
//            System.out.println(i);
//            System.out.printf(html1);
//        }
        String md = "::: hljs-center\n" +
                "\n" +
                "**欢迎来到广东省广州市科技有限公司**\n" +
                "\n" +
                ":::\n" +
                "\n" +
                "![7.jpg](http://qn.yywluo.cn/img/FgegfzReqEe31eK92-p-Zdyu_Raw.jpg)\n" +
                "::: hljs-center\n" +
                "\n" +
                "**这里集齐了来自世界各地的精英人才**\n" +
                "\n" +
                ":::\n" +
                "\n" +
                "![2.jpg](http://qn.yywluo.cn/img/FiN5Lo00Wv5obNSTCKZ9PFxImMTQ.jpg)\n" +
                "::: hljs-center\n" +
                "\n" +
                "**这里涉及的领域达到了数百种**\n" +
                "\n" +
                ":::\n" +
                "\n" +
                "![1.jpg](http://qn.yywluo.cn/img/Ft_BGrOmvdAxjxQGnMusX_49HDzE.jpg)\n" +
                "::: hljs-center\n" +
                "\n" +
                "**公司福利少不了，有包餐、下午茶、加班补贴、交通补贴等等**\n" +
                "\n" +
                ":::\n" +
                "\n" +
                "![3.jpg](http://qn.yywluo.cn/img/FtxJ_XSsSQ2MAffljjCxDKYCOtRv.jpg)";
//        md = md.replaceAll("\n\n","\n");
       // md.replaceAll("\n\n","\n");
        String mdHtml = renderMdToHtmlString(md);

        System.out.println("结果");
        System.out.println(mdHtml);
    }

}
