package com.webdrp.util.zxing;

import com.alibaba.druid.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.webdrp.util.UUIDUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 16:06 2020-04-01
 * @mail: zh878998515@gmail.com
 * @Description:生成二维码
 */
public class QrCodeZxingUtils {

    /**
     * 给二维码图片添加Logo
     * @param qrPic 二维码图片
     * @param logoUrl 需要绘制在中间的图片地址
     */
    public static BufferedImage addLogoQRCode(BufferedImage qrPic, String logoUrl, LogoConfig logoConfig){
        if (StringUtils.isEmpty(logoUrl)){
            return qrPic;
        }

        try{
            /**
             * 读取二维码图片，并构建绘图对象
             */
            BufferedImage image = qrPic;
            Graphics2D g = image.createGraphics();

            /**
             * 读取Logo图片
             */
            URL url = new URL(logoUrl);
            BufferedImage logo = ImageIO.read(url);

            int widthLogo = image.getWidth()/logoConfig.getLogoPart();
            int heightLogo = image.getWidth()/logoConfig.getLogoPart(); //保持二维码是正方形的

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2 ;

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 10, 10);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
            return image;
        }catch (Exception e){
            e.printStackTrace();
        }
        //出错返回原来的二维码
        return qrPic;
    }



    // LOGO宽度
    private static final int WIDTH = 70;
    // LOGO高度
    private static final int HEIGHT = 70;
    public static BufferedImage insertImage(BufferedImage source,  String logoUrl,boolean needCompress) throws Exception {
        URL url = new URL(logoUrl);
        Image src = ImageIO.read(url);

        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int QRCODE_SIZE = source.getWidth();

        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
        return source;
    }


}
