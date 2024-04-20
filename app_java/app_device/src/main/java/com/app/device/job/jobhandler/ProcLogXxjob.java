package com.app.device.job.jobhandler;


import com.app.device.dao.ISysLogDao;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcLogXxjob {
    private static Logger logger = LoggerFactory.getLogger(ProcLogXxjob.class);
    @Autowired
    ISysLogDao  sysLogDao;


    /**
     * 1、
     */
    @XxlJob("clearLogJobHandler")
    public void clearLogJobHandler() throws Exception {
        //1
        XxlJobHelper.log("清除配置（exit_log)日志开始....");
        int count=sysLogDao.deleteByExitDay();
        XxlJobHelper.log("清除配置（exit_log)日志结束数量...."+count);
    }
}
