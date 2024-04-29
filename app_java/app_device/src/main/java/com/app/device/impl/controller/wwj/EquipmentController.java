package com.app.device.impl.controller.wwj;

import cn.hutool.core.collection.CollectionUtil;
import com.app.device.controller.wwj.IEquipmentController;
import com.app.device.domain.Device.DeviceDTO;
import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.Wwj.DtuCmdDTO;
import com.app.device.domain.Wwj.DtuCmdVO;
import com.app.device.domain.Wwj.EquipmentDTO;
import com.app.device.domain.loginVo.User;
import com.app.device.handler.UserInfoContext;
import com.app.device.handler.netty.impl.ServerHandler;
import com.app.device.services.IDeviceService;
import com.app.device.services.wwj.IHardwareWwjService;
import com.app.device.type.DtuCmdStatusEnum;
import com.app.device.utils.ListUtils;
import com.app.device.utils.OrderUtils;
import com.app.device.utils.ServerResponse;
import com.app.device.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "通用设备操作接口")//描述UserController的信息
@RequestMapping("/equipment")
@ApiSort(value = 5)
public class EquipmentController implements IEquipmentController {
    private final static Logger log = LoggerFactory.getLogger(EquipmentController.class);
    @Autowired
    IDeviceService deviceService;
    @Autowired
    IHardwareWwjService hardwareWwjService;

