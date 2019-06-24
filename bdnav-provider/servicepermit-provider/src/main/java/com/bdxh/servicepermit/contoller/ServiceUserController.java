package com.bdxh.servicepermit.contoller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.enums.ServiceStatusEnum;
import com.bdxh.servicepermit.enums.ServiceTypeEnum;
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
import java.util.Map;

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
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(null, null, null, null, null, 1);
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
        //家长购买权限的集合信息（试用对于一个家长和一个孩子的所有商品，购买各对于一个家长和一个孩子的一个商品，俩者满足条件的都只存在一条数据）
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(schoolCode, studentCardNumber, familyCardNumber, null, Integer.valueOf(ServiceTypeEnum.ON_TRIAL.getKey()), null);
        if (CollectionUtils.isNotEmpty(serviceUsers)) {
            ServiceUser serviceUser = serviceUsers.get(0);
            if (serviceUser.getStatus().equals(1) && serviceUser.getEndTime().after(new Date())) {
                //正在试用中
                return WrapMapper.ok();
            }
            //家长购买权限的集合信息（试用对于一个家长和一个孩子的所有商品，购买各对于一个家长和一个孩子的一个商品，俩者满足条件的都只存在一条数据）
            List<ServiceUser> serviceUserTos = serviceUserService.findServicePermitByCondition(schoolCode, studentCardNumber, familyCardNumber, null, Integer.valueOf(ServiceTypeEnum.FORMAL.getKey()), Integer.valueOf(ServiceStatusEnum.NORMAL_USE.getValue()));
            if (CollectionUtils.isNotEmpty(serviceUserTos)) {
                //一个服务许可正式使用时，一个家长对于一个孩子只会存在一条数据，故此处get（0）
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
        //家长购买权限的集合信息（试用对于一个家长和一个孩子的所有商品，购买各对于一个家长和一个孩子的一个商品，俩者满足条件的都只存在一条数据）
        //商品类型为试用，试用代表的是试用所有的商品
        List<ServiceUser> serviceUsers = serviceUserService.findServicePermitByCondition(addNoTrialServiceUserDto.getSchoolCode(), addNoTrialServiceUserDto.getStudentNumber(), addNoTrialServiceUserDto.getCardNumber(), null,  Integer.valueOf(ServiceTypeEnum.ON_TRIAL.getKey()), null);
        if (CollectionUtils.isNotEmpty(serviceUsers)) {
            //该孩子没有试用资格
            return WrapMapper.notNoTrial("该孩子没有试用资格");
        }
        serviceUserService.createOnTrialService(addNoTrialServiceUserDto);
        return WrapMapper.ok();
    }

    /**
     * 购买服务许可资格 [swagger忽略不展示，此方法只在微信支付成功之后调用]
     *
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "购买服务许可资格", response = Boolean.class)
    @RequestMapping(value = "/createPayService", method = RequestMethod.POST)
    public Object createPayService(@Validated @RequestBody AddPayServiceUserDto addPayServiceUserDto) {
        serviceUserService.createPayService(addPayServiceUserDto);
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

    @ApiOperation(value = "根据条件查询订单", response = ServiceUser.class)
    @RequestMapping(value = "/queryServiceUser", method = RequestMethod.POST)
    public Object queryServiceUser(@Validated @RequestBody QueryServiceUserDto queryServiceUserDto){
        Map<String, Object> param = BeanToMapUtil.objectToMap(queryServiceUserDto);
        return WrapMapper.ok(serviceUserService.getServiceByCondition(param,queryServiceUserDto.getPageNum(),queryServiceUserDto.getPageSize()));
    }
}