package com.app.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.device.domain.Device.DeviceTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface IDeviceTypeDao extends BaseMapper<DeviceTypeDTO> {


}
