package com.app.device.handler;


import com.app.device.services.IHardwareRevService;
import com.app.device.services.IDeviceService;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * 鲁帮通消息订阅
 */
@Component
public class SfxCustomer {
    private static Logger log = LoggerFactory.getLogger(SfxCustomer.class);
    @Value("${sfx.rabbitmq.host}")
    private String host;
    @Value("${sfx.rabbitmq.port}")
    private int port;
    @Value("${sfx.rabbitmq.username}")
    private String username;
    @Value("${sfx.rabbitmq.password}")
    private String password;
    @Value("${sfx.rabbitmq.vhost}")
    private String vhost;
    @Value("${sfx.rabbitmq.queue}")
    private String queue;
    @Autowired
    IHardwareRevService hardwareRevService;

    @PostConstruct
    private void init() {
        try {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setVirtualHost(vhost);
            factory.setUsername(username);
            factory.setPassword(password);
            Connection connection = factory.newConnection();

            //创建通道
            Channel channel = connection.createChannel();

            //通道绑定队列：与生产端一致
            channel.queueDeclare(queue, true, false, false, null);
            //每次缓存5个消息在本地
            channel.basicQos(5);
            //获取消息
            //参数1: 消费那个队列的消息 队列名称
            //参数2: 开始消息的自动确认机制[只要消费就从队列删除消息]
            //参数3: 消费时的回调接口
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    log.info("取出消息:===>" + new String(body));
                    //channel.basicAck(envelope.getDeliveryTag(), false);
                    String stauts="1"; //在线
                    hardwareRevService.smartSfxDevUpInfo(new String(body),stauts);
                }

            });
        } catch (Exception e) {
            log.error("LBTCustomer-ini====error:" + e);
        }
    }
}