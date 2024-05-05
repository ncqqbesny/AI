package com.app.device.controller.wwj;

import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.HardWare.OperHardDTO;
import com.app.device.domain.Wwj.EquipmentDTO;
import com.app.device.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 设备操作
 */
public interface IEquipmentController {
    //发送任意指令
    ServerResponse<?> equipment(DeviceVo deviceVo);
    //循环发送指令至继电器，坏的
    ServerResponse<?> equipmentAddCount(EquipmentDTO equipmentDTO);
    //让设备继电器操作N次 记录操作
    ServerResponse<?> operHardRelayCount(OperHardDTO operHardDTO);
}
