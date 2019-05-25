package com.bdxh.servicepermit.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ServiceUserControllerClientFallback implements ServiceUserControllerClient {

    @Override
    public Wrapper deleteService( String schoolCode,Long cardNumber,Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ServiceUser>> queryServiceUser(QueryServiceUserDto queryServiceUsedDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateServiceUser(ModifyServiceUserDto modifyServiceUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper createServiceUser(AddServiceUserDto addServiceUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<ServiceUser>> queryAllServiceUser(String cardNumber, String schoolCode, String studentNumber) {
        return  WrapMapper.error();
    }
}
