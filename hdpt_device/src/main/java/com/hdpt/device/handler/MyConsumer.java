package com.hdpt.device.handler;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MyConsumer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Value("${rabbitmq.springboot.queue}")
    private String rabbitmqQueue;
    @Value("${rabbitmq.springboot.exchange}")
    private String rabbitmqExchange;
    @Value("${rabbitmq.springboot.key}")
    private String rabbitmqKey;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "jfshx_data",durable = "true"),
            exchange = @Exchange(value = "linkom_exchange",type = ExchangeTypes.DIRECT),
            key = "linkomkey"),
            ackMode = "MANUAL")
    /**
     * // 当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
     * RECORD,
     * // 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交
     * BATCH,
     * // 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
     * TIME,
     * // 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交
     * COUNT,
     * // TIME |　COUNT　有一个条件满足时提交
     * COUNT_TIME,
     * // 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
     * MANUAL,
     * // 手动调用Acknowledgment.acknowledge()后立即提交
     * MANUAL_IMMEDIATE,
     *
     */

    public void getMessage(@Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        channel.basicQos(1);
        List<String> messageContent = JSONObject.parseObject(message, List.class);
        String orderId = messageContent.get(0);
        System.out.println("getMessage"+messageContent);
        RLock lock = redissonClient.getLock("ZFLS#"+orderId);
        try {
            /**if (stringRedisTemplate.opsForValue().get("ZFLS#"+orderId) != null){
                lock.lock();
                if (stringRedisTemplate.opsForValue().get("ZFLS#"+orderId) != null){
                    //获取到消息后开始进行记分处理==================================================

                     * 此处代码省略


                    //积分处理完成后确认消息已被处理ACK==================================================
                    channel.basicAck(deliveryTag,false);

                    //为了防止消息重复消费，在消息处理完成后还需将消息放入redis中
                    stringRedisTemplate.opsForValue().set("ZFLS#"+orderId,"true");
                }
            }*/
        }finally {
            //channel.basicNack(deliveryTag, false, false);
            //lock.unlock();
        }
    }


}
