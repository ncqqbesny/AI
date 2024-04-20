package com.app.device.impl.controller;

import com.alibaba.fastjson.JSONObject;
import com.app.device.controller.IHardwareRevController;
import com.app.device.services.IDeviceExtendService;
import com.app.device.services.IDeviceTypeService;
import com.app.device.services.IHardwareCabService;
import com.app.device.services.IHardwareRevService;
import com.app.device.utils.ServerResponse;
import com.app.device.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@Api(tags = "接收设备信息接口")//描述UserController的信息
@RequestMapping("/hd")
public class HardwareRevController implements IHardwareRevController {

    private final static Logger log = LoggerFactory.getLogger(HardwareRevController.class);
    @Autowired
    IHardwareRevService hardwareRevService;
    @Autowired
    IDeviceExtendService deviceExtendService;
    @Autowired
    IDeviceTypeService deviceTypeService;


    @Override
    @ApiOperation(value = "远程网络设备信息上报", notes = "全国各地网络设备信息上报接口")
    @RequestMapping(value = "/netup", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject netDevUpInfo(@RequestBody String data) {
        JSONObject jb = new JSONObject();
        String des = "success";
        String code = "0";
        try {
            if (StringUtil.isEmpty(data)) {
                jb.put("des", "没有参数");
                jb.put("code", "-1");
                return jb;
            }
            String msg = hardwareRevService.netDevUpInfo(data);
            if (StringUtil.isNotEmpty(msg)) {
                jb.put("des", msg);
                jb.put("code", "-1");
                return jb;
            }

        } catch (Exception e) {
            log.info("netDevUpInfo param:" + data + "--error:" + e);
        } finally {
            jb.put("des", des);
            jb.put("code", code);
            return jb;
        }
    }

    @Override
    @ApiOperation(value = "远程器具柜设备信息上报", notes = "全国各器具柜设备信息上报接口")
    @RequestMapping(value = "/qjgup", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<?> qjgDevUpInfo(@RequestBody String data) {
        try {
            if (StringUtil.isEmpty(data)) {
                return ServerResponse.createByErrorMessage("没有参数");
            }
            String msg = hardwareRevService.qjgDevUpInfo(data);
            if (StringUtil.isEmpty(msg)) {
                return ServerResponse.createByErrorMessage(msg);
            }
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.info("qjgDevUpInfo param:" + data + "---error:" + e);
            return ServerResponse.createByError();
        }
    }

    /**
        serialNo 模块 ID 设备出厂编号
        temperature 环境温度 测量范围：-30～80℃；测量精度：±0.5℃
        humidity 环境湿度 测量范围：0～100%RH；测量精度：±
        3%RH
        angle 倾斜角度 量程范围：-180～180°；测量精度：±5° power 模块电量 量程范围：0～100%；测量精度：3%
        geo 地理位置信息 符合 NMEA0183，内容包括纬度、纬度半
        球、经度、经度半球、GPS 状态、解算卫
        参数 参数说明 备注
        serialNo 模块 ID 设备出厂编号
        星数量、HDOP 水平因子、海拔高度
        img 表头图片 图片规格：不小于 640×480 dpi；图片清
        晰度：无虚焦、无反光，主色差超过 10，
        数据格式见附录（一）图片数据解析
        collectTime 采集时间 数据采集时间，格式 yyyy-mm-dd
        24hh:mi:ss
        keepalive 通讯心跳 默认 60 分钟，单位：分钟
     **/
    @Override
    @ApiOperation(value = "智能设备信息上报", notes = "全国各地智能设备信息上报接口")
    @RequestMapping(value = "/smartcallback", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject smartDevUpInfo(@RequestBody String data) {
        log.info("智能设备信息上报参数=====" + data);
        JSONObject jb = new JSONObject();
        String message = "callback successful";
        String status = "200";
        try {
            if (StringUtil.isEmpty(data)) {
                jb.put("status","400");
                jb.put("message","参数为空");
                return jb;
            }
            String stauts="1";
            String msg = hardwareRevService.smartDevUpInfo(data,stauts);
            if (StringUtil.isNotEmpty(msg)) {
                jb.put("status","500");
                jb.put("message",msg);
                return jb;
            }
            jb.put("status","200");
            jb.put("message","callback successful");
            return jb;
        } catch (Exception e) {
            log.info("smartDevUpInfo param:" + data + "---error:" + e);
            jb.put("status","400");
            jb.put("message","callback error:"+e.getMessage());
            return jb;
        }
    }

    @ApiOperation(value = "测试", notes = "测试")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject test(@RequestBody String data) {
        JSONObject jb = new JSONObject();
        String des = "success";
        String code = "0";
        try {
            if (StringUtil.isEmpty(data)) {
                jb.put("des", "没有参数");
                jb.put("code", "-1");
                return jb;
            }
            String msg = "";
            if (StringUtil.isNotEmpty(msg)) {
                jb.put("des", msg);
                jb.put("code", "-1");
                return jb;
            }

        } catch (Exception e) {
            log.info("netDevUpInfo param:" + data + "--error:" + e);
        } finally {
            jb.put("des", des);
            jb.put("code", code);
            return jb;
        }
    }

}
