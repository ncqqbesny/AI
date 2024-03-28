package com.hdpt.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdpt.device.dao.IDeviceExtendDao;
import com.hdpt.device.domain.Device.DeviceExtendDTO;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import com.hdpt.device.domain.Device.ExtendDescDTO;
import com.hdpt.device.services.IDeviceExtendService;
import com.hdpt.device.services.IDeviceTypeService;
import com.hdpt.device.services.IExtendDescService;
import com.hdpt.device.utils.CommonPage;
import com.hdpt.device.utils.StringUtil;
import org.apache.catalina.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class DeviceExtendServiceImpl implements IDeviceExtendService {

    @Autowired
    IDeviceExtendDao deviceExtendDao;
    @Autowired
    IDeviceTypeService deviceTypeService;
    @Autowired
    IExtendDescService extendDescService;

    @Override
    public List<Map> mySelect(String sql) {

        return null;
    }

    @Override
    public int selectColumn(String gid) {
        int count = deviceExtendDao.selectColumn(gid);
        System.out.println("count====" + count);
        return 0;
    }

    @Override
    public String insertUpdate(Map<String, String> dataMap, String typeName, Integer mId) throws InvocationTargetException, IllegalAccessException {
        //找不到对应项目类型，上级
        List<DeviceTypeDTO> deviceTypeDTOList = deviceTypeService.findByNameAndMid(typeName,mId);
        if (CollectionUtil.isEmpty(deviceTypeDTOList)) {
            deviceTypeDTOList = deviceTypeService.findByName(typeName);
        }
        if (CollectionUtil.isEmpty(deviceTypeDTOList)) {
            return "没有相应的类型====" + typeName + "的编号";
        }
        //找不到对应项目模板，只找编号上级
        List<ExtendDescDTO> extendDescDTOList = extendDescService.findByTypeCode(deviceTypeDTOList.get(0).getTypeCode(), null);
        if (CollectionUtil.isEmpty(extendDescDTOList)) {
          extendDescDTOList = extendDescService.findByTypeCode(deviceTypeDTOList.get(0).getTypeCode(), null);
        }
        if (CollectionUtil.isEmpty(extendDescDTOList)) {
            return "没有相应的模板描述====" + typeName + "的编号";
        }
        DeviceExtendDTO deviceExtendDTO = new DeviceExtendDTO();
        deviceExtendDTO.setMid(mId);
        //deviceExtendDTO.setGid(UUID.randomUUID().toString().replace("-",""));
        //deviceExtendDTO.setCreateTime(new Date());
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            System.out.println("key=" + entry.getKey() + "  value=" + entry.getValue());
            if (entry.getKey().equalsIgnoreCase("deviceGid")) {
                deviceExtendDTO.setDeviceGid(entry.getValue());
            }
            for (ExtendDescDTO extendDescDTO : extendDescDTOList) {
                if (entry.getKey().equalsIgnoreCase(extendDescDTO.getFieldCode())) {
                    //SET设置extendDescDTO.getExtFieldCode()属性（变量）为 entry.getValue()（变量）
                    BeanUtils.setProperty(deviceExtendDTO, extendDescDTO.getExtFieldCode(), entry.getValue());
                }
                deviceExtendDTO.setDeviceTypeCode(extendDescDTO.getDeviceTypeCode());
                deviceExtendDTO.setCatalogCode(extendDescDTO.getCatalogCode());
                deviceExtendDTO.setDescCode(extendDescDTO.getDescCode());
            }
        }
        int count = 0;
        List<DeviceExtendDTO> list=findByDeviceGid(deviceExtendDTO.getDeviceGid(), deviceExtendDTO.getMid());
        if (CollectionUtil.isEmpty(list)) {
            count = deviceExtendDao.insert(deviceExtendDTO);
        } else {
            QueryWrapper<DeviceExtendDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("GID", "DEVICEGID", "DEVICETYPECODE", "MID", "CATALOGCODE", "DESCCODE")
                    //.eq("MID", list.get(0).getMid())
                    .eq("DEVICEGID", list.get(0).getDeviceGid());
            //deviceExtendDTO.setMid(list.get(0).getMid());
            count = deviceExtendDao.update(deviceExtendDTO, queryWrapper);
        }
        if (count == 0) {
            return "没有添加" + typeName + "成功";
        }
        return "";
    }

    @Override
    public List<DeviceExtendDTO> findByDeviceGid(String deviceGid, Integer mid) {
        QueryWrapper<DeviceExtendDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("GID", "DEVICEGID", "DEVICETYPECODE", "MID", "CATALOGCODE", "DESCCODE")
                //.eq("MID", mid)
                .eq("DEVICEGID", deviceGid);
        List<DeviceExtendDTO> list = deviceExtendDao.selectList(queryWrapper);
        //System.out.println("test mybaits"+list);
        return list;
    }

    @Override
    public CommonPage<?> getPageList(Integer pageSize, Integer pageNum, Integer sort, Map<String, String> paramMap) {
        //分页参数
        Page<DeviceExtendDTO> page = new Page<>(pageNum, pageSize);
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<DeviceExtendDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeviceExtendDTO::getDeviceTypeCode, "0101");
        //排序 0，降序，1，升序
        if (sort.equals("1")) {
            queryWrapper.orderByAsc(DeviceExtendDTO::getCreateTime);
        } else {
            queryWrapper.orderByDesc(DeviceExtendDTO::getCreateTime);
        }
        deviceExtendDao.selectPage(page, queryWrapper);
        page.getRecords().forEach(System.out::println);
        Long total = page.getTotal();
        List<DeviceExtendDTO> list = page.getRecords();
        CommonPage<?> result = CommonPage.restPage(list, pageNum, pageSize, total);
        return result;
    }

    @Override
    public Map<String, Object> findByDeviceGidAndMid(String typeCode, String deviceGid, Integer mid) throws IllegalAccessException {
        List<ExtendDescDTO> extendDescDTOList = extendDescService.findByTypeCode(typeCode, mid);
        QueryWrapper<DeviceExtendDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MID", mid)
                .eq("DEVICEGID", deviceGid);
        List<DeviceExtendDTO> list = deviceExtendDao.selectList(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        for (DeviceExtendDTO deviceExtendDTO : list) {
            Field[] fields = deviceExtendDTO.getClass().getDeclaredFields();
            for (int i = 0, len = fields.length; i < len; i++) {
                //这个是，有的字段是用private修饰的 将他设置为可读
                fields[i].setAccessible(true);
                //输出字段名
                //System.err.println(fields[i].getName()+":"+i);
                String key = fields[i].getName();
                //获取值
                //System.err.println(fields[i].get(deviceExtendDTO));
                //System.err.println(fields[i].getName()+":"+fields[i].get(deviceExtendDTO));
                Object value = fields[i].get(deviceExtendDTO);

                if (key.toUpperCase().equals("GID")) {
                    map.put("extendGid", value);
                    continue;
                }
                if (!key.toUpperCase().contains("EXT") && !key.toUpperCase().equals("GID")) {
                    map.put(key, value);
                    continue;
                }
                if (StringUtil.isEmpty(value)) {
                    value = "";
                }
                for (ExtendDescDTO extendDescDTO : extendDescDTOList) {
                    if (extendDescDTO.getExtFieldCode().equalsIgnoreCase(key)) {
                            //暂时不返回图片
                        //if (extendDescDTO.getFieldCode().equals("img")) {
                            //continue;
                        //}
                        //没有设备sn就不添加，取t_device表中的数据
                        if(extendDescDTO.getFieldCode().equalsIgnoreCase("devicesn") && StringUtil.isEmpty(value)){
                            continue;
                        }
                        map.put(extendDescDTO.getFieldCode(), value);
                    }
                }
            }

        }
        return map;
    }
}
