package com.app.device.services;


import com.app.device.domain.Device.DeviceTypeDTO;

import java.util.List;
import java.util.Map;

public interface IDeviceTypeService {

    List<DeviceTypeDTO>  findByNameAndMid(String name, Integer mid);
    List<DeviceTypeDTO> findByName(String name);

}
