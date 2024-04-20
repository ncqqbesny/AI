package com.app.device.services;

import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.utils.CommonPage;

import java.util.List;
import java.util.Map;

public interface IHardwareYjcService {
    /**
     * 界面操作点位按钮
     * @param
     * @return
     */
    public String saveBitCtrl(List<BitCtrlVo> bitCtrlVos,String operType) ;

    public String saveListInfo(List<BitCtrlVo> bitCtrlVos);
    //删除信息
    public String delInfo(List<Integer> ids);
    //分页查询
    public CommonPage<BitCtrlDTO> getBitCtrlPageList(Integer pageSize, Integer pageNum, Integer sort, BitCtrlVo bitCtrlVo);

    //开关查询
    public Map<String,String> getBitCtrlList(BitCtrlVo bitCtrlVo);



}

