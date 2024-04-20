package com.app.device.handler;

import com.app.device.config.MqttConsumerConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerListenerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //啟動mqtt訂閱數據
        MqttConsumerConfig client = ApplicationContextProvider.getBean(MqttConsumerConfig.class);
        client.init();
    }
}
