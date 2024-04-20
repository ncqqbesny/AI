package com.app.device.services.wwj;

import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.utils.CommonPage;
import com.app.device.utils.ServerResponse;

import java.util.List;
import java.util.Map;

public interface IHardwareWwjService {
    /**
     * 打开端口
     * @param port
     * @return
     */
    String openHardPort();
    //接收端口信息
    Map<String,Object> getPortInfoAndProc();
    //操作打开和关闭硬件，cent 分为加减分，1为打开一次，-1为关闭一次。
    String openAndCloseHard(Integer cent);

}

