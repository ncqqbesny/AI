package com.hdpt.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdpt.device.domain.Device.DeviceExtendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IDeviceExtendDao extends BaseMapper<DeviceExtendDTO> {

    /**
     * 自定义执行SQL
     * @param sql
     * @return
     */
    List<Map> mySelect(String sql);

    @Select("select count(1) from t_device_extend where DEVICEGID = ${gid} ")
    public int selectColumn(@Param("gid") String gid);

}
