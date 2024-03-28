package com.hdpt.device.job.jobhandler;


import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.hdpt.device.dao.ICabinetDao;
import com.hdpt.device.dao.IDeviceDao;
import com.hdpt.device.dao.INetDeviceDao;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcStatusXxjob {
    private static Logger logger = LoggerFactory.getLogger(ProcStatusXxjob.class);
    @Autowired
    INetDeviceDao netDeviceDao;
    @Autowired
    ICabinetDao  cabinetDao;
    @Autowired
    IDeviceDao deviceDao;

    /**
     * 1、
     */
    @XxlJob("resetStatusJobHandler")
    public void resetStatusJobHandler() throws Exception {

        //1
        XxlJobHelper.log("在线状态处理开始....");
        int netCount=netDeviceDao.updateStatusByInterval();
        XxlJobHelper.log("网络设备更新不在线状态数量---->"+netCount);
        int cabCount=cabinetDao.updateStatusByInterval();
        XxlJobHelper.log("器具柜更新不在线状态数量---->"+cabCount);
        int smartMhqCount=deviceDao.updateMhqStatusByInterval();
        XxlJobHelper.log("智能灭火器更新不在线状态数量---->"+smartMhqCount);
        int smartHcwgCount=deviceDao.updateHcwgStatusByInterval();
        XxlJobHelper.log("智能环测网关更新不在线状态数量---->"+smartHcwgCount);
        int runTimeCount=deviceDao.updateRunTimeByCurrentTime();
        XxlJobHelper.log("智能更新在线时长数量---->"+runTimeCount);
        XxlJobHelper.log("在线状态处理结束....");
    }
}
