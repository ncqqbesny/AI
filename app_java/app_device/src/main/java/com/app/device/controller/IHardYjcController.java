package com.app.device.controller;

import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.domain.BitCtrl.CabinetDeviceVo;
import com.app.device.domain.Cabinet.CabinetWarningVo;
import com.app.device.utils.ServerResponse;


import java.util.List;

public interface IHardYjcController {
    /**
     * 按钮操作
     * @param param
     * @return
     */
    ServerResponse<?> operBitCtrl(List<BitCtrlVo> param);

    //保存控制信息
    public ServerResponse<?> saveListInfo(List<BitCtrlVo> bitCtrlVos);
    //删除信息
    public ServerResponse<?> delInfo(List<Integer> ids);
    //分页查询
    public ServerResponse<?> getBitCtrlPageList(Integer pageSize, Integer pageNum, Integer sort, BitCtrlVo bitCtrlVo);

    public ServerResponse<?> getBitCtrlList(BitCtrlVo bitCtrlVo);


}
