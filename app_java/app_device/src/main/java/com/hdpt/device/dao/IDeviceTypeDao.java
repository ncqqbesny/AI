package com.hdpt.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IDeviceTypeDao extends BaseMapper<DeviceTypeDTO> {


}
