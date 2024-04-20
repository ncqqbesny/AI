package com.app.device.dao;

import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IBitCtrlDao {

    BitCtrlDTO selectByCtrlId(Integer ctrlId);

    List<BitCtrlDTO> findListByWhere(BitCtrlQuery example);

    List<BitCtrlDTO> findByCtrlNo(String ctrlNo);

    int addInfo(BitCtrlDTO record);

    int insertOrUpdate(BitCtrlDTO record);

    int insertOrUpdateBatch(List<BitCtrlDTO> item);

    int updateByExampleSelective(@Param("record") BitCtrlDTO record, @Param("example") BitCtrlQuery example);

    int updateBatch(List<BitCtrlDTO> list);

    int deleteByExample(BitCtrlQuery example);

    List<?> getPageByExample(BitCtrlQuery example);

}
