package com.hdpt.device.dao;

import com.hdpt.device.domain.Cabinet.CabinetDTO;
import com.hdpt.device.domain.Cabinet.CabinetQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ICabinetDao {

    CabinetDTO selectBycabinetId(Integer cabinetId);

    List<CabinetDTO> findListByWhere(CabinetQuery example);
    List<CabinetDTO> findProvinceCount();
    List<CabinetDTO> findAreaCount();
    List<CabinetDTO> findStatusCount();

    CabinetDTO findByCabinetNo(String cabinetNo);

    int addCabinet(CabinetDTO cabinetDTO);

    int insertOrUpdate(CabinetDTO cabinetDTO);

    int updateByExampleSelective(@Param("record") CabinetDTO record, @Param("example") CabinetQuery example);

    int updateStatusByInterval();
    int deleteByExample(CabinetQuery example);

    List<?> getPageByExample(CabinetQuery example);
}
