package com.bdxh.servicepermit.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.enums.ServiceProductEnum;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.servicepermit.vo.ServiceUserVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ServiceUserControllerClientFallback implements ServiceUserControllerClient {


    @Override
    public Wrapper createServiceUser(AddServiceUserDto addServiceUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<ServiceUser>> findServicePermitAll() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper findServicePermitByCondition(String schoolCode, String studentCardNumber, String familyCardNumber, Long productId) {
        return WrapMapper.error();
    }


    @Override
    public Wrapper createOnTrialService(AddNoTrialServiceUserDto addNoTrialServiceUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper createPayService(AddPayServiceUserDto addPayServiceUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateServiceUser(ModifyServiceUserDto modifyServiceUserDto) {
        return WrapMapper.error();
    }


    @Override
    public Wrapper deleteService(String schoolCode, String cardNumber, Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ServiceUserVo>> queryServiceUser(QueryServiceUserDto queryServiceUsedDto) {
        return WrapMapper.error();
    }
}
