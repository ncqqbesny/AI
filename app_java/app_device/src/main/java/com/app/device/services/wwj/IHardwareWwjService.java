package com.app.device.services.wwj;

import com.app.device.domain.Wwj.DtuCmdDTO;
import com.app.device.domain.Wwj.DtuCmdVO;

import java.util.List;
import java.util.Map;

public interface IHardwareWwjService {
    /**
     * 打开端口
     * @param
     * @return
     */
    String openHardPort();
    //接收端口信息
    Map<String,Object> getPortInfoAndProc();
    //操作打开和关闭硬件，cent 分为加减分，1为打开一次，-1为关闭一次。
    String openAndCloseHard(Integer cent);

    /**
     * 保存dtu cmd数据
     * @param dtuCmdDto
     * @return
     */
    String saveDtuCmd(DtuCmdDTO dtuCmdDto);

    /**
     * 查询dtu 命令数据
     * @param dtuCmdDto
     * @return
     */
    List<DtuCmdVO> getDtuCmdList(DtuCmdDTO dtuCmdDto);

}

