package com.app.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.device.domain.Device.DeviceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IDeviceDao extends BaseMapper<DeviceDTO> {

    /**
     * 自定义执行SQL
     * @param sql
     * @return
     */
    List<Map> mySelect(String sql);

    @Select("select count(1) from t_device_extend where DEVICEGID = ${gid} ")
    public int selectColumn(@Param("gid") String gid);
    //更新MHQ在线，离线状态
    int updateMhqStatusByInterval();
    //更新环测网关在线，离线状态
    int updateHcwgStatusByInterval();
    //更新在线时长
    int updateRunTimeByCurrentTime();
}
