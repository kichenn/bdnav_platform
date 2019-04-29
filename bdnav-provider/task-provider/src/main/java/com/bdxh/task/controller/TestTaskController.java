package com.bdxh.task.controller;

import com.bdxh.common.utils.DateUtil;
import com.bdxh.user.feign.StudentControllerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Description: 测试定时任务方法
 * @Author: Kang
 * @Date: 2019/4/29 15:07
 */
@Component
@Slf4j
public class TestTaskController {

    @Autowired
    private StudentControllerClient studentControllerClient;
    /**
     * 每五秒执行
     */
//    @Scheduled(cron = "0/5 * * * * *")
//    public void scheduled() {
//        log.info("---0/5-定时任务---{}", DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
//    }

    /**
     * 每一分钟执行
     */
//    @Scheduled(cron = "0/3 * * * * * ")
//    public void scheduled1() {
//        studentControllerClient.findStudentBySchoolClassId("", new Long("111"), new Long("111"));
//        log.info("---每1分钟执行-定时任务---{}", DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
//    }
}
