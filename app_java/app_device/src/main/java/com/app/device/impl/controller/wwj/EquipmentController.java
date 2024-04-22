package com.app.device.impl.controller.wwj;

import cn.hutool.core.collection.CollectionUtil;
import com.app.device.controller.wwj.IEquipmentController;
import com.app.device.domain.Device.DeviceDTO;
import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.Device.EquipmentDTO;
import com.app.device.handler.netty.impl.ServerHandler;
import com.app.device.services.IDeviceService;
import com.app.device.utils.ServerResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "设备操作接口")//描述UserController的信息
@RequestMapping("/equipment")
@ApiSort(value = 5)
public class EquipmentController implements IEquipmentController {
    private final static Logger log = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    private IDeviceService deviceService;

    @Override
    @ApiOperation(value = "控制设备", notes = "根据各种条件给设备发送指令 msg")
    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> equipment(@RequestBody  DeviceVo deviceVo) {

        //入参 设备id   根据设备id  查询设备最后一次录入数据时候的 ip地址  实现下发
        try {
            List<?> list = deviceService.getSmartDeviceListBySearch(deviceVo);
            if (CollectionUtil.isNotEmpty(list)) {
                //需要给设备发送的 16进制数据
                String msg = " 16 27 88 90 12 45 31 15 41 ";
                msg = deviceVo.getMsg();
                log.info("equipment--参数==" + deviceVo);
                //转码
                ByteBuf message = Unpooled.copiedBuffer(msg.getBytes());
                List<DeviceDTO> data = (List<DeviceDTO>) list;
                log.info("执行的设备数据===" + data);
                //执行设备控制    根据product.getUrl() 上个类写入map  的key  取到map中的 ChannelHandlerContext  执行writeAndFlush发送数据
                ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(message);
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByError();
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("操作错误");
        }

    }


    @Override
    @ApiOperation(value = "给设备加分", notes = "根设备编号进行加分，只针对银尔泰")
    @RequestMapping(value = "/equipmentAddCount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> equipmentAddCount(@RequestBody EquipmentDTO equipmentDTO) {
        //入参 设备id   根据设备id  查询设备最后一次录入数据时候的 ip地址  实现下发
        try {
            if (null == equipmentDTO.getCount()) {
                equipmentDTO.setCount(0);
            }
            if (null == equipmentDTO.getWaitTime()){
                equipmentDTO.setWaitTime(0);
            }
            DeviceVo deviceVo = new DeviceVo();
            deviceVo.setDeviceSn(equipmentDTO.getDeviceSn());
            List<?> list = deviceService.getSmartDeviceListBySearch(deviceVo);
            log.info("equipment--参数==" + deviceVo);
            if (CollectionUtil.isNotEmpty(list)) {
                //打开继电器
                String openRelayStr = "config,set,doout,1,1\r\n";
                //转码
                //ByteBuf message = Unpooled.copiedBuffer(openRelayStr.getBytes());
                //关闭继电器
                String colseRelayStr = "config,doout,ok,1,1\r\n";
                //转码
                //ByteBuf colseMessage = Unpooled.copiedBuffer(colseRelayStr.getBytes());

                List<DeviceDTO> data = (List<DeviceDTO>) list;
                log.info("执行的设备数据===" + data + "控制信息" + openRelayStr);

                    for (int i = 0; i < equipmentDTO.getCount(); i++) {
                        //执行设备控制    根据product.getUrl() 上个类写入map  的key  取到map中的 ChannelHandlerContext  执行writeAndFlush发送数据
                        log.info("执行的令===" + i + "--控制命令" + openRelayStr);
                        ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(Unpooled.copiedBuffer(openRelayStr.getBytes()));
                        log.info("等待1秒执行关闭继电器" + i );
                        Thread.sleep(equipmentDTO.getWaitTime()*1000);
                        log.info("执行的令===" + i + "--控制命令" + colseRelayStr);
                        ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(Unpooled.copiedBuffer(colseRelayStr.getBytes()));
                        //ChannelFuture  cfu = ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(message);
                        //if (cfu != null) {
                            //cfu.sync();
                        //}
                    }
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByError();
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("操作错误");
        }
    }
}