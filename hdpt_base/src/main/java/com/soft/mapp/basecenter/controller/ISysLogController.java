package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.SysLog.SysLogPo;
import com.soft.mapp.basecenter.domain.SysLog.SysLogVo;
import com.soft.mapp.basecenter.utils.ServerResponse;

import java.util.List;

public interface ISysLogController extends IRestService {
    //99020147	删除操作日志
    public ServerResponse<?> delLogInfo(List<String> ids);
    //99020147	添加操作日志
    public ServerResponse<?> addLogInfo(SysLogPo sysLogPo);

    //990202146	获取日志列表	获取标签、操作、报警日志	主柜《标签、操作、报警日志》
    public ServerResponse<?> getLogInfo(Integer pageSize, Integer pageNum, Integer sort, SysLogVo sysLogVo);
}
