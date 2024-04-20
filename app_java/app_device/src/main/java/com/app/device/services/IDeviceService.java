package com.app.device.services;


import com.app.device.domain.Device.DeviceExtendDTO;
import com.app.device.domain.Device.DeviceVo;
import com.app.device.domain.Device.RemoteCtrlVo;
import com.app.device.domain.hdUp.NetDeviceVo;
import com.app.device.utils.CommonPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IDeviceService {

    List<Map> mySelect(String sql);
    public int selectColumn(String gid);
    //插入及更新
    public String insertUpdate(Map<String,String> dataMap,String typeName,Integer mId) throws InvocationTargetException, IllegalAccessException;
    List<?> findByDeviceGid(String deviceGid, String mid);
    CommonPage<?>getPageList(Integer pageSize, Integer pageNum, Integer sort, DeviceVo deviceVo) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException;
    List<?> getSmartDeviceListByGids(List<String> gids) throws IllegalAccessException;
    List<?> getSmartDeviceListByMids(List<String> mIds, List<String> deviceTypeCodes) throws IllegalAccessException;
    List<?> getSmartDeviceListBySearch(DeviceVo deviceVo ) throws IllegalAccessException;
    //获得灭火器对接平台token
    Map<String,String>  getFireAccessToken();
    //获取灭火器对接平台设备列表
    List<String> getAllFireDeviceList();
    //配置灭火器远程任务
    String remoteCtrlFireDevice(RemoteCtrlVo remoteCtrlVo);
    //订阅灭火器平台设备
    String subscribeFireDevice(String subscribe,List<String> serialNos);
    //发送mq信息
    public String sendMessage();

    String updateByExampleSelective( List<DeviceVo> saveListInfo);
    String delInfo(List<String> gids);
    String saveDeviceInfo(String data);
}
