package com.hdpt.device.impl.services;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hdpt.device.domain.Device.DeviceVo;
import com.hdpt.device.domain.EnvironmentTest.*;
import com.hdpt.device.services.IDeviceService;
import com.hdpt.device.services.IEnvironmentTestService;
import com.hdpt.device.utils.Client;
import com.hdpt.device.utils.ConvertUtils;
import com.hdpt.device.utils.HttpTool;
import com.hdpt.device.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

@Service
public class EnvironmentTestServiceImpl implements IEnvironmentTestService {
    @Autowired
    IDeviceService deviceService;

    private final static Logger log = LoggerFactory.getLogger(EnvironmentTestServiceImpl.class);
    String url = "https://www.rdmscloud.com";
    String appId = "202312291190240607713361920";
    String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCITDT/7SbL41dvVcFZ8vccotxg6HVmuh21fvq0lCZrAhUolxa3JOUc21We+jAhRkoaYWw9N0VlrAAY8tRQhIAQLITJjn9B9CRZmxVSKAsARc05CsX8ybUQeT/1zYegnCOC3odfZkSRnWnMuuEZOgr/FMYVFbwu2iSJmhPU+EMj8WoxDtXUiYul5tKMOmml69PCnsnFelcY58/8esvhmkym9qJP6y0Hwh3xG5k5P0MCca7838bfMGl5kVNe/NNFdHyOZfyL4PND2Plk+MfUarjHWhwJIOzh6KSX2myYmQdYisLagkxSrIFmeOmTO9HJglkGqcykzNhR7+WGk0LB7+jXAgMBAAECggEARWEFWYEOTpra1wF/+pmC0EDWjT3HBROYK9sHIwSGydG1k4PVPES7m4TKgLI57bYp4mPVBRQtbyOuqQRyrsnDpngC39Z5UMlF95bJs5hOgUUGwhKJ1bOXR1txA14ru8W/Dc7YeedE4BqQ0HYQPxccoduhOPpnYDIKh0C296GoC5MHcCiM4tf5qegQTmth1LPKFf9nk88d/NyW6L2XieVV9ET0UaQhugZBvf78z2OxegQ/XO62HOGbkxJKb5tSfc4H1blthznq9ccSXHGmMspOj7X9Dh+8cXJ+eHO7W6a3Ch/H1/FVhug754LK53gd6yQbbIo3Cl8gShav8SifbcOkgQKBgQDZWDcCFeAVCc9ZGHDz+M8uRqisY5Wf8LgAzvTomJAO3ykSjZhkEcvkwXwWMEO/tuHKRzsEV+LGoJ6TJbMwgpxisBQkU3wLzLdQHJp8FMo3z58E01unzQS9PWDZbIrsmLcQGG6b0IP15JbihHvUH43vAC/j1VQAnBrjO7PjXQXrKwKBgQCgieT8ygsaBJDTxRoAqh878If/hZJQqkEXs3GBAJ9sHkfJSwmUyu1K9O2EpMnnAvRBxOIgnPn089qU/XLf1h3rrHXq4MsqI2D0Rn7vgV9rR/7Eq0wgPOzeD4+leKevEDJZEXJAOP9kOBnf7yv/W5q7q5MNXTzX4pgIP6I5CYpzBQKBgQCvjUBJL/AiLh1LwqEhfruhjjMZowos+Ns20/MfmHLrgDcFpFcNpQ5ChEG9Dlj6ljZEE0rJFE+4ze2nvxDAQw8b60spmEEeqsj6LVBentBDSjEQTcO/gqqsS9JZbAG+Xw+mMYXN1xtvVqbE6eu3B5G2FTmZVisWnwBcwFFpeS6ZpwKBgQCaiEI5J4+KKuzyPJ+sFhZcz0tKJtxR86EW3G+c2uma3IcDd8ywQqoqZhwge7OyQIC4snltJU3igb1df57NYkGI84jKYDqQxF92pElZ6PHdxf6yd6WevqbPlrESelMHTBL687VDptoIMYyOp551f0sx7ZVu3tpLgV4RSpewScPQtQKBgQDFo0bXv7sbVhC1wtBDnyVO/QOXiLI1qxLx6QHXOGt1CPfkkQ2/ETm6eAIUM6qWp4ENeYVSXsOdVY/s62xP15hvtSSMPUgfHem+Hw0P8UhNF60M4wXhvFZSlf+t4F3SCb2gqcDafdQNfWMNTbOlFbO81rYc9X8crKewaOqksi2Csw==";

