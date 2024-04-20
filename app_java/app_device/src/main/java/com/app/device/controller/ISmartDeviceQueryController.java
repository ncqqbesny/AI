package com.app.device.controller;

import com.app.device.domain.Device.DeviceVo;
import com.app.device.utils.ServerResponse;

import java.util.List;

public interface ISmartDeviceQueryController {

    public ServerResponse<?> getSmartDevicePageList(Integer pageSize, Integer pageNum, Integer sort, DeviceVo deviceVo);
    public ServerResponse<?> getSmartDeviceListByGids(List<String> gids);
    public ServerResponse<?> getSmartDeviceListByMids(List<String> mIds,List<String> deviceTypeCodes);
    public ServerResponse<?> getSmartDeviceList(DeviceVo deviceVo);
}
