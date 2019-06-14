package com.bdxh.servicepermit.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.properties.ServiceUserConstant;
import com.bdxh.servicepermit.service.ServiceRolePermitService;
import com.bdxh.servicepermit.service.ServiceUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.persistence.ServiceUserMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Date 2019-04-26 10:26:58
 */
@Service
@Slf4j
public class ServiceUserServiceImpl extends BaseService<ServiceUser> implements ServiceUserService {

    @Autowired
    private ServiceUserMapper serviceUserMapper;

    @Autowired
    private ServiceRolePermitService serviceRolePermitService;

    @Autowired
    private ServiceRoleServiceImpl serviceRoleService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /*
     *查询总条数
     */
    @Override
    public Integer getServiceUsedAllCount() {
        return serviceUserMapper.getServiceUserAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelServiceUsedInIds(List<Long> ids) {
        return serviceUserMapper.delServiceUserInIds(ids) > 0;
    }

    @Override
    public PageInfo<ServiceUser> getServiceByCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceUser> orders = serviceUserMapper.getServiceByCondition(param);
        PageInfo<ServiceUser> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByServiceId(String SchoolCode, String cardNumber, Long id) {
        return serviceUserMapper.deleteByServiceId(SchoolCode, cardNumber, id) > 0;
    }

    @Override
    public List<ServiceUser> queryAllServiceUser(QueryServiceUserDto queryServiceUsedDto) {
        Map<String, Object> param = BeanToMapUtil.objectToMap(queryServiceUsedDto);
        return serviceUserMapper.getServiceByCondition(param);
    }


    /**
     * @Description: 鉴定是否有试用资格
     * @Author: Kang
     * @Date: 2019/6/13 15:12
     */
    @Override
    public List<ServiceUser> findServicePermitByCondition(String schoolCode, String studentCardNumber, String familyCardNumber, Integer type, Integer status) {
        return serviceUserMapper.findServicePermitByCondition(schoolCode, studentCardNumber, familyCardNumber, type, status);
    }

    /**
     * @Description: 领取试用权限
     * @Author: Kang
     * @Date: 2019/6/13 16:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOnTrialService(AddNoTrialServiceUserDto addNoTrialServiceUserDto) {
        //增加权限记录
        ServiceUser serviceUser = new ServiceUser();
        BeanUtils.copyProperties(addNoTrialServiceUserDto, serviceUser);
        String startTime = DateUtil.now2();
        String endTime = DateUtil.addDay(startTime, ServiceUserConstant.TEST_DAYS);
        //可用天数，默认七天
        serviceUser.setDays(ServiceUserConstant.TEST_DAYS);
        //开始使用时间
        serviceUser.setStartTime(DateUtil.format(startTime, "yyyy-MM-dd"));
        //结束使用时间
        serviceUser.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd"));
        serviceUser.setStatus(1);
        serviceUser.setType(1);
        serviceUser.setProductId(new Long("1001"));
        serviceUser.setProductName("测试权限商品（适用所有商品信息）....");
        serviceUser.setOrderNo(new Long("10001"));
        serviceUser.setId(snowflakeIdWorker.nextId());
        serviceUserMapper.insertSelective(serviceUser);
        //查询试用角色
        ServiceRole serviceRole = serviceRoleService.findServiceRoleByName(ServiceUserConstant.ROLE_TEST);
        //绑定试用权限记录和试用角色信息
        AddServiceRolePermitDto addServiceRolePermitDto = new AddServiceRolePermitDto();
        addServiceRolePermitDto.setSchoolCode(serviceUser.getSchoolCode());
        addServiceRolePermitDto.setCardNumber(serviceUser.getCardNumber());
        addServiceRolePermitDto.setServiceUserId(serviceUser.getId());
        addServiceRolePermitDto.setServiceRoleId(serviceRole.getId());
        addServiceRolePermitDto.setRemark("试用角色权限绑定");
        serviceRolePermitService.addServiceRolePermitInfo(addServiceRolePermitDto);
    }

}
