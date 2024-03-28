package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.sysParam.SysParamQuery;
import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ISysParamDao {

    List<SysPramPo> getSysParamByType(String paramType);
    List<SysPramPo> getSysParamByTypeAndCode(@Param("paramType")  String paramType, @Param("paramCode") String paramCode);
    List<SysPramPo> getSysParamByTypeAndName(@Param("paramType")  String paramType, @Param("paramCode") String paramCode, @Param("paramName") String paramName);
    List<SysPramPo> getSysParamByName(String paramName);
    List<SysPramPo> getSysParamByRemark(String remark);
    List<SysPramPo> findListByWhere(SysParamQuery sysParamQuery);
    int insertOrUpdate(SysPramPo record);
    int updateByExampleSelective(@Param("record") SysPramPo record, @Param("example") SysParamQuery example);

}
