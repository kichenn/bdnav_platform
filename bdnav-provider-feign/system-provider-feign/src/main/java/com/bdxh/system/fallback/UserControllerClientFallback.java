package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.dto.UserQueryDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.feign.UserControllerClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 系统用户feign降级服务
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Component
public class UserControllerClientFallback implements UserControllerClient {

    @Override
    public Wrapper<User> queryUserByUserName(String userName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addUser(AddUserDto addUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateUser(UpdateUserDto updateUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryListPage(UserQueryDto userQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delUser(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchUser(String ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Long>> findUserRoleByUserId(Long userId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper enableAndDisable(Long userId, Byte status) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<UserRole>> findUserRelationship(Long userId) {
        return WrapMapper.error();
    }


}
