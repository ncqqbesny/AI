package com.hdpt.device.services;


import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface INetDeviceService {


    String addInfo(NetDeviceDTO netDeviceDTO);

    String updateByExampleSelective( List<NetDeviceVo> saveListInfo);

    String delInfo(List<Integer> ids);
    String insertOrUpdate(String data);
    String saveListInfo(List<NetDeviceVo> saveListInfo);
    String saveExcelInfo(List<NetDeviceVo> saveListInfo,String remark);
    String netDevUpInfo(String data);

}
