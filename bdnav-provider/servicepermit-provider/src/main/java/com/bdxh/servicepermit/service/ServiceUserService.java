package com.bdxh.servicepermit.service;

import com.bdxh.common.support.IService;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-04-26 10:26:58
 */
@Service
public interface ServiceUserService extends IService<ServiceUser> {

    /**
     * 查询所有数量
     */
    Integer getServiceUsedAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelServiceUsedInIds(List<Long> id);

    /**
     * 根据条件查询用户订单列表
     *
     * @param param
     * @return
     */
    PageInfo<ServiceUser> getServiceByCondition(Map<String, Object> param, Integer pageNum, Integer pageSize);

    /**
     * 删除用户许可
     *
     * @param SchoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    Boolean deleteByServiceId(String SchoolCode, String cardNumber, Long id);


    /**
     * 查询单个学生的购买记录
     *
     * @param queryServiceUsedDto
     * @return
     */
    List<ServiceUser> queryAllServiceUser(QueryServiceUserDto queryServiceUsedDto);

    /**
     * @Description: 鉴定是否有试用资格
     * @Author: Kang
     * @Date: 2019/6/13 15:12
     */
    List<ServiceUser> findServicePermitByCondition(String schoolCode, String studentCardNumber, String familyCardNumber, Integer type, Integer status);

    /**
     * @Description: 领取试用权限
     * @Author: Kang
     * @Date: 2019/6/13 16:07
     */
    void createOnTrialService(AddNoTrialServiceUserDto addNoTrialServiceUserDto);
}
