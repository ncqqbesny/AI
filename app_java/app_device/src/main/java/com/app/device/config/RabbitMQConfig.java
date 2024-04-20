package com.app.device.config;



import com.app.device.utils.StringUtil;
import  com.app.device.services.ICacheStoreService;
import  com.app.device.services.INetDeviceQueryService;
import org.springframework.amqp.core.*;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitMQConfig {
    @Autowired
    ICacheStoreService cacheStoreService;
    @Value("${rabbitmq.springboot.queue}")
    private String rabbitmqQueue;
    @Value("${rabbitmq.springboot.exchange}")
    private String rabbitmqExchange;
    @Value("${rabbitmq.springboot.key}")
    private String rabbitmqKey;

    /**
     * springBoot默认注入的RabbitTempleate是没有实现消息确认机制的，需要我们手动实现
     */

    private RabbitAdmin rabbitAdmin;

    private ConnectionFactory connectionFactory;



    public RabbitMQConfig(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    @PostConstruct
    public void initExchangeAndQueue(){
        /*====================================================方式一====================================================*/
        //定义队列的相关信息
        Queue queue = QueueBuilder.durable(rabbitmqQueue).build();
        //这一步才是真正的在RabbitMQ中创建队列
        rabbitAdmin.declareQueue(queue);

        //定义交换机的相关信息
        Exchange exchange = ExchangeBuilder.directExchange(rabbitmqExchange).build();
        //这一步才是真正的在RabbitMQ中创建交换机
        rabbitAdmin.declareExchange(exchange);

        Binding binding = BindingBuilder.bind(queue).to(exchange).with(rabbitmqKey).noargs();
        //这一步才是真正的将队列绑定到交换机上
        rabbitAdmin.declareBinding(binding);

        /*====================================================方式二====================================================*/
        //rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("rabbitMQ-springBoot-queue",true))
                //.to(new DirectExchange("rabbitMQ-springBoot-exchange",true,false))
                //.with("rabbitMQ-springBoot-key"));
    }

    @Bean("appRabbitTempleate")
    public RabbitTemplate getRabbitTempleate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);


        //开启RabbitMQ的消息到达队列的消息确认机制
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean isReceive, String s) {
                String messageId = correlationData.getId();
                if (isReceive){
                    //交换机成功接收到消息的逻辑
                    String cacheMessageId =(String)cacheStoreService.getCommonCache("ORDER#MESSAGE#SEND#FAILD");
                    //通过redis中是否存在messageId判断消息是否成功到达队列
                    if (StringUtil.isNotEmpty(cacheMessageId) && cacheMessageId.equals(messageId)){
                        //如果messageId不存在，说明消息成功到达队列==》当下情况：消息既到达交换机又成功到达了队列==》消息投递成功
                        /**
                         * 消息投递成功的逻辑：
                         * 删除本地记录表中的记录
                         */
                        System.out.println("消息投递成功的===="+messageId);
                    }else {
                        //如果messageId不存在，说明消息没有到达队列==》当下情况：消息到达交换机并未到达队列
                        /**
                         * 消息未到达队列，消息投递失败的逻辑
                         * 需要移除redis中对应的messageId
                         */
                        cacheStoreService.setCommonCache("ORDER#MESSAGE#SEND#FAILD",messageId);
                    }
                }else {
                    //交换机未接收到消息的逻辑
                    /**
                     * 消息未到达交换机，消息投递失败的逻辑
                     * 需要移除redis中对应的messageId[如果交换机不存在，消息未到达队列的逻辑不会走，redis中就不会有对应的messageId；如果交换机存在，消息未到达队列的逻辑就会走，redis中就会有对应的messageId；不管redis中有没有，保险起见，都需要做移除操作]
                     */
                    cacheStoreService.delCommonCache("ORDER#MESSAGE#SEND#FAILD");
                }
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                /**
                 * 1、此处如果执行了，说明消息未到达队列，消息投递失败
                 * 2、此处如果没有执行，分两种情况：
                 *      A、没有执行，并不能说明消息一定投递成功了，因为没有交换机该逻辑也不会执行，消息投递失败
                 *      B、没有执行，投递成功了，消息成功到达了队列
                 * 该逻辑的执行优先于交换机的逻辑执行
                 */
                String messageId = message.getMessageProperties().getMessageId();
                //将投递失败的消息的唯一标识存入redis中
                cacheStoreService.setCommonCache("ORDER#MESSAGE#SEND#FAILD",messageId);
            }
        });

        return rabbitTemplate;
    }

}
