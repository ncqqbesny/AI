package com.app.device.controller.wwj;

import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.Wwj.EquipmentDTO;
import com.app.device.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface IEquipmentController {

     ServerResponse<?> equipment(DeviceVo deviceVo);
    ServerResponse<?> equipmentAddCount(EquipmentDTO equipmentDTO);
}
