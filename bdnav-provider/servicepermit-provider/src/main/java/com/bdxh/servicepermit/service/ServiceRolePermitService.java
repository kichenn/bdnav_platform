package com.bdxh.servicepermit.service;

import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.entity.ServiceRolePermit;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Service
public interface ServiceRolePermitService extends IService<ServiceRolePermit> {

    /**
     * 查询所有数量
     */
    Integer getServiceRolePermitAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelServiceRolePermitInIds(List<Long> id);


    /**
     * 根据服务许可id删除服务角色权限
     *
     * @param serviceUserId
     * @return
     */
    Boolean delServiceRolePermitByServiceUserId(Long serviceUserId);

    /**
     * 增加服务许可角色权限信息
     *
     * @return
     */
    Boolean addServiceRolePermitInfo(AddServiceRolePermitDto addServiceRolePermitDto);

    /**
     * @Description: 家长id查询 服务许可信息（一个家长有多个孩子）
     * @Author: Kang
     * @Date: 2019/6/1 14:57
     */
    List<ServiceRolePermitInfoVo> findServiceRolePermitInfoVo(String familyCardNumber);
}
