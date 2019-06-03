package com.bdxh.servicepermit.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceRoleControllerClient;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ServiceRoleControllerClientFallback implements ServiceRoleControllerClient {

    @Override
    public Wrapper addServiceRole(AddServiceRoleDto addServiceRole) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delServiceRoleById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyServiceRoleById(ModifyServiceRoleDto modifyServiceRoleDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ServiceRole>> queryServiceRole(ServiceRoleQueryDto serviceRoleQueryDto) {
        return WrapMapper.error();
    }
}
