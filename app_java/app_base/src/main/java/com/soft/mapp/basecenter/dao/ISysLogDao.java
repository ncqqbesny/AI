package com.soft.mapp.basecenter.dao;

import com.soft.mapp.basecenter.domain.SysLog.SysLogPo;
import com.soft.mapp.basecenter.domain.SysLog.SysLogQuery;
import com.soft.mapp.basecenter.domain.loginVo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ISysLogDao {

    SysLogPo selectBySysLogId(Integer logId);

    List<?> findList(SysLogQuery sysLogQuery);

    User findByUsername(String userName);

    int addInfo(SysLogPo SysLogPo);

    int updateByExampleSelective(@Param("record") SysLogPo record, @Param("example") SysLogQuery example);

    int deleteByExample(SysLogQuery example);

    List<?> getPageSysLog(SysLogQuery sysLogQuery);

}
