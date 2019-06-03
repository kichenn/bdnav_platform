package com.bdxh.servicepermit.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.servicepermit.entity.ServiceRolePermit;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Repository
public interface ServiceRolePermitMapper extends Mapper<ServiceRolePermit> {

    /**
     * 查询总条数
     */
    Integer getServiceRolePermitAllCount();

    /**
     * 批量删除方法
     */
    Integer delServiceRolePermitInIds(@Param("ids") List<Long> ids);

    /**
     * 家长id查询 服务权限许可信息（一个家长有多个孩子）
     *
     * @param familyCardNumber
     * @return
     */
    List<ServiceRolePermitInfoVo> findServiceRolePermitInfoVo(@Param("familyCardNumber") String familyCardNumber);
}
