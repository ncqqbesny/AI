package com.soft.mapp.basecenter.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import com.soft.mapp.basecenter.impl.services.SysParamServiceImpl;
import com.soft.mapp.basecenter.job.WxPublicSyncUserJob;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineRunnerListenerImpl implements CommandLineRunner {
    @Value("${spring.profiles.active}")
    private String active;
    @Value("${spring.devjob.boot}")
    private String devboot;

    @Override
    public void run(String... args) throws Exception {
        if (active.indexOf("prd") != -1) {
            //execTimerTask();
        }
        if (active.indexOf("dev") != -1 && devboot.indexOf("debug") != -1) {
            //execTimerTask();
        }
    }

    /**
     * 给公从号定时发信息
     * @throws Exception
     */
    private void execTimerTask() throws Exception {

        // 创建一个jobDetail的实例，将该实例与s绑定
        // DI JOB
        JobDetail jobDetailTime = JobBuilder.newJob(WxPublicSyncUserJob.class).withIdentity("myJob").build();
        SysParamServiceImpl sysParamService = ApplicationContextProvider.getBean(SysParamServiceImpl.class);
        int refresh = 1000; //豪秒
        List<SysPramPo> paramPos = sysParamService.getParamVaule("wx_public_config", "timeInterval", "refresh");
        if (CollectionUtil.isNotEmpty(paramPos)) {
            refresh = ConvertUtils.toInt(paramPos.get(0).getParamVal());
        }
        SimpleTrigger triggerTime = TriggerBuilder.newTrigger().withIdentity("myTrigger")
              .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(refresh).repeatForever()).build();
        // 创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler schedulerTime = factory.getScheduler();
        schedulerTime.scheduleJob(jobDetailTime, triggerTime);
        schedulerTime.start();

        //初始化一个JOB
        //WxPublicSyncUserJob wxPublicSyncUserJob=new WxPublicSyncUserJob();
        //wxPublicSyncUserJob.loadAtStartup();
    }
}
