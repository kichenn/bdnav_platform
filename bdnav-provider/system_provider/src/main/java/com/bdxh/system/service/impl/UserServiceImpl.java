package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.User;
import com.bdxh.system.persistence.UserMapper;
import com.bdxh.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Override
    public List<User> findList(Map<String, Object> param) {
        return userMapper.getByCondition(param);
    }

    @Override
    public PageInfo<User> findListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> roleLogs = userMapper.getByCondition(param);
        PageInfo<User> pageInfo=new PageInfo<>(roleLogs);
        return pageInfo;
    }
}
