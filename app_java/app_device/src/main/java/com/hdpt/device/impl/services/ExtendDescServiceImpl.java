package com.hdpt.device.impl.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdpt.device.dao.IDeviceTypeDao;
import com.hdpt.device.dao.IExtendDescDao;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import com.hdpt.device.domain.Device.ExtendDescDTO;
import com.hdpt.device.domain.Device.ExtendDescQuery;
import com.hdpt.device.services.IDeviceTypeService;
import com.hdpt.device.services.IExtendDescService;
import com.hdpt.device.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtendDescServiceImpl implements IExtendDescService {

    @Autowired
    IExtendDescDao extendDescDao;


    @Override
    public List<ExtendDescDTO> findByTypeCode(String typeCode, Integer mid) {
        /*QueryWrapper<ExtendDescDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("GID","DEVICEGID","DEVICETYPECODE","MID","CATALOGCODE","DESCCODE","LAST_TIME")
                .eq("MID",mid)
                .like("DEVICETYPECODE",typeCode);
        List<ExtendDescDTO> list=extendDescDao.selectList(queryWrapper);
        System.out.println("test mybaits"+list);
        return  list;*/
        ExtendDescQuery extendDescQuery=new ExtendDescQuery();
        ExtendDescQuery.Criteria criteria=extendDescQuery.createCriteria();
        if(StringUtil.isNotEmpty(typeCode)){
            criteria.andDeviceTypeCodeEqualTo(typeCode);
        }
        //暂时不管理到项目
        /*if(StringUtil.isNotEmpty(mid)){
            criteria.andMidEqualTo(mid);
        }*/
        return extendDescDao.findList(extendDescQuery);
    }
}
