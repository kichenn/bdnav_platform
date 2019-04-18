package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.vo.SchoolUserShowVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 系统用户管理service
 * @Author: Kang
 * @Date: 2019/3/26 12:12
 */
public interface SchoolUserService extends IService<SchoolUser> {


    /**
     * 根据条件分页查询用户列表
     *
     * @return
     */
    PageInfo<SchoolUserShowVo> findListPage(SchoolUserQueryDto schoolUserQueryDto);

    /**
     * 根据用户名查询用户对象
     *
     * @param userName
     * @return
     */
    SchoolUser getByUserName(String userName);

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    void delUser(Long id);

    /**
     * 根据id批量删除用户信息
     *
     * @param ids
     */
    Boolean delBatchSchoolUserInIds(List<Long> ids);

    /**
     * 根据用户id查询所有关系
     *
     * @param userId
     * @return
     */
    List<String> findUserRoleByUserId(Long userId);

    /**
     * @Description: 用户id启用或者禁用信息
     * @Author: Kang
     * @Date: 2019/3/27 10:38
     */
    Boolean modifySchoolUserStatusById(Long id, Byte status);


    /**
    * @Description: 新增学校用户信息
    * @Author: Kang
    * @Date: 2019/4/3 10:19
    */
    void addSchoolUser(AddSchoolUserDto addSchoolUserDto);
    /**
     * @Description: 修改学校用户信息
     * @Author: Kang
     * @Date: 2019/4/2 11:34
     */
    void modifySchoolUser(ModifySchoolUserDto modifySchoolUserDto);
}
