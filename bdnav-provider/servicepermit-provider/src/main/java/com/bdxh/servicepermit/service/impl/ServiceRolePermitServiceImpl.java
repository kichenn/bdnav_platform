package com.bdxh.servicepermit.service.impl;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.service.ServiceRolePermitService;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceRolePermit;
import com.bdxh.servicepermit.persistence.ServiceRolePermitMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Service
@Slf4j
public class ServiceRolePermitServiceImpl extends BaseService<ServiceRolePermit> implements ServiceRolePermitService {

    @Autowired
    private ServiceRolePermitMapper serviceRolePermitMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /*
     *查询总条数
     */
    @Override
    public Integer getServiceRolePermitAllCount() {
        return serviceRolePermitMapper.getServiceRolePermitAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelServiceRolePermitInIds(List<Long> ids) {
        return serviceRolePermitMapper.delServiceRolePermitInIds(ids) > 0;
    }

    /**
     * 根据服务许可id删除服务角色权限
     *
     * @param serviceUserId
     * @return
     */
    @Override
    public Boolean delServiceRolePermitByServiceUserId(Long serviceUserId) {
        ServiceRolePermit serviceRolePermit = new ServiceRolePermit();
        serviceRolePermit.setServiceUserId(serviceUserId);
        return serviceRolePermitMapper.delete(serviceRolePermit) > 0;
    }

    /**
     * 增加服务许可角色权限信息
     *
     * @return
     */
    @Override
    public Boolean addServiceRolePermitInfo(AddServiceRolePermitDto addServiceRolePermitDto) {
        ServiceRolePermit serviceRolePermit = new ServiceRolePermit();
        BeanUtils.copyProperties(addServiceRolePermitDto, serviceRolePermit);
        serviceRolePermit.setId(snowflakeIdWorker.nextId());
        return serviceRolePermitMapper.insertSelective(serviceRolePermit) > 0;
    }

    /**
     * @Description: 家长id查询 服务权限许可信息（一个家长有多个孩子）
     * @Author: Kang
     * @Date: 2019/6/1 14:57
     */
    @Override
    public List<ServiceRolePermitInfoVo> findServiceRolePermitInfoVo(String familyCardNumber) {
        return serviceRolePermitMapper.findServiceRolePermitInfoVo(familyCardNumber);
    }
}
