package com.bdxh.account.fallback;

import com.bdxh.account.dto.AddUserDevice;
import com.bdxh.account.dto.ModifyUserDevice;
import com.bdxh.account.dto.QueryUserDeviceDto;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class UserDeviceControllerClientFallback implements UserDeviceControllerClient {


    @Override
    public Wrapper getUserDeviceAll(String schoolCode, String ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addUserDeviceInfo(@Valid AddUserDevice addUserDevice) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyUserDeviceInfo(@Valid ModifyUserDevice modifyUserDevice) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delUserDeviceById(@Valid Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<UserDevice> findUserDeviceByCodeOrCard(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<UserDevice>> findUserDeviceList(String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<UserDevice>> findUserDeviceByCondition(QueryUserDeviceDto queryUserDeviceDto) {
        return WrapMapper.error();
    }
}