    private Client client = new Client(url, appId, privateKey, EnvironmentTestServiceImpl::assertResult);

    //构建数据
    @Override
    public DrawImageVo buildDataStyle(DrawImageInfoVo drawImageInfoVo, List<SceneItemDeviceDataVO> deviceDataVoList, Boolean isAlarm) {
        DrawImageVo drawImageVo = new DrawImageVo();
        drawImageVo.setWeight(400);
        drawImageVo.setHeight(300);
        ArrayList<ImageArea> areas = new ArrayList<>();

        // 区域1
        ImageArea imageArea1 = new ImageArea();
        imageArea1.setColor(Color.white);
        imageArea1.setFont(new Font("黑体", Font.BOLD, 18));
        imageArea1.setStartPoint(new Point(0, 0));
        imageArea1.setWeight(400);
        imageArea1.setHeight(300);
        imageArea1.setPaint(Color.black);
        ArrayList<DrawField> fields1 = new ArrayList<>();
        //绘制的字段
        DrawField drawField1 = new DrawField();
        drawField1.setKey("项目");
        drawField1.setValue(drawImageInfoVo.getSceneName());
        drawField1.setPoint(new Point(15, 28));
        drawField1.setType("field");
        fields1.add(drawField1);

        DrawField drawField2 = new DrawField();
        drawField2.setKey("管理员");
        drawField2.setValue(drawImageInfoVo.getAdministrator());
        drawField2.setPoint(new Point(15, 58));
        drawField2.setType("field");
        fields1.add(drawField2);

        int initY = 62;
        int incrY = 1;
        List<ImageArea> imageAreaIndexList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (SceneItemDeviceDataVO deviceDataVo : deviceDataVoList) {
            int gapY = 30;
            List<DeviceParamDataVO> deviceParamDataVOList = deviceDataVo.getData();
            if (CollUtil.isEmpty(deviceParamDataVOList)) {
                continue;
            }
            //分组
            DrawField drawField3 = new DrawField();
            drawField3.setKey(deviceDataVo.getSceneItemName());
            drawField3.setValue("");
            drawField3.setType("field");
            drawField3.setPoint(new Point(15, initY + (incrY * gapY)));
            fields1.add(drawField3);

            //指标
            incrY++;
            int countX = 0;
            ImageArea imageAreaIndex = new ImageArea();
            imageAreaIndex.setColor(Color.white);
            imageAreaIndex.setFont(new Font("黑体", Font.BOLD, 16));
            imageAreaIndex.setStartPoint(new Point(15, initY + (incrY * gapY)));
            imageAreaIndex.setWeight(400);
            imageAreaIndex.setHeight(10);
            imageAreaIndex.setPaint(Color.black);
            ArrayList<DrawField> fieldsIndex = new ArrayList<>();
            gapY = 29;
            for (int i = 0; i < deviceParamDataVOList.size(); i++) {
                DeviceParamDataVO deviceParamDataVO = deviceParamDataVOList.get(i);
                DrawField drawField4 = new DrawField();
                drawField4.setType("field");
                drawField4.setKey(deviceParamDataVO.getParamName());
                drawField4.setValue((String) deviceParamDataVO.getData());
                if (countX % 3 == 0) {
                    drawField4.setPoint(new Point(15, initY + (incrY * gapY)));
                } else if (countX % 3 == 1) {
                    drawField4.setPoint(new Point(141, initY + (incrY * gapY)));
                } else {
                    drawField4.setPoint(new Point(262, initY + (incrY * gapY)));
                    incrY++;
                }
                if (i == (deviceParamDataVOList.size() - 1) && (countX % 3 == 0 || countX % 3 == 1)) {
                    //最后一行指标只有1、2个，Y坐标也需要增加
                    incrY++;
                }
                fieldsIndex.add(drawField4);
                countX++;
            }
            imageAreaIndex.setFieldList(fieldsIndex);
            imageAreaIndexList.add(imageAreaIndex);
        }
        imageArea1.setFieldList(fields1);

        //区域2
        ImageArea imageArea2 = new ImageArea();
        imageArea2.setColor(new Color(112, 112, 112));
        imageArea2.setFont(new Font("黑体", Font.BOLD, 18));
        imageArea2.setStartPoint(new Point(0, 250));
        imageArea2.setWeight(400);
        imageArea2.setHeight(50);
        imageArea2.setPaint(Color.white);
        ArrayList<DrawField> fields2 = new ArrayList<>();
        //状态
        DrawField drawField9 = new DrawField();
        drawField9.setKey("状态");
        drawField9.setValue("正常");
        drawField9.setType("field");
        drawField9.setPoint(new Point(15, 281));
        fields2.add(drawField9);
        if (isAlarm != null && isAlarm) {
            imageArea2.setColor(Color.RED);
            drawField9.setValue("异常");
        }
        //更新时间
        DrawField drawField10 = new DrawField();
        drawField10.setKey("");
        drawField10.setType("field");
        drawField10.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        drawField10.setPoint(new Point(170, 281));
        fields2.add(drawField10);
        imageArea2.setFieldList(fields2);

        //区域3
        ImageArea imageArea3 = new ImageArea();
        imageArea3.setColor(Color.black);
        imageArea3.setStartPoint(new Point(292, 10));
        imageArea3.setWeight(88);
        imageArea3.setHeight(88);
        imageArea3.setFont(new Font("黑体", Font.BOLD, 18));
        imageArea3.setPaint(Color.BLACK);

        ArrayList<DrawField> fields3 = new ArrayList<>();
        DrawField drawField11 = new DrawField();
        drawField11.setType("image");
        drawField11.setQrCodeSize(88);
        drawField11.setQrCodeX(292);
        drawField11.setQrCodeY(10);
        drawField11.setQrCodeDesc("");
        drawField11.setQrCodeContent(drawImageInfoVo.getQrCodeUrl());
        fields3.add(drawField11);
        imageArea3.setFieldList(fields3);

        areas.add(imageArea1);
        areas.addAll(imageAreaIndexList);
        areas.add(imageArea2);
        areas.add(imageArea3);
        drawImageVo.setAreas(areas);
        return drawImageVo;
    }

