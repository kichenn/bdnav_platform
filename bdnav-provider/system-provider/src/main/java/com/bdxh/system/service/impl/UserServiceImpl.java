package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.persistence.UserMapper;
import com.bdxh.system.persistence.UserRoleMapper;
import com.bdxh.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * @description: 系统用户管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 17:03
 **/
@Service
@Slf4j
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public PageInfo<User> findListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> roleLogs = userMapper.getByCondition(param);
        return new PageInfo(roleLogs);
    }

    @Override
    public User getByUserName(String userName) {
        User user = userMapper.getByUserName(userName);
        return user;
    }

    @Override
    public void delUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRoleMapper.delete(userRole);
    }

    @Override
    public void delBatchUser(List<Long> ids) {
        if (ids != null&&!ids.isEmpty()){
            ids.forEach(id->{
                userMapper.deleteByPrimaryKey(id);
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRoleMapper.delete(userRole);
            });
        }
    }


    @Override
    public void addUsers(AddUserDto addUserDto) {
        String [] roleIds=addUserDto.getRoleIds().split(",");
        AddUserDto user = BeanMapUtils.map(addUserDto, AddUserDto.class);
        user.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
           userMapper.addUsers(user);
        if (roleIds != null&&roleIds.length>0){
            for (int i = 0; i <roleIds.length ; i++) {
                UserRole userRole=new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Long.valueOf(roleIds[i]));
                userRoleMapper.insert(userRole);
            }
        }

    }

    @Override
    public void updateUsers(UpdateUserDto updateUserDto) {

    }


}
