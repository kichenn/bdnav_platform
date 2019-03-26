package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.Role;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.common.example.UpdateByExampleMapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 角色管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:05
 **/
public interface RoleService extends IService<Role> {

    void delRole(Long roleId);

    void delBatchRole(List<Long> roleIds);

    List<Role> findList(Map<String,Object> param);

    PageInfo<Role> findListPage(Map<String,Object> param, int pageNum, int pageSize);

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<String> getRoleListByUserId(Long userId);

    /**
     * 根据角色查询角色对象
     * @param role
     * @return
     */
    Role getRoleByRole(String role);

    //分页查询全部角色信息
    PageInfo<Role> findRolesInConditionPaging(Integer pageNum, Integer pageSize);


}