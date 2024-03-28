package com.hdpt.device.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdpt.device.domain.Device.DeviceTypeDTO;
import com.hdpt.device.domain.Device.ExtendDescDTO;
import com.hdpt.device.domain.Device.ExtendDescQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IExtendDescDao  {

    ExtendDescDTO selectById(Integer id);

    List<ExtendDescDTO> findList(ExtendDescQuery example);

    List<ExtendDescDTO> findByMid(Integer mId);

    List<ExtendDescDTO> findProvinceCount();

    List<ExtendDescDTO> findAreaCount();

    List<ExtendDescDTO> findStatusCount();
    List<ExtendDescDTO> findTypeCount();

    int addInfo(ExtendDescDTO netDeviceDTO);

    int updateByExampleSelective(@Param("record") ExtendDescDTO record, @Param("example") ExtendDescQuery example);
    int updateStatusByInterval();
    int deleteByExample(ExtendDescQuery example);

    List<ExtendDescDTO> getPageByExample(ExtendDescQuery example);

    int insertOrUpdate(ExtendDescDTO netDeviceDTO);

    boolean saveBatchByNative(List<ExtendDescDTO> list);

}
