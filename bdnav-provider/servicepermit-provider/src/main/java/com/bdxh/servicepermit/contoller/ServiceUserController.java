package com.bdxh.servicepermit.contoller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.service.ServiceUserService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Description: 控制器
 * @Date 2019-04-26 10:26:58
 */
@RestController
@RequestMapping("/serviceUser")
@Slf4j
@Validated
@Api(value = "用户服务认证控制器", tags = "用户服务认证交互API")
public class ServiceUserController {

    @Autowired
    private ServiceUserService serviceUserService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * 添加用户服务许可
     *
     * @param addServiceUsedDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("创建用户服务许可")
    @RequestMapping(value = "/createService", method = RequestMethod.POST)
    public Object createService(@Valid @RequestBody AddServiceUserDto addServiceUsedDto, BindingResult bindingResult) {
        try {
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.setId(snowflakeIdWorker.nextId());
            BeanUtils.copyProperties(addServiceUsedDto, serviceUser);
            Boolean flag = serviceUserService.save(serviceUser) > 0;
            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有未过期账号信息", response = Boolean.class)
    @RequestMapping(value = "/findServicePermitAll", method = RequestMethod.GET)
    public Object findServicePermitAll() {
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(null, null, null, null, 1);
        return WrapMapper.ok(serviceUsers);
    }


    /**
     * @Description: 鉴定是否有试用资格（如果试用结束引导购买，如若没试用则引导试用）
     * @Author: Kang
     * @Date: 2019/6/13 15:22
     */
    @ApiOperation(value = "鉴定试用资格", response = Boolean.class)
    @RequestMapping(value = "/findServicePermitByCondition", method = RequestMethod.GET)
    public Object findServicePermitByCondition(@RequestParam("schoolCode") String schoolCode, @RequestParam("studentCardNumber") String studentCardNumber, @RequestParam("familyCardNumber") String familyCardNumber) {
        //家长购买权限的集合信息(试用的集合信息)
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(schoolCode, studentCardNumber, familyCardNumber, 1, null);
        if (CollectionUtils.isNotEmpty(serviceUsers)) {
            ServiceUser serviceUser = serviceUsers.get(0);
            if (serviceUser.getStatus().equals(1) && serviceUser.getEndTime().after(new Date())) {
                //正在试用中
                return WrapMapper.ok();
            }
            List<ServiceUser> serviceUserTos = serviceUserService.findServicePermitByCondition(schoolCode, studentCardNumber, familyCardNumber, 2, 1);
            if (CollectionUtils.isNotEmpty(serviceUserTos)) {
                ServiceUser serviceUserTo = serviceUserTos.get(0);
                if (serviceUserTo.getEndTime().after(new Date())) {
                    //正式使用，并且还在有效期
                    return WrapMapper.ok();
                }
            }
            //已经试用，并且没有开通服务，通知前端引导购买
            return WrapMapper.notNoTrial("已经试用，并且没有开通服务");
        }
        //有资格领取试用资格。。。
        return WrapMapper.yesNotNoTrial("恭喜您，有试用资格");
    }

    /**
     * 领取试用服务许可资格
     *
     * @return
     */
    @ApiOperation(value = "领取试用服务许可资格", response = Boolean.class)
    @RequestMapping(value = "/createOnTrialService", method = RequestMethod.POST)
    public Object createOnTrialService(@Validated @RequestBody AddNoTrialServiceUserDto addNoTrialServiceUserDto) {
        //家长购买权限的集合信息(试用的集合信息)
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(addNoTrialServiceUserDto.getSchoolCode(), addNoTrialServiceUserDto.getStudentNumber(), addNoTrialServiceUserDto.getCardNumber(), 1, null);
        if (CollectionUtils.isNotEmpty(serviceUsers)) {
            //该孩子没有试用资格
            return WrapMapper.notNoTrial("该孩子没有试用资格");
        }
        serviceUserService.createOnTrialService(addNoTrialServiceUserDto);
        return WrapMapper.ok();
    }

    /**
     * @Description: 修改服务许可的信息（服务状态，剩余天数）[swagger忽略不展示，此方法只在定时任务出现]
     * @Author: Kang
     * @Date: 2019/6/13 18:42
     */
    @ApiIgnore
    @RequestMapping(value = "/updateServiceUser", method = RequestMethod.POST)
    public Object updateServiceUser(@Validated @RequestBody ModifyServiceUserDto modifyServiceUserDto) {
        ServiceUser serviceUser = new ServiceUser();
        BeanUtils.copyProperties(modifyServiceUserDto, serviceUser);
        serviceUser.setUpdateDate(new Date());
        serviceUserService.update(serviceUser);
        return WrapMapper.ok();
    }
}