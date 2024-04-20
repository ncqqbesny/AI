package com.app.device.handler.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class SubscribeTopicExample {
    private static final String TOPIC_NAME = "/topic/test"; // 订阅的主题名称

    public static void main(String[] args) throws Exception {
            String broker = "tcp://121.40.242.143:1883"; // MQTT服务器地址
            String clientId = "myClient"; // 客户端ID
            String username = "root"; // 用户名
            String password = "12345699"; // 密码

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            try {
                MqttClient mqttClient = new MqttClient(broker, clientId);

                mqttClient.connect(options);

                System.out.println("Connected to MQTT server");

                // 订阅主题
                String topic = "test";
                int qos = 2;
                mqttClient.subscribe(topic, qos);

                // 设置消息处理函数
                mqttClient.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println("Received message: " + new String(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });

                // 发送消息
                String payload = "Hello, MQTT yzg!";
                mqttClient.publish("test", payload.getBytes(), 0, false);

                Thread.sleep(5000); // 等待5秒钟

                mqttClient.disconnect();

                System.out.println("Disconnected from MQTT server");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
