package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.dto.UserQueryDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.persistence.UserMapper;
import com.bdxh.system.persistence.UserRoleMapper;
import com.bdxh.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PageInfo<UserQueryDto> findListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserQueryDto> roleLogs = userMapper.getByCondition(param);
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
        if (!addUserDto.getRoleIds().equals("")&&!addUserDto.getRoleIds().equals(null)){
            String [] roleIds=addUserDto.getRoleIds().split(",");
            /*     AddUserDto user = BeanMapUtils.map(addUserDto, AddUserDto.class);*/
            addUserDto.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
            userMapper.addUsers(addUserDto);
            if (roleIds != null&&roleIds.length>0){
                for (int i = 0; i <roleIds.length ; i++) {
                    UserRole userRole=new UserRole();
                    userRole.setUserId(addUserDto.getId());
                    userRole.setRoleId(Long.valueOf(roleIds[i]));
                    userRoleMapper.insert(userRole);
                }
            }
        }else{
            addUserDto.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
            userMapper.addUsers(addUserDto);
        }


    }

    public static boolean stringArrayCompare(String[] b, List<String> c) {
        boolean flag = false;
        for (int i = 0; i < c.size(); i++) {
            for (int j = 0; j < b.length; j++) {
                if (c.get(i).equals(b[j])&&b.length==c.size()) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    @Override
    public void updateUsers(UpdateUserDto updateUserDto) {
        if (!updateUserDto.getRoleIds().equals("")&&!updateUserDto.getRoleIds().equals(null)){
            String [] roleIds=updateUserDto.getRoleIds().split(",");
            List<String> Urbyids=findUserRoleByUserId(updateUserDto.getId());
           Boolean falg=stringArrayCompare(roleIds, Urbyids);
         if (falg.equals(Boolean.FALSE)){
                UserRole userRole = new UserRole();
                userRole.setUserId(updateUserDto.getId());
                userRoleMapper.delete(userRole);
                for (int i = 0; i <roleIds.length ; i++) {
                    UserRole addUr=new UserRole();
                    addUr.setUserId(updateUserDto.getId());
                    addUr.setRoleId(Long.valueOf(roleIds[i]));
                    userRoleMapper.insert(addUr);
                }
                userMapper.UpdateUsers(updateUserDto);
            }else{
                userMapper.UpdateUsers(updateUserDto);
            }
        }else{
            UserRole userRole = new UserRole();
            userRole.setUserId(updateUserDto.getId());
            userRoleMapper.delete(userRole);
            userMapper.UpdateUsers(updateUserDto);
        }
    }

    @Override
    public List<String> findUserRoleByUserId(Long userId) {
        List<String> userRoles = new ArrayList<>();
        List<UserRole> UrList = userRoleMapper.findUserRoleByUserId(userId);
        if (CollectionUtils.isNotEmpty(UrList)) {
            UrList.stream().forEach(e -> {
                userRoles.add(String.valueOf(e.getRoleId()));
            });
        }
        return userRoles;
    }


    /**
     * 用户id启用或者禁用信息
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean enableAndDisable(Long id, Byte status) {
        UpdateUserDto uud=new UpdateUserDto();
        uud.setId(id);
        uud.setStatus(status);
        return userMapper.UpdateUsers(uud) > 0;
    }


}
