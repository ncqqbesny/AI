package com.soft.mapp.basecenter.job;

import cn.hutool.core.collection.CollectionUtil;
import com.soft.mapp.basecenter.domain.sysParam.SysPramPo;
import com.soft.mapp.basecenter.domain.sysParam.SysPramVo;
import com.soft.mapp.basecenter.handler.ApplicationContextProvider;
import com.soft.mapp.basecenter.handler.QuartzSchedulerService;
import com.soft.mapp.basecenter.impl.services.SysParamServiceImpl;
import com.soft.mapp.basecenter.impl.services.WechatServiceImpl;
import com.soft.mapp.basecenter.utils.ConvertUtils;
import com.soft.mapp.basecenter.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公众号用户同步处理
 */
public class WxPublicSyncUserJob implements Job {
    private final static Logger log = LoggerFactory.getLogger(WxPublicSyncUserJob.class);
    public void loadAtStartup() {
        log.info("--- Registerting WxPublicSyncUserJob Cron Job ---");
        QuartzSchedulerService schedulerService=ApplicationContextProvider.getBean(QuartzSchedulerService.class);
        SysParamServiceImpl sysParamService = ApplicationContextProvider.getBean(SysParamServiceImpl.class);
        int flag = 1;
        //秒,分，时，日，月，星期几，年
        String cronExpression="0 0 10 10 10 ? 2022";
        List<SysPramPo> paramPos = sysParamService.getParamVaule("wx_public_config", "sync_user", "flag");
        if (CollectionUtil.isNotEmpty(paramPos)) {
            flag=3;
        }
        if (flag >= 1) {
            Map<String,Integer> map= DateUtils.getDetailDate(new Date());
            cronExpression=ConvertUtils.toString(map.get("second"))
                    +" "+ConvertUtils.toString(map.get("minute")+flag)
                    +" "+ConvertUtils.toString(map.get("hour"))
                    +" "+ConvertUtils.toString(map.get("day"))
                    +" "+ConvertUtils.toString(map.get("month"))
                    +" ? " //星期几
                    +" "+ConvertUtils.toString(map.get("year"));

        }
        schedulerService.registerCronJob(WxPublicSyncUserJob.class, cronExpression);
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            WechatServiceImpl wechatService = ApplicationContextProvider.getBean(WechatServiceImpl.class);
            SysParamServiceImpl sysParamService = ApplicationContextProvider.getBean(SysParamServiceImpl.class);
            int flag = 0; //豪秒
            List<SysPramPo> paramPos = sysParamService.getParamVaule("wx_public_config", "sync_user", "flag");
            if (CollectionUtil.isNotEmpty(paramPos)) {
                flag = ConvertUtils.toInt(paramPos.get(0).getParamVal());
            }
            if (flag >= 1) {
                log.info("WxPublicCodeTimer start------开始 同步公众号用户信息");
                String msg1 = wechatService.syncWxPublicUser();
                log.info("WxPublicCodeTimer end------结束 同步公从号用户信息 有错误信息是：" + msg1);
                SysPramVo sysPramVo = new SysPramVo();
                BeanUtils.copyProperties(paramPos.get(0), sysPramVo);
                sysPramVo.setParamVal("0");
                int count = sysParamService.updateByExampleSelective(sysPramVo);
                log.info("WxPublicCodeTimer update flag end------更新结束标志 ：" + count);
            }
            //log.info("WxPublicCodeTimer start------开始 发送模板消息");
            //String msg = wechatService.timerWxPublicSendSubscribeInfo();
            //log.info("WxPublicCodeTimer end--------结束 发模板消息，有报错信息是：" + msg);
        } catch (Exception e) {
            log.info("微信公众号定时处理异常 WxPublicCodeTimer error ：----" + e);
        }
    }
}