    //绘图至Bate64
    @Override
    public String drawImg(DrawImageVo drawImageVo) {
        if (drawImageVo == null) {
            return "";
        }
        log.info("**********画图参数={}", drawImageVo);
        BufferedImage image = new BufferedImage(drawImageVo.getWeight(), drawImageVo.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (ImageArea area : drawImageVo.getAreas()) {
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Point startPoint = area.getStartPoint();
            g2d.setColor(area.getColor());
            g2d.fillRect(ConvertUtils.toInt(startPoint.getX()), ConvertUtils.toInt(startPoint.getY()), area.getWeight(), area.getHeight());
            g2d.setFont(area.getFont());
            g2d.setPaint(area.getPaint());
            for (DrawField drawField : area.getFieldList()) {
                if ("field".equals(drawField.getType())) {
                    g2d.drawString(StrUtil.isNotEmpty(drawField.getKey()) ? drawField.getKey() + "：" + drawField.getValue() : drawField.getValue(), ConvertUtils.toInt(drawField.getPoint().getX()), ConvertUtils.toInt(drawField.getPoint().getY()));
                } else {
                    // 设置二维码参数
                    Map<EncodeHintType, Object> hints = new HashMap<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    // 设置间距为0
                    hints.put(EncodeHintType.MARGIN, 0);
                    QRCodeWriter qrWriter = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = qrWriter.encode(drawField.getQrCodeContent(), BarcodeFormat.QR_CODE, drawField.getQrCodeSize(), drawField.getQrCodeSize(), hints);
                        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                        // 绘制二维码
                        int qrCodeX = drawField.getQrCodeX();
                        int qrCodeY = drawField.getQrCodeY();
                        g2d.drawImage(qrCodeImage, qrCodeX, qrCodeY, null);
                        g2d.drawString(drawField.getQrCodeDesc(), qrCodeX, qrCodeY + drawField.getQrCodeSize() + 20);

                    } catch (WriterException e) {
                        log.error("**********画图1-1={}，", e);
                        log.error("**********画图1-2={}，", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            // 释放资源
            g2d.dispose();
        }

        String resstr = "";
        try {
            //调试用：保存图片
            log.info("**********image的值={}，", image);
            File outputImage = new File("test.png");
            log.info("**********outputImage的值={}，", outputImage);
            boolean res = ImageIO.write(image, "png", outputImage);
            log.info("**********res的值={}，", res);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            log.info("**********baos的值={}，", baos);

            boolean resbaos = ImageIO.write(image, "png", baos);
            log.info("**********resbaos的值={}，", resbaos);
            byte[] imageBytes = baos.toByteArray();
            resstr = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            log.error("**********画图2-1={}，", e);
            log.error("**********画图2-2={}，", e.getMessage());
        }
        return resstr;
    }

    /**
     * 获得在线数据
     *
     * @return
     */
    private List<DrawImageInfoVo> getFcGateWayOnlineData() {
        List<DrawImageInfoVo> drawImageInfoVos = new ArrayList<>();
        //参数
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setStatus("1");
        deviceVo.setJob(true);
        try {
            List<?> list = deviceService.getSmartDeviceListBySearch(deviceVo);
            if (list.size() == 0) {
                return null;
            }
            for (Object obj : list) {
                DrawImageInfoVo drawImageInfoVo = new DrawImageInfoVo();
                drawImageInfoVo.setAlarm(false);
                List<SceneItemDeviceDataVO> deviceDataVoList = new ArrayList<>();
                SceneItemDeviceDataVO satusSceneItemDeviceDataVO = new SceneItemDeviceDataVO();
                satusSceneItemDeviceDataVO.setSceneItemName("状态");
                List<DeviceParamDataVO> statusDeviceParamDataVOList = new ArrayList<>();
                SceneItemDeviceDataVO spaceSceneItemDeviceDataVO = new SceneItemDeviceDataVO();
                spaceSceneItemDeviceDataVO.setSceneItemName("空间");
                List<DeviceParamDataVO> spaceDeviceParamDataVOList = new ArrayList<>();
                Class<?> clazz = obj.getClass(); // 获取对象所属类的Class对象
                Field[] fields = clazz.getDeclaredFields(); // 获取类的所有字段（包括private）
                try {
                    for (Field field : fields) {
                        String fieldName = field.getName(); // 获取字段名
                        fieldName = fieldName.replace("$cglib_prop_", "");
                        boolean accessible = field.isAccessible(); // 判断字段是否为访问权限
                        if (!accessible) {
                            field.setAccessible(true); // 设置字段为可访问状态
                        }
                        Object value = field.get(obj); // 获取字段的值
                        //项目名称
                        if (fieldName.equalsIgnoreCase("devicesn")) {
                            if (null != value) {
                                drawImageInfoVo.setDeviceSn(value.toString());
                            } else {
                                drawImageInfoVo.setDeviceSn("");
                            }
                            continue;
                        }
                        //项目名称
                        if (fieldName.equalsIgnoreCase("mname")) {
                            if (null != value) {
                                drawImageInfoVo.setSceneName(value.toString());
                            } else {
                                drawImageInfoVo.setSceneName("");
                            }
                            continue;
                        }
                        if (fieldName.equalsIgnoreCase("mid") && null != value) {
                            //管理员
                            drawImageInfoVo.setAdministrator(getUserNameByMid(value.toString()));
                            //二维码
                            drawImageInfoVo.setQrCodeUrl(getSmallPrgUrlByMid(value.toString()));
                            continue;
                        }
                        //状态组
                        if (fieldName.equalsIgnoreCase("smoke_status") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("烟感");
                            deviceParamDataVO.setData("正常");
                            if (value.toString().equals("1")) {
                                deviceParamDataVO.setData("异常");
                                drawImageInfoVo.setAlarm(true);
                            }
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("water_status") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("水浸");
                            deviceParamDataVO.setData("正常");
                            if (value.toString().equals("1")) {
                                deviceParamDataVO.setData("异常");
                                drawImageInfoVo.setAlarm(true);
                            }
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("water_battery") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("水浸电量");
                            deviceParamDataVO.setData(value.toString());
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("people") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("人员");
                            deviceParamDataVO.setData("无人");
                            if (value.toString().equals("1")) {
                                deviceParamDataVO.setData("有人");
                            }
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("light") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("光照");
                            deviceParamDataVO.setData("无光照");
                            if (value.toString().equals("1")) {
                                deviceParamDataVO.setData("有光照");
                            }
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("vibrate") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("震动");
                            deviceParamDataVO.setData("无震动");
                            if (value.toString().equals("1")) {
                                deviceParamDataVO.setData("有震动");
                                drawImageInfoVo.setAlarm(true);
                            }
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("pressure") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("气压");
                            deviceParamDataVO.setData(getShortNumber(value, "0") + "Pa");
                            statusDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        //空间组
                        if (fieldName.equalsIgnoreCase("temperature") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("温度");
                            deviceParamDataVO.setData(getShortNumber(value, "1") + "°C");
                            spaceDeviceParamDataVOList.add(deviceParamDataVO);
                        }

                        if (fieldName.equalsIgnoreCase("humidity") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("湿度");
                            deviceParamDataVO.setData(getShortNumber(value, "2") + "%");
                            spaceDeviceParamDataVOList.add(deviceParamDataVO);
                        }
                        if (fieldName.equalsIgnoreCase("noise") && null != value) {
                            DeviceParamDataVO deviceParamDataVO = new DeviceParamDataVO();
                            deviceParamDataVO.setParamName("噪声");
                            deviceParamDataVO.setData(getShortNumber(value, "0") + "dB");
                            spaceDeviceParamDataVOList.add(deviceParamDataVO);
                        }

                        //System.out.println("字段名：" + fieldName);
                        //System.out.println("字段值：" + value);
                        if (!accessible) {
                            field.setAccessible(false); // 还原字段的访问权限
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                satusSceneItemDeviceDataVO.setData(statusDeviceParamDataVOList);
                spaceSceneItemDeviceDataVO.setData(spaceDeviceParamDataVOList);
                deviceDataVoList.add(satusSceneItemDeviceDataVO);
                deviceDataVoList.add(spaceSceneItemDeviceDataVO);
                drawImageInfoVo.setDeviceDataVoList(deviceDataVoList);
                drawImageInfoVos.add(drawImageInfoVo);
            }


        } catch (Exception e) {
            log.error("getFcGateWayOnlineData--error:" + e);
        }
        return drawImageInfoVos;
    }

    /**
     * 四舍五入二位
     *
     * @param num
     * @return
     */
    private String getShortNumber(Object num, String bit) {
        if (StringUtil.isEmpty(bit)) {
            bit = "2";
        }
        if (null == num || num.equals("")) {
            return "0";
        }
        double number = ConvertUtils.toDouble(num);
        return String.format("%." + bit + "f", number);
    }

    /**
     * 根据项目id获得用户姓名
     *
     * @param mid
     * @return
     */
    private String getSmallPrgUrlByMid(String mid) {
        String sql1 = "select param_val from sys_param  where param_type='wx_small_config' and param_code='http_page_prefix' ";
        Map<String, Object> map = SqlRunner.db().selectOne(sql1);
        if (map != null && map.get("param_val") != null) {
            return map.get("param_val").toString() + mid;
        }
        return "";
    }

    /**
     * 根据项目id获得用户姓名
     *
     * @param mid
     * @return
     */
    private String getUserNameByMid(String mid) {
        String sql1 = "select name from user" + " where m_id=" + mid;
        Map<String, Object> map = SqlRunner.db().selectOne(sql1);
        if (map != null && map.get("name") != null) {
            return map.get("name").toString();
        }
        return "";
    }

    @Override
    public String pushImg2InkScreen() {
        //构建数据
        List<DrawImageInfoVo> list = getFcGateWayOnlineData();
        if (null == list || CollUtil.isEmpty(list)) {
            return "没有数据";
        }
        for (DrawImageInfoVo drawImageInfoVo : list) {
            DrawImageVo drawImageVo = buildDataStyle(drawImageInfoVo, drawImageInfoVo.getDeviceDataVoList(), drawImageInfoVo.isAlarm());
            //画图
            String imgBase64 = drawImg(drawImageVo);
            //获得数据设备编号数据
            String getwaySn=getLbtDeviceInfo(drawImageInfoVo.getDeviceSn());
            if(StringUtil.isEmpty(getwaySn)){
                log.info("pushImg2InkScreen 墨水屏显示，没有设备信息"+drawImageInfoVo);
                continue;
            }
            //下发到墨水屏
            upHttpLbtServer(drawImageInfoVo.getDeviceSn(),new StringBuilder(imgBase64), getwaySn);
        }
        return null;
    }

    private String getLbtDeviceInfo(String deviceSn) {
        String url = "https://www.rdmscloud.com/api/1190238510869946368/service/iot_system/1.0/devcode_query_device_V2";
        Map<String, String> paramMap = getParamMap(url);
        paramMap.put("device_code", deviceSn);
        log.info("getLbtDeviceInfo 设备编号：" + deviceSn);
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .url(getRestUrlPath(url))
                .version("1.0")
                .params(paramMap)
                .httpMethod(HttpTool.HTTPMethod.POST);
        String json = client.execute(requestBuilder);
        log.info("getLbtDeviceInfo获取结果："+json);
        JSONObject jsonObject = JSON.parseObject(json);
        if(null!=jsonObject && !"1".equals(jsonObject.getString("code"))){
            return "";
        }
        JSONObject resultJSONObject=jsonObject.getJSONObject("result");
        if(null==resultJSONObject){
            return "";
        }
        JSONArray data=resultJSONObject.getJSONArray("data");
        for(int i=0;i<data.size();i++){
            JSONObject dataJsonObject= data.getJSONObject(i);
            if(null!=dataJsonObject && StringUtil.isNotEmpty(dataJsonObject.getString("gateway_sn"))){
                return dataJsonObject.getString("gateway_sn");
            }
        }
        return "";
    }
    private void upHttpLbtServer(String deviceSn,StringBuilder imgBase64Sb, String gatewaySn) {
        String url = "https://www.rdmscloud.com/api/1190238510869946368/service/iot_system/1.0/pushImg2InkScreen";
        Map<String, String> paramMap = getParamMap(url);
        if (StringUtil.isEmpty(gatewaySn)) {
            gatewaySn = "07270323060068";
        }
        paramMap.put("gatewaySn", gatewaySn);
        if (imgBase64Sb.length() == 0) {
            imgBase64Sb = new StringBuilder("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");
        }
        log.info("写入默水屏数据：" + imgBase64Sb.toString());
        paramMap.put("imgBase64", imgBase64Sb.toString());
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .url(getRestUrlPath(url))
                .version("1.0")
                .params(paramMap)
                //.addFile("file",imgFile)
                .httpMethod(HttpTool.HTTPMethod.POST);
        client.execute(requestBuilder);
        updateDevice(deviceSn, imgBase64Sb.toString(), gatewaySn);
    }
    /**
     * 更新黑发布墨水屏数据
     * @param gatewaySn
     * @param imageBase64
     */
    private void updateDevice(String deviceSn,String imageBase64,String gatewaySn){
        StringBuilder sb=new StringBuilder();
        sb.append("update t_device set GATEWAYSN=");
        sb.append("'"+gatewaySn+"',");
        sb.append("IMGINKSCREEN=");
        sb.append("'"+imageBase64+"',");
        sb.append("LAST_TIME=NOW()");
        sb.append(" where DEVICESN=");
        sb.append("'"+deviceSn+"'");
        boolean flag = SqlRunner.db().update(sb.toString());
        if(flag){
            log.info("updateDevice 墨水屏更新处理成功："+deviceSn);
        }else{
            log.info("updateDevice 墨水屏更新处理失败："+deviceSn);
        }
    }

    /**
     * 获取rest url  参数前面的字符串
     *
     * @param url 请求连接
     * @return rest url
     */
    private static String getRestUrlPath(String url) {
        if (url.indexOf("?") > 0) {
            return url.substring(0, url.indexOf("?"));
        } else {
            return url;
        }
    }

    /**
     * 获取参数MAP
     *
     * @param url 请求连接
     * @return 参数MAP
     */
    private static Map<String, String> getParamMap(String url) {

        Map<String, String> paramMap = new HashMap<String, String>();
        if (url.indexOf("?") > 0) {
            String rest = url.substring(url.indexOf("?") + 1);
            String[] params = rest.split("&");

            for (String param : params) {
                String[] values = param.split("=");
                paramMap.put(values[0], values.length > 1 ? values[1] : "");
            }
        }
        return paramMap;
    }

    public static void assertResult(Client.RequestInfo requestInfo, String responseData) {
        System.out.println(responseData);
        String method = requestInfo.getMethod();
        if (method == null) {
            return;
        }
        String node = requestInfo.getDataNode();
        JSONObject jsonObject = JSON.parseObject(responseData).getJSONObject(node);
        String code = Optional.ofNullable(jsonObject).map(json -> json.getString("code")).orElse("20000");
        //Assert.assertEquals("10000", code);
    }

}
