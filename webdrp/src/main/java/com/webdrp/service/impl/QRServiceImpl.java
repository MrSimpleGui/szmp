package com.webdrp.service.impl;
import com.webdrp.util.UUIDUtils;
import com.webdrp.util.zxing.LogoConfig;
import com.webdrp.util.zxing.QrCodeZxingUtils;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


@Service
public class QRServiceImpl {

    public  String merge(String baseImageUrl,String urlAddress, Integer richUserId,String nickName,String headImage) throws Exception {
        //底图
        URL url = new URL(baseImageUrl);
        BufferedImage imageLocal = ImageIO.read(url);

        //生成二维码
        ByteArrayOutputStream out = QRCode.from(urlAddress).to(ImageType.JPG).withSize(500, 500).stream();
        //将二维码的Byte读入
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        //Byte数组转为图片
        BufferedImage imageCode = ImageIO.read(in);
        //LogoConfig中设置Logo的属性
        LogoConfig logoConfig = new LogoConfig();
        // 二维码中间加入用户头像
        imageCode = QrCodeZxingUtils.insertImage(imageCode, headImage, true);
        imageCode.flush();
        //画图
        Graphics2D g = imageLocal.createGraphics();
        g.drawImage(imageCode, 510 ,985, 160, 160, null);
        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("宋体", Font.CENTER_BASELINE, 24));

//        nickName = "昵称:"+nickName;
//        String richUserIdStr = "邀请码:"+richUserId;

//        g.drawString(nickName,515,imageLocal.getHeight()-40);
//        g.drawString(richUserIdStr,515,imageLocal.getHeight()-15);
        g.dispose();

        String pngName = richUserId + "_" + UUIDUtils.getUUID() + ".jpg";
        File file = new File("qr_images");
        if (!file.exists()) {
            file.mkdir();
        }
        File outputfile = new File("qr_images",pngName);
        if(!outputfile.exists()){
            outputfile.createNewFile();
        }
        ImageIO.write(imageLocal, "jpg", outputfile);
        in.close();
        return pngName;
    }

    /**
     * 商品图片合成器
     * @param baseImageUrl http://qn.yywluo.cn/img/FtvTkIGhFi9f61xXT-9mlSadWYqU.jpg
     * @param urlAddress 分享链接，
     * @param memberId 用户ID
     * @return
     * @throws URISyntaxException,IOException
     */
    public String shareImageMerge(String baseImageUrl,String urlAddress,Integer memberId) throws IOException {
        URL url = new URL(baseImageUrl);
        //底图
        BufferedImage imageLocal = ImageIO.read(url);
        //生成二维码
        ByteArrayOutputStream out = QRCode.from(urlAddress).to(ImageType.JPG).withSize(500, 500).stream();
        //将二维码的Byte读入
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        //Byte数组转为图片
        BufferedImage imageCode = ImageIO.read(in);
        //画图
        Graphics2D g = imageLocal.createGraphics();
        g.drawImage(imageCode, 510 ,985, 160, 160, null);
        g.setColor(new Color(28, 28, 28));
        g.setFont(new Font("宋体", Font.CENTER_BASELINE, 24));

        g.dispose();

        String pngName = memberId + "_" + UUIDUtils.getUUID() + ".jpg";
        File file2 = new File("qr_images");
        // 假如没有目录创建目录
        if (!file2.exists()) {
            file2.mkdir();
        }

        File outputfile = new File("qr_images",pngName);
        if(!outputfile.exists()){
            outputfile.createNewFile();
        }
        ImageIO.write(imageLocal, "jpg", outputfile);
        in.close();

        return pngName;
    }
}
