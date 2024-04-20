package com.app.device.controller;

import com.app.device.domain.Device.RemoteCtrlVo;
import com.app.device.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ISmartDeviceController {
    //保存智能设备
    public ServerResponse<?> saveSmartListInfo(@RequestBody  String data);
    //删除信息
    public ServerResponse<?> delSmartDeviceInfo(List<String> gids);
    //配置远程任务
    public ServerResponse<?> remoteCtrlFireDevice(RemoteCtrlVo remoteCtrlVo);
    //订阅灭火器设备信息
    public ServerResponse<?> subscribeFireDevice(String subscribe,List<String> serialNos);
    //test
    public ServerResponse<?> test(String body);
}