    @Override
    @ApiOperation(value = "控制设备", notes = "根据各种条件给设备发送指令 msg")
    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> equipment(@RequestBody DeviceVo deviceVo) {

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
    @ApiOperation(value = "给设备操作次数", notes = "根设备编号进行操作几次，只针对YRT")
    @RequestMapping(value = "/equipmentAddCount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 3) //排序
    public ServerResponse<?> equipmentAddCount(@RequestBody EquipmentDTO equipmentDTO) {
        //入参 设备id   根据设备id  查询设备最后一次录入数据时候的 ip地址  实现下发
        try {
            if (null == equipmentDTO.getCount()) {
                equipmentDTO.setCount(0);
            }
            if (null == equipmentDTO.getWaitTime()) {
                equipmentDTO.setWaitTime(0);
            }
            if (null == equipmentDTO.getWaitMidTime()) {
                equipmentDTO.setWaitMidTime(0);
            }
            DeviceVo deviceVo = new DeviceVo();
            deviceVo.setDeviceSn(equipmentDTO.getDeviceSn());
            List<?> list = deviceService.getSmartDeviceListBySearch(deviceVo);
            log.info("equipment--参数==" + equipmentDTO);
            if (CollectionUtil.isNotEmpty(list)) {
                //打开继电器
                String openRelayStr = "config,set,doout,1,1\r\n";
                //转码
                //ByteBuf message = Unpooled.copiedBuffer(openRelayStr.getBytes());
                //关闭继电器
                String colseRelayStr = "config,set,doout,1,0\n";
                //转码
                //ByteBuf colseMessage = Unpooled.copiedBuffer(colseRelayStr.getBytes());
                List<DeviceDTO> data = (List<DeviceDTO>) list;
                log.info("执行的设备数据===" + data + "控制信息" + openRelayStr);
                String msg="";
                for(int i=0;i< equipmentDTO.getCount();i++) {
                    //执行设备控制    根据product.getUrl() 上个类写入map  的key  取到map中的 ChannelHandlerContext  执行writeAndFlush发送数据
                    log.info("执行的令===" + i + "--控制命令" + openRelayStr);
                    msg = exeCmd(data.get(0).getIp(), openRelayStr, "打开继电器",equipmentDTO.getWaitTime());
                    log.info("执行的令===" + i + "--控制命令" + colseRelayStr);
                    if (StringUtil.isNotEmpty(msg)) {
                        log.warn("执行命令==="+openRelayStr+"--url==="+data.get(0).getIp()+"--错误==="+msg);
                    }
                    Thread.sleep(equipmentDTO.getWaitMidTime()*1000);
                    msg = exeCmd(data.get(0).getIp(), colseRelayStr, "关闭继电器",equipmentDTO.getWaitTime());
                    if (StringUtil.isNotEmpty(msg)) {
                        log.warn("执行命令==="+openRelayStr+"--url==="+data.get(0).getIp()+"--错误==="+msg);
                    }
                    //ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(Unpooled.copiedBuffer(colseRelayStr.getBytes()));
                    //ChannelFuture  cfu = ServerHandler.map.get(data.get(0).getIp()).channel().writeAndFlush(message);
                    //if (cfu != null) {
                    //cfu.sync();
                    //}
                }
                if(StringUtil.isNotEmpty(msg)){
                    List<DeviceVo> listVo = new ArrayList<>();
                    DeviceVo deviceVo1=new DeviceVo();
                    deviceVo1.setDeviceSn(data.get(0).getDeviceSn());
                    deviceVo1.setRemark(msg);
                    listVo.add(deviceVo1);
                    deviceService.updateByExampleSelective(listVo);
                    log.warn("执行加分出错了-错误==="+msg);
                    return ServerResponse.createByErrorMessage(msg);
                }
                return ServerResponse.createBySuccess();
            } else {
                return ServerResponse.createByError();
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("操作错误");
        }
    }

    private String exeCmd(String url, String cmd, String cmdDesc ,int waiteTime) {
        String msg = "";
        String cmdNo = OrderUtils.getOrderCode(null);
        DtuCmdDTO dtuCmdDTO = new DtuCmdDTO();
        dtuCmdDTO.setCmdNo(cmdNo);
        User context = UserInfoContext.getUser();
        if(null!=context) {
            dtuCmdDTO.setUserId(context.getUserId());
            dtuCmdDTO.setMId(context.getMId());
        }
        dtuCmdDTO.setSendCmd(cmd);
        dtuCmdDTO.setSendTime(new Date());
        dtuCmdDTO.setSendUrl(url);
        dtuCmdDTO.setStatus(DtuCmdStatusEnum.send.ordinal());
        dtuCmdDTO.setCmdDesc(cmdDesc);
        try {
            log.info("开始执行命令==="+cmd+"---参数："+dtuCmdDTO);
            ServerHandler.map.get(url).channel().writeAndFlush(Unpooled.copiedBuffer(cmd.getBytes()));
        }catch (Exception e){
            dtuCmdDTO.setStatus(DtuCmdStatusEnum.bad.ordinal());
            dtuCmdDTO.setCmdDesc("长连接错误"+e.getMessage());
            hardwareWwjService.saveDtuCmd(dtuCmdDTO);
            log.error("长连接执行命令错误==="+cmd+"---参数==="+dtuCmdDTO);
            return "长连接错误";
        }
        msg = hardwareWwjService.saveDtuCmd(dtuCmdDTO);
        if (StringUtil.isNotEmpty(msg)) {
            return msg;
        }
        //一直查询，直到查到结果，查询找过3分钟，表示执行失败，更单据为失败
        Date startDate = new Date();
        while (true) {
            List<DtuCmdVO> dtuCmdList = hardwareWwjService.getDtuCmdList(dtuCmdDTO);
            if (CollectionUtil.isNotEmpty(dtuCmdList) && null!=dtuCmdList.get(0).getRevCmd() && dtuCmdList.get(0).getRevCmd().contains("ok")) {
                DtuCmdDTO dtuCmdDTO1 = new DtuCmdDTO();
                dtuCmdDTO1.setCmdNo(cmdNo);
                dtuCmdDTO1.setStatus(DtuCmdStatusEnum.ok.ordinal());
                dtuCmdDTO1.setRemark("完成");
                hardwareWwjService.saveDtuCmd(dtuCmdDTO1);
                return "";
            }
            //过时了
            if (System.currentTimeMillis() - startDate.getTime() > waiteTime*1000) {
                DtuCmdDTO dtuCmdDTO1 = new DtuCmdDTO();
                dtuCmdDTO1.setCmdNo(cmdNo);
                dtuCmdDTO1.setStatus(DtuCmdStatusEnum.bad.ordinal());
                dtuCmdDTO1.setRemark("超时了");
                hardwareWwjService.saveDtuCmd(dtuCmdDTO1);
                log.info("超时了==="+cmd+"---参数==="+dtuCmdDTO);
                return "超过时间了";
            }

        }
    }

}