package com.app.device.handler.mqtt;

import com.app.device.config.MqttProviderConfig;
import com.app.device.impl.services.HardwareRevServiceImpl;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yzg
 * @Date: 2023/7/30 16:00
 * @Description:
 */
@Configuration
public class MqttProviderCallBack implements MqttCallback {
    private final static Logger log = LoggerFactory.getLogger(MqttProviderCallBack.class);
    @Value("${spring.mqtt.opclient.id}")
    private String clientId;
    @Autowired
    MqttProviderConfig mqttProviderConfig;
    /**
     * 与服务器断开连接的回调
     * @author yzg
     * @param throwable
     * @return void
     * @date 2023/7/30 16:19
     */
    @Override
    public void connectionLost(Throwable throwable) {
        mqttProviderConfig.connect();
        log.info(clientId + "与服务器断开连接");
    }

    /**
     * 消息到达的回调
     * @author yzg
     * @param s
     * @param mqttMessage
     * @return void
     * @date 2023/7/30 16:19
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    /**
     * 消息发布成功的回调
     * @author yzg
     * @param iMqttDeliveryToken
     * @return void
     * @date 2023/7/30 16:20
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        IMqttAsyncClient client = iMqttDeliveryToken.getClient();
        System.out.println(client.getClientId() + "发布消息成功！");
    }
}
