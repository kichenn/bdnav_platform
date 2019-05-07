package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 角色权限管理
 * @Author: Kang
 * @Date: 2019/3/26 12:06
 */
public interface SchoolRoleService extends IService<SchoolRole> {

    //角色id删除权限
    void delRole(Long roleId);

    //角色id批量删除权限
    void delBatchRole(List<Long> roleIds);


    //条件搜索-分页
    PageInfo<SchoolRoleShowVo> findListPage(SchoolRoleQueryDto roleQueryDto);

    //根据用户id查询角色列表(角色名称)
    List<String> getRoleListByUserId(Long userId);

    //根据用户id查询角色列表(角色id)
    List<Long> getRoleIdListByUserId(Long userId);

    //用户id查询角色id和角色名称
    List<Map<Long, String>> findRoleByUserIdResultMap(Long UserId);

    //根据角色查询角色对象
    SchoolRole getRoleByRole(String role);

    //分页查询全部角色信息
    PageInfo<SchoolRole> findRolesInConditionPaging(Integer pageNum, Integer pageSize);

}