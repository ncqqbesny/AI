package com.hdpt.device.services;

import com.hdpt.device.domain.Cabinet.CabinetSaveVO;

import java.util.List;

public interface IHardwareCabService {
      //保存主柜信息
    public String saveCabinet(CabinetSaveVO cabinetSaveVO);
    String delInfo(List<Integer> ids);


}
