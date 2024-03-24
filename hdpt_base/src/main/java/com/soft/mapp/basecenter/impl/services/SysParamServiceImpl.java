package com.soft.mapp.basecenter.impl.services;

import com.soft.mapp.basecenter.dao.ISysParamDao;
import com.soft.mapp.basecenter.domain.sysParam.SysParamQuery;
import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import com.soft.mapp.basecenter.domain.sysParam.SysPramVo;
import com.soft.mapp.basecenter.services.ISysParamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysParamServiceImpl implements ISysParamService {
    @Autowired
    ISysParamDao sysParamDao;

    @Override
    public List<SysPramVo> getSysParamByType(String paramType) {
        List<SysPramPo> lists=sysParamDao.getSysParamByType(paramType);
        List<SysPramVo> sysPramVos=new ArrayList<>();
        for(SysPramPo paramPo:lists){
            SysPramVo paramVo=new SysPramVo();
            BeanUtils.copyProperties(paramPo,paramVo);
            //后续可作转换逻辑
            sysPramVos.add(paramVo);
        }
        return sysPramVos;
    }
    @Override
    public List<SysPramVo> getSysParamByTypeAndCode(String paramType, String paramCode) {
        List<SysPramPo> lists = sysParamDao.getSysParamByTypeAndCode(StringUtils.trim(paramType), StringUtils.trim(paramCode));
        List<SysPramVo> sysParamVos = new ArrayList<>();
        for (SysPramPo paramPo : lists) {
            SysPramVo paramVo = new SysPramVo();
            BeanUtils.copyProperties(paramPo, paramVo);
            //后续可作转换逻辑
            sysParamVos.add(paramVo);
        }
        return sysParamVos;
    }

    @Override
    public List<SysPramPo> getParamVaule(String paramType, String paramCode, String paramName) {
        SysParamQuery SysParamQuery = new SysParamQuery();
        SysParamQuery.Criteria criteria = SysParamQuery.createCriteria();
        if (StringUtils.isNotEmpty(paramType)) {
            criteria.andParamTypeEqualTo(paramType);
        }
        if (StringUtils.isNotEmpty(paramCode)) {
            criteria.andParamCodeEqualTo(paramCode);
        }
        if (StringUtils.isNotEmpty(paramName)) {
            criteria.andParamNameLike(paramName);
        }
        return sysParamDao.findListByWhere(SysParamQuery);
    }

    @Override
    public int insertOrUpdate(SysPramVo record) {
        SysPramPo sysPramPo=new SysPramPo();
        BeanUtils.copyProperties(record,sysPramPo);
        return sysParamDao.insertOrUpdate(sysPramPo);
    }



    @Override
    public int updateByExampleSelective(SysPramVo sysPramVo) {
        SysPramPo record=new SysPramPo();
        BeanUtils.copyProperties(sysPramVo,record);
        SysParamQuery SysParamQuery=new SysParamQuery();
        SysParamQuery.Criteria criteria=SysParamQuery.createCriteria();
        if(StringUtils.isNotEmpty(record.getParamType())){
            criteria.andParamTypeEqualTo(record.getParamType());
        }
        if(StringUtils.isNotEmpty(record.getParamCode())){
            criteria.andParamCodeEqualTo(record.getParamCode());
        }
        int count=sysParamDao.updateByExampleSelective(record,SysParamQuery);
        return count;
    }
    @Override
    public Map<String, String> getConfigMapByType(String paramType) {
        Map<String, String> map = new HashMap<>();
        List<SysPramVo> sysParamVos = getSysParamByType(paramType);
        for (SysPramVo sysParamVo : sysParamVos) {
            map.put(sysParamVo.getParamCode(), sysParamVo.getParamVal());
        }
        return map;
    }
}
