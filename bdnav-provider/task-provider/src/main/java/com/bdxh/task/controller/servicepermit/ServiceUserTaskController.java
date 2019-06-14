package com.bdxh.task.controller.servicepermit;

import com.bdxh.common.utils.DateUtil;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.task.configration.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
 * @Description: 家长商品服务权限定时任务（swagger忽略不展示）
 * @Author: Kang
 * @Date: 2019/6/13 18:14
 */
@ApiIgnore
@Component
@Slf4j
public class ServiceUserTaskController {

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @Autowired
    private RedisUtil redisUtil;

    //微校token前缀
    private static final String WEIXIAO_TOKEN_PREFIX = "weixiao_token:";

    /**
     * 定时任务：商品的服务剩余天数更新，到期后修改信息 （每天零点执行）@Scheduled(cron = "0 0 0 * * ? *")
     */
    @Scheduled(cron = "0 0 0 * * ? *")
    public void servicePermitTask() {
        log.info("----------log---更新商品服务剩余天数-----------");
        //查询所有未过期的账号信息
        List<ServiceUser> serviceUserList = serviceUserControllerClient.findServicePermitAll().getResult();
        //获取当前时间
        Date thisTime = DateUtil.format(DateUtil.now2(), "yyyy-MM-dd");
        for (ServiceUser serviceUser : serviceUserList) {
            ModifyServiceUserDto modifyServiceUserDto = new ModifyServiceUserDto();
            modifyServiceUserDto.setId(serviceUser.getId());
            modifyServiceUserDto.setDays(serviceUser.getDays() - 1);
            if (serviceUser.getEndTime().before(thisTime) || serviceUser.getDays().equals(0)) {
                //已过期
                modifyServiceUserDto.setDays(0);
                modifyServiceUserDto.setStatus(2);
                //已过期查找redis中的token并注销下线
                redisUtil.delete(ServiceUserTaskController.WEIXIAO_TOKEN_PREFIX + serviceUser.getFamilyId());
            }
            serviceUserControllerClient.updateServiceUser(modifyServiceUserDto);
        }
    }
}
