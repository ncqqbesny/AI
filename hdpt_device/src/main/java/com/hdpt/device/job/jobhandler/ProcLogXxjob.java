package com.hdpt.device.job.jobhandler;


import com.hdpt.device.dao.ICabinetDao;
import com.hdpt.device.dao.INetDeviceDao;
import com.hdpt.device.dao.ISysLogDao;
import com.hdpt.device.dao.IUserDao;
import com.hdpt.device.domain.Cabinet.CabinetDTO;
import com.hdpt.device.domain.Cabinet.CabinetQuery;
import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceQuery;
import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.domain.queryScreen.DeviceAreaVo;
import com.hdpt.device.domain.queryScreen.DeviceNameVo;
import com.hdpt.device.domain.queryScreen.DeviceProvinceVo;
import com.hdpt.device.domain.queryScreen.DeviceType;
import com.hdpt.device.type.DeviceTypeEnum;
import com.hdpt.device.utils.ConvertUtils;
import com.hdpt.device.utils.JedisUtil;
import com.hdpt.device.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
