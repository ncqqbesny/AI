package com.hdpt.device.controller;

import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.utils.ServerResponse;

import java.util.List;

public interface INetDeviceQueryController {

    public ServerResponse<?> getNetDevicePageList(Integer pageSize, Integer pageNum, Integer sort, NetDeviceVo netDeviceVo);
    public ServerResponse<?> getNetDeviceListByIds(List<Integer> ids);
    public ServerResponse<?> getNetDeviceListByMids(List<Integer> mIds,List<String> deviceTypes);

    public ServerResponse<?> getNetDeviceList(NetDeviceVo netDeviceVo);
}
