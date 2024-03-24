package com.hdpt.device.impl.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdpt.device.dao.IDeviceExtendDao;
import com.hdpt.device.dao.IDeviceTypeDao;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import com.hdpt.device.services.IDeviceExtendService;
import com.hdpt.device.services.IDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceTypeServiceImpl implements IDeviceTypeService {

    @Autowired
    IDeviceTypeDao deviceTypeDao;


    @Override
    public List<DeviceTypeDTO> findByNameAndMid(String name,Integer mid) {
        QueryWrapper<DeviceTypeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("GID","TYPECODE","DEVICETYPENAME","MID","ENABLEDSTATUS")
                    //.eq("MID",mid)
                    .like("DEVICETYPENAME",name);
        List<DeviceTypeDTO> list=deviceTypeDao.selectList(queryWrapper);
        //System.out.println("test mybaits"+list);
        return  list;
    }
    @Override
    public List<DeviceTypeDTO> findByName(String name) {
        QueryWrapper<DeviceTypeDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("GID","TYPECODE","DEVICETYPENAME","MID","ENABLEDSTATUS")
                .like("DEVICETYPENAME",name);
        List<DeviceTypeDTO> list=deviceTypeDao.selectList(queryWrapper);
        //System.out.println("test mybaits"+list);
        return  list;
    }
}
