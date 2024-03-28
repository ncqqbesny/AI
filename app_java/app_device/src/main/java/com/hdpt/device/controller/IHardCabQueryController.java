package com.hdpt.device.controller;

import com.hdpt.device.domain.Cabinet.CabinetQueryVo;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.utils.ServerResponse;

import java.util.List;

public interface IHardCabQueryController {
    // 99020100获得柜信息
    public ServerResponse<?> getPageCabList(Integer pageSize, Integer pageNum, Integer sort, CabinetQueryVo cabinetQueryVo);
    public ServerResponse<?> getCabListByIds(List<Integer> ids);
    public ServerResponse<?> getCabListByMids(List<Integer> mIds);

    public ServerResponse<?> getCabList(CabinetQueryVo cabinetQueryVo);
}
