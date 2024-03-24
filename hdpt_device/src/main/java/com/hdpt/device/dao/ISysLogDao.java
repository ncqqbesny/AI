package com.hdpt.device.dao;

import com.hdpt.device.domain.SysLog.SysLogPo;
import com.hdpt.device.domain.SysLog.SysLogQuery;
import com.hdpt.device.domain.loginVo.User;
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

    /**
     * 按参数log exit day 清除日志
     * @return
     */
    int deleteByExitDay();
    List<?> getPageSysLog(SysLogQuery sysLogQuery);

}
