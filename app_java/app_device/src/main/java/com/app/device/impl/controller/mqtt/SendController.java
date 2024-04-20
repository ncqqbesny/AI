package com.app.device.impl.controller.mqtt;


import com.app.device.config.MqttProviderConfig;
import com.app.device.utils.DateUtils;
import com.app.device.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: yzg
 * @Date: 2023/7/30 16:26
 * @Description:
 */
@RestController
@Api(tags = "三方接口")//描述UserController的信息
@RequestMapping("/appdevice")
public class SendController {

    @Autowired
    private MqttProviderConfig providerClient;


    @ApiOperation(value = "mqtt测试", notes = "mqtt测试")
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public String sendMessage(int qos, boolean retained, String topic, String message) {
        try {
            qos=1;
            retained=true;
            topic="yjc-ctrl";
            message="yzg mqtt test1"+ DateUtils.getYYYYMMDDHHMMSSDate(new Date());
            providerClient.connect();
            providerClient.publish(qos, retained, topic, message);
            return "发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
    }
    @ApiOperation(value = "开锁", notes = "开锁",hidden = true)
    @RequestMapping(value = "/openLock", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> openLock() {
        try {
            int qos=1;
            boolean retained=true;
            String topic="yjc-ctrl";
            String message=DateUtils.getYYYYMMDDHHMMSSDate(new Date())+";ctrlLock;1";
            providerClient.connect();
            providerClient.publish(qos, retained, topic, message);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    @ApiOperation(value = "关锁", notes = "关锁",hidden = true)
    @RequestMapping(value = "/closeLock", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> closeLock() {
        try {
            int qos=1;
            boolean retained=true;
            String topic="yjc-ctrl";
            String message=DateUtils.getYYYYMMDDHHMMSSDate(new Date())+";ctrlLock;2";
            providerClient.connect();
            providerClient.publish(qos, retained, topic, message);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

}

