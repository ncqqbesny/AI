package com.hdpt.device.services;


import com.hdpt.device.domain.Device.DeviceTypeDTO;

import java.util.List;
import java.util.Map;

public interface IDeviceTypeService {

    List<DeviceTypeDTO>  findByNameAndMid(String name, Integer mid);
    List<DeviceTypeDTO> findByName(String name);

}
