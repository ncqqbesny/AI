package com.hdpt.device.services;


import com.hdpt.device.domain.Device.DeviceExtendDTO;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.utils.CommonPage;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IDeviceExtendService {

    List<Map> mySelect(String sql);
    public int selectColumn(String gid);
    //插入及更新
    public String insertUpdate(Map<String,String> dataMap,String typeName,Integer mId) throws InvocationTargetException, IllegalAccessException;
    List<DeviceExtendDTO> findByDeviceGid(String deviceGid, Integer mid);

    CommonPage<?>getPageList(Integer pageSize, Integer pageNum, Integer sort, Map<String,String> paramMap);
    Map<String, Object> findByDeviceGidAndMid(String typeCode,String deviceGid,Integer mid) throws IllegalAccessException;

}
