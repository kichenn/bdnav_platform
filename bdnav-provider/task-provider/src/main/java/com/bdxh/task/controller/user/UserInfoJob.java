package com.bdxh.task.controller.user;

import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.feign.StudentControllerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-29 17:30
 **/
@Component
@Slf4j
@Configurable
@EnableScheduling
public class UserInfoJob {
    @Autowired
    private StudentControllerClient studentControllerClient;

    @Scheduled(cron = "0 0 0 0/1 * *")
    public void test(){
        StudentQueryDto studentQueryDto=new StudentQueryDto();
       log.info("===========");
        studentControllerClient.queryStudentListPage(studentQueryDto);
    }
}