package com.app.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.app.device.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface IHardwareRevController {
    //硬件信息上报
    public JSONObject netDevUpInfo(@RequestBody String data);
    //器具柜信息上报
    public ServerResponse<?> qjgDevUpInfo(@RequestBody String data);
    //智能设备上报
    public JSONObject smartDevUpInfo(@RequestBody String data);

}
