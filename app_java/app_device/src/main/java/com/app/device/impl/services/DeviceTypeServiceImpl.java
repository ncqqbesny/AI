package com.app.device.impl.services;

import com.app.device.dao.IDeviceTypeDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.app.device.domain.Device.DeviceTypeDTO;
import com.app.device.services.IDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
