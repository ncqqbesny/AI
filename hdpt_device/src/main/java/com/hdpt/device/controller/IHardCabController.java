package com.hdpt.device.controller;

import com.hdpt.device.domain.Cabinet.CabinetSaveVO;
import com.hdpt.device.utils.ServerResponse;


import java.util.List;

public interface IHardCabController {


    public ServerResponse<?> saveCabinet(CabinetSaveVO cabinetSaveVO);
    public ServerResponse<?> delCabInfo(List<Integer> ids);
}
