package com.soft.mapp.basecenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class PicUtils {
    private final static Logger log = LoggerFactory.getLogger(PicUtils.class);


    /**
     * 输入流转为base64图片
     *
     * @param in
     * @return
     */
    public static String inputStreamToBase64Code(InputStream in) {
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            log.info("WeChatUtil.createwxaqrcode error===" + e);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String base64Code = new String(Objects.requireNonNull(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(data)));
        return base64Code;
    }

    /**
     * 输入流转为图片
     *
     * @param path     文件路径：到文件夹即可，代码里会在文件夹里生成对应的jpg文件
     * @param inStream
     * @return
     */
    public static String inputStreamToJpg(String path, String fileName, InputStream inStream) {
        // 判断文件路径是否存在
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 创建文件
        //String jpgFile = path + "/" + UUID.randomUUID() + ".jpg";
        String jpgFile = path + "/" + fileName;
        File file = new File(jpgFile);
        boolean jpgFileExist = false;
        try {
            jpgFileExist = file.createNewFile();
            log.info("jpg文件创建成功");
        } catch (IOException e) {
            log.info("jpg文件创建失败");
            e.printStackTrace();
        }
        if (jpgFileExist) {
            // 保存图片
            try {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                //创建一个Buffer字符串
                byte[] buffer = new byte[1024];
                //每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                //使用一个输入流从buffer里把数据读取出来
                while ((len = inStream.read(buffer)) != -1) {
                    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outStream.write(buffer, 0, len);
                }
                //关闭输入流
                inStream.close();
                //把outStream里的数据写入内存
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = outStream.toByteArray();
                //new一个文件对象用来保存图片，默认保存当前工程根目录
                //创建输出流
                FileOutputStream fileOutStream = new FileOutputStream(file);//写入数据
                fileOutStream.write(data);
                fileOutStream.flush();
                fileOutStream.close();
                // 写入成功返回文件路径
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
     * 对图片作镜像处理
     *
     * @param inPathFileName
     * @param outPathFileName
     * @return
     */
    public static String ImageMirror(String inPathFileName, String outPathFileName) {
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
     * @return
     */
    public static String base64ToJpg(String path, String fileName, String base64) {
        // 判断文件路径是否存在
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        // 创建文件
        //String jpgFile = path + "/" + UUID.randomUUID() + ".jpg";
        String jpgFile = path + "/" + fileName;
        File file = new File(jpgFile);
        boolean jpgFileExist = false;
        try {
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
     *
     * @param imagePath 图片路径+文件名
     * @return
     */
    public static String getBase64ByJpg(String imagePath) {
        //String imagePath = "test.jpg"; // 图片路径
        String base64Image = "";
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
