package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import com.soft.mapp.basecenter.domain.sysParam.SysPramVo;

import java.util.List;
import java.util.Map;

public interface ISysParamService {
    List<SysPramVo> getSysParamByType(String paramType);
    List<SysPramVo> getSysParamByTypeAndCode(String paramType, String paramCode);
    List<SysPramPo> getParamVaule(String paramType, String paramCode, String paramName);
    int insertOrUpdate(SysPramVo record);
    int updateByExampleSelective(SysPramVo record);
    Map<String, String> getConfigMapByType(String paramType);
}
