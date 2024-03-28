package com.hdpt.device.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

public class PicUtils {
    private final static Logger log = LoggerFactory.getLogger(PicUtils.class);

    /**
     * 对图片作镜像处理
     * @param inPathFileName
     * @param outPathFileName
     * @return
     */
    public  static String ImageMirror(String inPathFileName,String outPathFileName){
        File file = null;
        BufferedImage image = null;
        try {
            //file = new File("E:\\in.jpg");
            file = new File(inPathFileName);
            image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();
            for (int j = 0; j < height; j++) {
                int l = 0, r = width - 1;
                while (l < r) {
                    int pl = image.getRGB(l, j);
                    int pr = image.getRGB(r, j);
                    image.setRGB(l, j, pr);
                    image.setRGB(r, j, pl);
                    l++;
                    r--;
                }
            }
            file = new File(outPathFileName);
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            return "程序执行错误";
        }
        return "";
    }
    /**
     * base64转为图片
     *
     * @param path   文件路径：到文件夹即可，代码里会在文件夹里生成对应的jpg文件
     * @param base64
     * @return  图片文件路径
     */
    public static String base64ToJpg(String path,String fileName, String base64) {
        // 判断文件路径是否存在
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 创建文件
        //String jpgFile = path + "/" + UUID.randomUUID() + ".jpg";
        String jpgFile=path + "/"+fileName;
        File file = new File(jpgFile);
        boolean jpgFileExist = false;
        try {
            if(file.exists()){
                file.delete();
            }
            jpgFileExist = file.createNewFile();
            log.info("jpg文件创建成功");
        } catch (IOException e) {
            log.info("jpg文件创建失败");
            e.printStackTrace();
        }
        if (jpgFileExist) {
            // 解密
            //Base64.Decoder decoder = Base64.getDecoder();
            Base64.Decoder decoder = Base64.getMimeDecoder();
            // 去掉base64前缀 data:image/jpeg;base64,
            base64 = base64.substring(base64.indexOf(",", 1) + 1, base64.length());
            byte[] b = decoder.decode(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            // 保存图片
            try {
                FileOutputStream out = new FileOutputStream(jpgFile);
                out.write(b);
                out.flush();
                out.close();
                // 写入成功返回文件路径
                log.info("写入成功："+jpgFile);
                return jpgFile;
            } catch (FileNotFoundException e) {
                log.info("文件未找到");
                e.printStackTrace();
            } catch (IOException e) {
                log.info("写入失败");
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * 图片转换base64
     * @param imagePath 图片路径+文件名
     * @return
     */
    public static String getBase64ByJpg(String imagePath) {
        //String imagePath = "test.jpg"; // 图片路径
        String base64Image="";
        try {
            byte[] imageBytes = readImage(imagePath);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
    private static byte[] readImage(String imagePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(imagePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) >= 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }
}
