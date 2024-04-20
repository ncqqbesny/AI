package com.app.device.config;



import com.app.device.handler.mqtt.MqttProviderCallBack;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * @Author: yzg
 * @Date: 2024/1/30 15:32
 * @Description:
 */
@Configuration
@Slf4j
public class MqttProviderConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.opclient.id}")
    private String clientId;
    /**
     * 客户端对象
     */
    private MqttClient client;

    /**
     * 客户端连接服务端
     * @author xct
     * @param
     * @return void
     * @date 2021/7/30 16:01
     */

    public void connect(){
        try {
            //创建MQTT客户端对象
            client = new MqttClient(hostUrl,clientId,new MemoryPersistence());
            //连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            //是否清空session，设置为false表示服务器会保留客户端的连接记录（订阅主题，qos），客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
            //设置为true表示每次连接到服务端都是以新的身份
            options.setCleanSession(true);
            //设置连接用户名
            options.setUserName(username);
            //设置连接密码
            options.setPassword(password.toCharArray());
            //设置超时时间，单位为秒
            options.setConnectionTimeout(100);
            //设置心跳时间 单位为秒，表示服务器每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
            options.setKeepAliveInterval(20);
            //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic",(clientId + "与服务器断开连接").getBytes(),0,false);
            //设置回调
            client.setCallback(new MqttProviderCallBack());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(int qos,boolean retained,String topic,String message){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        //当订阅消费端服务器重新连接MQTT服务器时，总能拿到该主题最新消息， 这个时候我们需要把retained设置为true;
        mqttMessage.setRetained(retained);
        //mqttMessage.setPayload(message.getBytes());
        //解决中文乱码问题
        mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));
        //主题目的地，用于发布/订阅消息
        MqttTopic mqttTopic = client.getTopic(topic);
        //提供一种机制来跟踪消息的传递进度。
        //用于在以非阻塞方式（在后台运行）执行发布时跟踪消息的传递进度
        MqttDeliveryToken token;
        try {
            //将指定消息发布到主题，但不等待消息传递完成。返回的token可用于跟踪消息的传递状态。
            //一旦此方法干净地返回，消息就已被客户端接受发布。当连接可用时，将在后台完成消息传递。
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

