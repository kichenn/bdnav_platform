package com.bdxh.task.controller.school;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.task.controller.school.job.StrategyJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: Quartz学校定时任务触发controller
 * @Author: Kang
 * @Date: 2019/5/16 10:24
 */
@RestController
@RequestMapping("/StrategyJob")
@Slf4j
@Validated
@Api(value = "Quartz学校定时任务触发控制器", tags = "Quartz学校定时任务触发控制器")
public class TriggerJobController {


    @ApiOperation("启动学校策略定时任务")
    @RequestMapping(value = "/startStrategyJob", method = RequestMethod.GET)
    public Object startStrategyJob(@RequestParam(value = "schoolCode") String schoolCode,@RequestParam(value = "groupId" ) Long groupId) throws SchedulerException {

        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(StrategyJob.class).withIdentity("myJob").build();
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").usingJobData("schoolCode", schoolCode).usingJobData("groupId",groupId)
                .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(20).repeatForever()).build();
        //每日的9点40触发任务
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 40 9 * * ? ")).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        return WrapMapper.ok();

    }
}
