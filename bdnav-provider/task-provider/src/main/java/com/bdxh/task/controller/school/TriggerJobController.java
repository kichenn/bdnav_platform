package com.bdxh.task.controller.school;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.task.controller.school.job.StrategyJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.*;

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


    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    @ApiOperation("启动学校策略定时任务")
    @RequestMapping(value = "/startStrategyJob", method = RequestMethod.GET)
    public Object startStrategyJob(@RequestParam(value = "schoolCode") String schoolCode, @RequestParam(value = "groupId") Long groupId) throws SchedulerException, ParseException {

        //查询策略触发条件
        QuerySchoolStrategy qss=new QuerySchoolStrategy();
        qss.setSchoolCode(schoolCode);
        qss.setPushState(new Byte("1"));
        List<SchoolStrategy> strategyList = schoolStrategyControllerClient.getStrategyList(qss).getResult();
        System.out.println(strategyList.toString());
        if (CollectionUtils.isNotEmpty(strategyList)) {
            //创建schedule实例
            StdSchedulerFactory factory = new StdSchedulerFactory();

            for (SchoolStrategy e : strategyList) {
                //获取周时间段
                String dayMark = e.getDayMark();
                String[] dayMarks = dayMark.split(",");
                if (dayMark.length() != 13) {
                    return WrapMapper.error("周时间段格式不正确，请检查");
                }
                //排除周时间段
                WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
                //param1 周几（星期一：1，星期二：2） param2（true排除，false不排除）
                weeklyCalendar.setDayExcluded(DayOfWeek.MONDAY.getValue(), dayMarks[0].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.TUESDAY.getValue(), dayMarks[1].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.WEDNESDAY.getValue(), dayMarks[2].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.THURSDAY.getValue(), dayMarks[3].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.FRIDAY.getValue(), dayMarks[4].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.SATURDAY.getValue(), dayMarks[5].equals("0") ? Boolean.FALSE : Boolean.TRUE);
                weeklyCalendar.setDayExcluded(DayOfWeek.SUNDAY.getValue(), dayMarks[6].equals("0") ? Boolean.FALSE : Boolean.TRUE);

                //获取排除时间段
                String exclusionDay = e.getExclusionDays();
                String[] exclusionDays = exclusionDay.split(",");
                HolidayCalendar holidayCalendar = new HolidayCalendar();
                for (String tempDay : exclusionDays) {
                    holidayCalendar.addExcludedDate(DateUtil.format(tempDay, "yyyy-MM-dd HH:mm:ss"));
                }
                //日时间段时间集合
                Set<String> timeMarkResult = new HashSet<>();

                //获取日时间段
                String timeMark = e.getTimeMark();
                //获取 2:00-3:00,3:00-4:00
                String[] timeMarks = timeMark.split(",");
                for (String tempTimeMark : timeMarks) {
                    String[] sections = tempTimeMark.split("-");
                    timeMarkResult.add(sections[0]);
                    timeMarkResult.add(sections[1]);
                }
                //入参
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("schoolCode", schoolCode);
                jsonObject.put("groupId", groupId);
                jsonObject.put("strategyList",strategyList);

                for (String time : timeMarkResult) {
                    //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
                    JobDetail jobDetail = JobBuilder.newJob(StrategyJob.class).withIdentity("startStrategyJob:" + e.getId() + ",time:" + time).build();
                    //      //执行表达式(#替换字段[分钟]，S小时)
                    String cronScheduleStr = "0 # S * * ?";
                    //获取 2:00 , 3:00
                    cronScheduleStr = cronScheduleStr.replace("#", time.substring(time.indexOf(":") + 1, time.length()));
                    cronScheduleStr = cronScheduleStr.replace("S", time.substring(0, time.indexOf(":")));

                    Scheduler scheduler = factory.getScheduler();
                    //添加排除周时间段筛选条件
                    scheduler.addCalendar("dayMark", weeklyCalendar, true, true);
                    //获取排除时间段
                    scheduler.addCalendar("exclusionDay", holidayCalendar, true, true);
                    //第二步：创建CronTrigger，指定组及名称,并设置Cron表达式
                    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("startStrategyTrigger:" + e.getId() + ",time:" + time)
                            .withSchedule(CronScheduleBuilder.cronSchedule(cronScheduleStr))
                            .usingJobData("data", jsonObject.toJSONString())
                            .build();
                    scheduler.scheduleJob(jobDetail, cronTrigger);
                    scheduler.start();
                }
            }
        } else {
            return WrapMapper.error("策略为空");
        }
        return WrapMapper.ok();

    }


    public static void main(String[] args) {
        String s = "01:10-03:20,03:00-04:00,04:00-05:00";

        Set<String> result = new HashSet<>();
        String strs[] = s.split(",");
        int i = 0;
        for (String str : strs) {
            String[] sections = str.split("-");
            result.add(sections[0]);
            result.add(sections[1]);
        }

        System.out.println(s.substring(s.indexOf(":") + 1, s.length()));
        System.out.println(s.substring(0, s.indexOf(":")));
    }
}
