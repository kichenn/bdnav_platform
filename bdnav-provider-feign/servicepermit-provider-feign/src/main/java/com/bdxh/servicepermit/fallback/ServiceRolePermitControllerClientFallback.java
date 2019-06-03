package com.bdxh.servicepermit.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceRoleDto;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.dto.ModifyServiceRoleDto;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.feign.ServiceRoleControllerClient;
import com.bdxh.servicepermit.feign.ServiceRolePermitControllerClient;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ServiceRolePermitControllerClientFallback implements ServiceRolePermitControllerClient {

    @Override
    public Wrapper delServiceRolePermitByServiceUserId(Long serviceUserId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addServiceRolePermitInfo(AddServiceRolePermitDto addServiceRolePermitDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<ServiceRolePermitInfoVo>> findServiceRolePermitInfoVo(String familyCardNumber) {
        return WrapMapper.error();
    }
}
