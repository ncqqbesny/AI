package com.app.device.handler.mqtt;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.app.device.config.MqttConsumerConfig;
import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.domain.Cabinet.CabinetDeviceDTO;
import com.app.device.handler.ApplicationContextProvider;
import com.app.device.impl.services.HardwareRevServiceImpl;
import com.app.device.impl.services.HardwareYjcService;
import com.app.device.type.OpTypeEnum;
import com.app.device.utils.StringUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yzg
 * @Date: 2021/7/30 17:06
 * @Description:
 */
public class MqttReConsumerCallBack implements MqttCallback {

    private final static Logger log = LoggerFactory.getLogger(MqttReConsumerCallBack.class);

    /**
     * 客户端断开连接的回调
     * @author yzg
     * @param throwable
     * @return void
     * @date 2021/7/30 17:14
     */
    @Override
    public void connectionLost(Throwable throwable) {
        MqttConsumerConfig client = ApplicationContextProvider.getBean(MqttConsumerConfig.class);
        client.connect();
        log.info("与服务器断开连接，可重连");
    }

    /**
     * 消息到达的回调
     * @author yzg
     * @param topic
     * @param message
     * @return void
     * @date 2021/7/30 17:14
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("接收消息主题 : %s",topic));
        System.out.println(String.format("接收消息Qos : %d",message.getQos()));
        System.out.println(String.format("接收消息内容 : %s",new String(message.getPayload())));
        System.out.println(String.format("接收消息retained : %b",message.isRetained()));
        //接收設備上傳上來的信息
        try {
            //String msgStrs = new String(message.getPayload());
            String msgStrs=new String(message.getPayload(), "UTF-8");

            //Encoding gb2312 = Encoding.GetEncoding("gb2312");
            if (StringUtil.isEmpty(msgStrs)) {
                return;
            }
            log.info("接收平台应急仓硬件消息内容===》" + msgStrs);
            if (!StringUtil.isJSON(msgStrs)) {
                log.warn("接收平台应急仓硬件信息不是json数据" + msgStrs);
                return;
            }
            JSONObject jsonObject = JSONObject.parseObject(msgStrs);
            if (null == jsonObject) {
                log.warn("json解析错误数据" + msgStrs);
                return;
            }
            //处理更新设备数据
            String msg = "";
            List<CabinetDeviceDTO> deviceDTOS = JSONArray.parseArray(jsonObject.getString("devices"), CabinetDeviceDTO.class);
            msg=handleReSmartDevice(deviceDTOS);
            if (StringUtil.isNotEmpty(msg)) {
                log.warn("接收平台设备信息出问题了===" + msg + "----设备信息===" + deviceDTOS);
                return;
            }
            //处理更新点位数据
            List<BitCtrlDTO> bitCtrlDTOS = JSONArray.parseArray(jsonObject.getString("bitCtrls"), BitCtrlDTO.class);
            if (CollectionUtil.isEmpty(bitCtrlDTOS)) {
                log.warn("接收信息不是操作点位数据" + msgStrs);
                return;
            }
            HardwareYjcService hardwareYjcService = ApplicationContextProvider.getBean(HardwareYjcService.class);
            List<BitCtrlVo> param = new ArrayList<>();
            for (BitCtrlDTO bitCtrlDTO : bitCtrlDTOS) {
                for(CabinetDeviceDTO item:deviceDTOS) {
                BitCtrlVo bitCtrlVo = new BitCtrlVo();
                BeanUtils.copyProperties(bitCtrlDTO, bitCtrlVo);
                    bitCtrlVo.setDeviceSn(item.getCode());
                    param.add(bitCtrlVo);
                }
            }
             msg = hardwareYjcService.saveBitCtrl(param, OpTypeEnum.re.name());
            if (StringUtil.isNotEmpty(msg)) {
                log.warn("mqt发送数据信息错误,错误信息====" + msg + "---保存点位信息===" + param);
            }
        }catch (Exception e){
            MqttConsumerConfig client = ApplicationContextProvider.getBean(MqttConsumerConfig.class);
            client.connect();
            log.error("接收數據產生異常再進行連接 msg=="+message+"--error=="+e);
        }
    }

    private  String  handleReSmartDevice(List<CabinetDeviceDTO> deviceDTOS){
        String msg="";
        for(CabinetDeviceDTO item:deviceDTOS){
            HardwareRevServiceImpl hardwareRevService = ApplicationContextProvider.getBean(HardwareRevServiceImpl.class);
            msg=hardwareRevService.smartYjcDevUpInfo(item);
        }
        return msg;
    }

    /**
     * 消息发布成功的回调
     * @author yzg
     * @param iMqttDeliveryToken
     * @return void
     * @date 2021/7/30 17:14
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

