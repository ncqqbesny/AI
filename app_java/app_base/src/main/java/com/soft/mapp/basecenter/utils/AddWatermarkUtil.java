package com.soft.mapp.basecenter.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description:
 *  图片添加水印
 */
public class AddWatermarkUtil {
    /**
     * 在一张背景图上添加二维码
     * @param roundImage 背景图 地址 为空值，则只生成二维码
     * @param logoImage  链接
     * @param url   二维码连接地址
     * @param outImgPath  输出的图片
     * @param  width 二维码宽度
     * @param  hight 二维码高度
     * @param  x   二位码位置
     * @param  y   二位码位置
     */
    public static String addWater(String roundImage,String logoImage, String url, String outImgPath,String outImgFile,Integer width,Integer hight,Integer x,Integer y) {
        try {
            // 读取原图片信息
            //得到文件
            BufferedImage bufImg=null;
            Image srcImg=null;
            if(null==width || null==hight){
                width=200;
                hight=200;
            }
            if(null==x || null==y){
                x=0;
                y=0;
            }
            if(StringUtil.isNotEmpty(roundImage)) {
                File file = ResourceUtils.getFile(roundImage);
                //文件转化为图片
                srcImg = ImageIO.read(file);
                //获取图片的宽
                int srcImgWidth = srcImg.getWidth(null);
                //获取图片的高
                int srcImgHeight = srcImg.getHeight(null);
                // 加水印
                bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            }else{
                bufImg = new BufferedImage(width, hight, BufferedImage.TYPE_INT_RGB);
            }
            Graphics2D g = bufImg.createGraphics();
            if(StringUtil.isEmpty(roundImage)){
                g.drawImage(srcImg, x, y, width, hight, null);
                //背景色
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, hight);
            }
            //使用工具类生成二维码
            Image image = createQrCode(logoImage, url, width, hight);
            //将小图片绘到大图片上,500,300 .表示你的小图片在大图片上的位置。
            g.drawImage(image, x, y, null);
            //设置颜色。
            g.setColor(Color.WHITE);
            g.dispose();
            File filePath = new File(outImgPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath+"/"+outImgFile);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 将字符串转为二维码
     * @param roundImage
     * @param url
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    private static BufferedImage createQrCode(String roundImage, String url, int width, int height) throws IOException {
        QrConfig config = new QrConfig(width, height);
        Image image = ImageIO.read(new FileInputStream(roundImage));
        config.setImg(image);
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        return QrCodeUtil.generate(url, config);
    }


}