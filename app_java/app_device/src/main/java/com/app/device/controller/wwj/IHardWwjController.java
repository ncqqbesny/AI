package com.app.device.controller.wwj;

import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.utils.ServerResponse;

import java.util.List;

public interface IHardWwjController {
    /**
     * 打开端口
     * @param
     * @return
     */
    ServerResponse<?> openHardPort();
    //接收端口信息
    ServerResponse<?> getPortInfoAndProc();
    //操作打开和关闭硬件，cent 分为加减分，1为打开一次，-1为关闭一次。
    public ServerResponse<?> openAndCloseHard(Integer cent);
}
