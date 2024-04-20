package com.app.device.services;


import com.app.device.domain.hdUp.NetDeviceDTO;
import com.app.device.domain.hdUp.NetDeviceQuery;
import com.app.device.domain.hdUp.NetDeviceVo;
import com.app.device.domain.loginVo.UserDTO;
import com.app.device.utils.CommonPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface INetDeviceQueryService {

    List<NetDeviceDTO> selectByIds(List<Integer> ids);
    List<NetDeviceDTO> findList(NetDeviceVo netDeviceVo);
    List<NetDeviceDTO> selectByMids(List<Integer> mIds,List<String> deviceTypes);
    CommonPage<NetDeviceDTO> getPageList(Integer pageSize, Integer pageNum, Integer sort,NetDeviceVo netDeviceVo);
    List<Map> getSystemParam(String paramType);
    //保存图片和文本文件（写入灭火器输入文件）
    Map<String,Object> savePicAndTextFile(Map<String,String> fileParamMap);
    //获得灭火器的配置
    public Map<String,String> getFirexConfig();

}