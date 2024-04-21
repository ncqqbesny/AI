package com.app.device.impl.controller.wwj;

import cn.hutool.core.collection.CollectionUtil;
import com.app.device.domain.Device.DeviceDTO;
import com.app.device.domain.Device.DeviceVo;
import com.app.device.handler.netty.impl.ServerHandler;
import com.app.device.impl.services.DeviceServiceImpl;
import com.app.device.services.IDeviceService;
import com.app.device.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//**控制设备 **
@Controller
@RequestMapping("/equipmenContro")
public class EquipmentController {
    private final static Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    private IDeviceService deviceService;

    @ApiOperation(value = "控制硬件操作", notes = "打开与控制硬件操作")
    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> equipment(@RequestBody DeviceVo deviceVo){
        //入参 设备id   根据设备id  查询设备最后一次录入数据时候的 ip地址  实现下发
        try {
            List<?> list = deviceService.getSmartDeviceListBySearch(deviceVo);
            if (CollectionUtil.isNotEmpty(list)) {
                //需要给设备发送的 16进制数据
                String msg = " 16 27 88 90 12 45 31 15 41 ";
                msg=deviceVo.getMsg();
                log.info("equipment--参数=="+deviceVo);
                //转码
                ByteBuf message = Unpooled.copiedBuffer(msg.getBytes());
                List<DeviceDTO>  data=(List<DeviceDTO>)list;
                log.info("执行的设备数据==="+data);
                //执行设备控制    根据product.getUrl() 上个类写入map  的key  取到map中的 ChannelHandlerContext  执行writeAndFlush发送数据
                ServerHandler.map.get(data.get(0).getGateway()).channel().writeAndFlush(message);
                return ServerResponse.createBySuccess();
            } else {
                return  ServerResponse.createByError();
            }
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("操作错误");
        }

    }
}