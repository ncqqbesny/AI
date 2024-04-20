package com.app.device.dao;

import com.app.device.domain.hdUp.NetDeviceDTO;
import com.app.device.domain.hdUp.NetDeviceQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface INetDeviceDao {

    NetDeviceDTO selectById(Integer id);

    List<NetDeviceDTO> findList(NetDeviceQuery example);

    List<NetDeviceDTO> findByMid(Integer mId);

    List<NetDeviceDTO> findProvinceCount();

    List<NetDeviceDTO> findAreaCount();

    List<NetDeviceDTO> findStatusCount();
    List<NetDeviceDTO> findTypeCount();

    int addInfo(NetDeviceDTO netDeviceDTO);

    int updateByExampleSelective(@Param("record") NetDeviceDTO record, @Param("example") NetDeviceQuery example);
    int updateStatusByInterval();
    int deleteByExample(NetDeviceQuery example);

    List<NetDeviceDTO> getPageByExample(NetDeviceQuery example);

    int insertOrUpdate(NetDeviceDTO netDeviceDTO);
    //批量添加
    boolean saveBatchByNative(List<NetDeviceDTO> list);
    //批量更新
    int  updateBatchByNative(List<NetDeviceDTO> list);

    /**
     * 自定义执行SQL
     * @param sql
     * @return
     */
    List<Map> mySelect(String sql);

}
