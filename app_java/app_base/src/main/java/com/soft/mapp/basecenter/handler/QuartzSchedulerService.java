package com.soft.mapp.basecenter.handler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class QuartzSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzSchedulerService.class);

    private Scheduler scheduler;

    @PostConstruct
    private void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (Exception e) {
            LOG.error("Unable to start quartz scheduler", e);
        }
    }

    @PreDestroy
    private void destroy() {
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            LOG.error("Unable to shutdown quartz scheduler", e);
        }
    }

    public void registerCronJob(Class<? extends Job> jobClass, String cronExpression) {
        try {
            String jobName = jobClass.getSimpleName();
            CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName).build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName).withSchedule(cronBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            LOG.info("Registered Cron Job:" + jobName + " " + jobDetail.getKey());
        } catch (Exception e) {
            LOG.error("Unable to register cron job", e);
        }
    }

    public void updateCronSchedule(Class<? extends Job> jobClass, String cronExpression) {
        try {
            String jobName = jobClass.getSimpleName();
            CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger newCronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName).withSchedule(cronBuilder).build();
            scheduler.rescheduleJob(TriggerKey.triggerKey(jobName), newCronTrigger);
            LOG.info("Updated Cron Job:" + jobName + " " + newCronTrigger.getJobKey());
            LOG.info("Jobs executed: " + scheduler.getMetaData().getNumberOfJobsExecuted());
        } catch (Exception e) {
            LOG.error("Unable to reschedule cron job", e);
        }
    }

}