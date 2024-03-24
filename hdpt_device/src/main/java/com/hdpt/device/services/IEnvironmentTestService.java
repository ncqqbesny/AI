package com.hdpt.device.services;


import com.hdpt.device.domain.EnvironmentTest.DrawImageInfoVo;
import com.hdpt.device.domain.EnvironmentTest.DrawImageVo;
import com.hdpt.device.domain.EnvironmentTest.SceneItemDeviceDataVO;

import java.util.List;
import java.util.Map;

public interface IEnvironmentTestService {
    //构建数据
    DrawImageVo buildDataStyle(DrawImageInfoVo drawImageInfoVo, List<SceneItemDeviceDataVO> deviceDataVoList, Boolean isAlarm);
    //画图
    public String drawImg(DrawImageVo drawImageVo);

    public String pushImg2InkScreen();
}
