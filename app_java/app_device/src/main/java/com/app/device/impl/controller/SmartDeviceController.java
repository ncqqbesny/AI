package com.app.device.impl.controller;

import com.app.device.domain.Device.RemoteCtrlVo;
import com.app.device.domain.system.AutoLog;
import com.app.device.handler.netty.ChannelMap;
import com.app.device.services.IHardwareRevService;
import com.app.device.utils.ServerResponse;
import com.app.device.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.app.device.controller.ISmartDeviceController;
import com.app.device.handler.netty.Message;
import com.app.device.services.IDeviceService;
import com.app.device.services.INetDeviceQueryService;
import com.app.device.services.INetDeviceService;
import com.app.device.utils.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.*;

//@Slf4j
@RestController
@Api(tags = "智能设备操作接口")//描述UserController的信息
@RequestMapping("/smartdevice")
public class SmartDeviceController implements ISmartDeviceController {
    private final static Logger log = LoggerFactory.getLogger(SmartDeviceController.class);
    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String path;
    @Autowired
    IDeviceService deviceService;
    @Autowired
    INetDeviceQueryService netDeviceQueryService;
    @Autowired
    IHardwareRevService hardwareRevService;

    @Override
    @ApiOperation(value = "保存智能设备信息", notes = "批量添加和编辑智能设备信息")
    @RequestMapping(value = "/saveSmartListInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 8) //排序
    @AutoLog(value ="保存智能设备信息", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> saveSmartListInfo(@RequestBody String data) {
        String msg = deviceService.saveDeviceInfo(data);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "删除智能设备信息", notes = "按gid批量删除智能设备信息")
    @RequestMapping(value = "/delSmartDeviceInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    @AutoLog(value ="删除智能设备信息", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> delSmartDeviceInfo(@RequestBody List<String> gids) {
        String msg = deviceService.delInfo(gids);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "向灭火平台给设备发送控制命令", notes = "目标设备接收到控制命令后执行相应操作，并向平台返回执行结果，设备集合 注：serialNos为空则对所有对接平台设备进行配置")
    @RequestMapping(value = "/remoteCtrlFireDevice", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> remoteCtrlFireDevice(@RequestBody RemoteCtrlVo remoteCtrlVo) {
        String msg = deviceService.remoteCtrlFireDevice(remoteCtrlVo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "订阅灭火器平台设备发送信息", notes = "订阅灭火器平台设备发送信息给维护平台")
    @RequestMapping(value = "/subscribeFireDevice", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subscribe", value = "订阅开关 ON-开启订阅，OFF-关闭订阅，为空时为ON", paramType = "Query", dataType = "String", required = false),
            @ApiImplicitParam(name = "serialNos", value = "设备集合 注：serialNos为空则对所有对接平台设备进行配置 ", paramType = "Query", dataType = "String", required = false)

    })
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> subscribeFireDevice(@RequestParam(value="subscribe",required=false) String subscribe,@RequestParam(value="serialNos",required=false) List<String> serialNos) {
        String msg = deviceService.subscribeFireDevice(subscribe,serialNos);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @ApiOperation(value = "demo测试", notes = "设备测试")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> test(@RequestBody  String body) {
        //String stauts="1"; //在线
        //String upJsonStr="{\"vibrate\":\"1\",\"pressure\":\"102787.21875\",\"reportAt\":1702974436832,\"type\":\"DATA\",\"people\":\"1\",\"uri\":\".state.device_state\",\"light\":\"0\",\"smoke_status\":1,\"water_battery\":\"100\",\"noise\":\"60\",\"temperature\":\"13.5\",\"humidity\":\"46.400001525879\",\"water_status\":0,\"thing\":{\"activated\":false,\"deviceSn\":\"JFSHX1212\",\"id\":\"1734392044668899330\"}}";
        //hardwareRevService.smartSfxDevUpInfo(upJsonStr,stauts);
        //deviceService.sendMessage();
        log.info(" *************發送數據給客戶端");
        // 客户端ID
        Message msg=new Message("{\"msg_type\":2}");
        msg.setId("xskip");
        msg.setContent("adfadftest");
        String id = msg.getId();
        ChannelHandlerContext channel = ChannelMap.getChannelMap().get("ttt");
        if (null == channel) {
            throw new RuntimeException("客户端已离线");
        }
        byte[] bytes = "我是主动发送的!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = channel.alloc().buffer();
        buffer.writeBytes(bytes);
        //channel.writeAndFlush("ASFDASFDSAFD33333");
        return ServerResponse.createBySuccess();
    }


}
