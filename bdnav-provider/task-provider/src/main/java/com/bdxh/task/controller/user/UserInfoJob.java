package com.bdxh.task.controller.user;

import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.feign.StudentControllerClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-29 17:30
 **/
@Component
public class UserInfoJob {
    @Autowired
    private StudentControllerClient studentControllerClient;

    @Scheduled(cron = "0/10 * * * * *")
    public void test(){
        StudentQueryDto studentQueryDto=new StudentQueryDto();
        studentControllerClient.queryStudentListPage(studentQueryDto);
    }
}