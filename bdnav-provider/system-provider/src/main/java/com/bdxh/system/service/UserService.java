package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.dto.AddUserDto;
import com.bdxh.system.dto.UpdateUserDto;
import com.bdxh.system.dto.UserQueryDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.entity.UserRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 系统用户管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:03
 **/
public interface UserService extends IService<User> {


    /**
     * 根据条件分页查询用户列表
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserQueryDto> findListPage(Map<String,Object> param, Integer pageNum, Integer pageSize);

    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 根据id删除用户信息
     * @param id
     */
    void delUser(Long id);

    /**
     * 根据id批量删除用户信息
     * @param ids
     */
    void delBatchUser(List<Long> ids);

    /**
     * 添加用户
     */
    Boolean addUsers(AddUserDto addUserDto);
    /**
     * 修改用户
     */
    Boolean updateUsers(UpdateUserDto updateUserDto);

   /**
    * 根据用户id查询所有关系
    * @param userId
    * @return
    */
    List<String> findUserRoleByUserId(Long userId);

    /**
     * 启用与禁止
     * @param id
     * @param status
     * @return
     */
    Boolean enableAndDisable(Long id,Byte status);

}
