package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.domain.SysLog.SysLogPo;
import com.soft.mapp.basecenter.domain.SysLog.SysLogVo;
import com.soft.mapp.basecenter.handler.CommonPage;

import java.util.List;

public interface ISysLogService {
    //	删除操作日志
    public String delLogInfo(List<String> ids);
    //	添加操作日志
    public String addLogInfo(SysLogPo sysLogPo);
    //	获取日志列表	获取标签、操作、报警日志	主柜《标签、操作、报警日志》
    public CommonPage<SysLogPo> getLogInfo(Integer pageSize, Integer pageNum, Integer sort, SysLogVo sysLogVo);

}
