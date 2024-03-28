package com.hdpt.device.job.jobhandler;


import com.hdpt.device.services.*;
import com.hdpt.device.utils.FileUtils;
import com.hdpt.device.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EnvironmentTestXxjob {
    private static Logger logger = LoggerFactory.getLogger(EnvironmentTestXxjob.class);
    @Autowired
    IEnvironmentTestService environmentTestService;




    /**
     * 1、
     */
    @XxlJob("environmentTestJobHandler")
    public void firexExtendJobHandler() throws Exception {
        XxlJobHelper.log("环测试网关定时处理程序开始....");
        String msg=environmentTestService.pushImg2InkScreen();
        if(StringUtil.isNotEmpty(msg)){
            XxlJobHelper.log("环测试网关定时发布墨水屏数据错误:"+msg);
        }
        XxlJobHelper.log("环测试网关定时处理程序结束....");
    }


}
