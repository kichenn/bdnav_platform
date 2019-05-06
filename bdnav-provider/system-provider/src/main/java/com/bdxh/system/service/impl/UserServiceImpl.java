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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void delUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRoleMapper.delete(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUsers(AddUserDto addUserDto) {
        Boolean falg;
        if (!addUserDto.getRoleIds().equals("")&&!addUserDto.getRoleIds().equals(null)){
            String [] roleIds=addUserDto.getRoleIds().split(",");
            addUserDto.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
            falg=userMapper.addUsers(addUserDto)>0;
            if (roleIds != null&&roleIds.length>0){
                for (int i = 0; i <roleIds.length ; i++) {
                    UserRole userRole=new UserRole();
                    System.out.println("jknxishnishidj=="+addUserDto.getId());
                    userRole.setUserId(addUserDto.getId());
                    userRole.setRoleId(Long.valueOf(roleIds[i]));
                    userRoleMapper.insert(userRole);
                }
            }

        }else{
            addUserDto.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
            falg=userMapper.addUsers(addUserDto)>0;
        }
        return falg;
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUsers(UpdateUserDto updateUserDto) {
        Boolean falgResult;
        if (!updateUserDto.getRoleIds().equals("")&&!updateUserDto.getRoleIds().equals(null)){
            String [] roleIds=updateUserDto.getRoleIds().split(",");
            List<String> urbyIds=findUserRoleByUserId(updateUserDto.getId());
            Boolean falg=stringArrayCompare(roleIds, urbyIds);
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
                falgResult= userMapper.UpdateUsers(updateUserDto)>0;
            }else{
                falgResult= userMapper.UpdateUsers(updateUserDto)>0;
            }
        }else{
            UserRole userRole = new UserRole();
            userRole.setUserId(updateUserDto.getId());
            userRoleMapper.delete(userRole);
            falgResult= userMapper.UpdateUsers(updateUserDto)>0;
        }
        return falgResult;
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
