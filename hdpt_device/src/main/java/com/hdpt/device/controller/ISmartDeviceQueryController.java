package com.hdpt.device.controller;

import com.hdpt.device.domain.Device.DeviceDTO;
import com.hdpt.device.domain.Device.DeviceVo;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.utils.ServerResponse;

import java.util.List;

public interface ISmartDeviceQueryController {

    public ServerResponse<?> getSmartDevicePageList(Integer pageSize, Integer pageNum, Integer sort, DeviceVo deviceVo);
    public ServerResponse<?> getSmartDeviceListByGids(List<String> gids);
    public ServerResponse<?> getSmartDeviceListByMids(List<String> mIds,List<String> deviceTypeCodes);
    public ServerResponse<?> getSmartDeviceList(DeviceVo deviceVo);
}
