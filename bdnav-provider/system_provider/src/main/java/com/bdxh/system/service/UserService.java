package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 系统用户管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:03
 **/
public interface UserService extends IService<User> {

    List<User> findList(Map<String,Object> param);

    PageInfo<User> findListPage(Map<String,Object> param, int pageNum, int pageSize);
}
