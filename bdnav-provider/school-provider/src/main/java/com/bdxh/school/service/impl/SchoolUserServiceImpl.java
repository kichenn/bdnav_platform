package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.entity.SchoolUserRole;
import com.bdxh.school.persistence.SchoolUserMapper;
import com.bdxh.school.persistence.SchoolUserRoleMapper;
import com.bdxh.school.service.SchoolUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 系统用户管理service实现
 * @Author: Kang
 * @Date: 2019/3/26 14:11
 */
@Service
@Slf4j
public class SchoolUserServiceImpl extends BaseService<SchoolUser> implements SchoolUserService {

    @Autowired
    private SchoolUserMapper schoolUserMapper;

    @Autowired
    private SchoolUserRoleMapper schoolUserRoleMapper;


    @Override
    public PageInfo<SchoolUser> findListPage(SchoolUserQueryDto schoolUserQueryDto) {
        Page page = PageHelper.startPage(schoolUserQueryDto.getPageNum(), schoolUserQueryDto.getPageSize());
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(schoolUserQueryDto, schoolUser);
        List<SchoolUser> roleLogs = schoolUserMapper.getByCondition(schoolUser);

        PageInfo<SchoolUser> pageInfo = new PageInfo(roleLogs);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public SchoolUser getByUserName(String userName) {
        SchoolUser user = schoolUserMapper.getByUserName(userName);
        return user;
    }

    @Override
    public void delUser(Long id) {
        schoolUserMapper.deleteByPrimaryKey(id);
        SchoolUserRole userRole = new SchoolUserRole();
        userRole.setUserId(id);
        schoolUserRoleMapper.delete(userRole);
    }

    @Override
    public Boolean delBatchSchoolUserInIds(List<Long> ids) {
        return schoolUserMapper.delBatchSchoolUserInIds(ids) > 0;
    }

    @Override
    public List<String> findUserRoleByUserId(Long userId) {
        List<String> userRoles = new ArrayList<>();
        List<SchoolUserRole> UrList = schoolUserRoleMapper.findUserRoleByUserId(userId);
        if (CollectionUtils.isNotEmpty(UrList)) {
            UrList.stream().forEach(e -> {
                userRoles.add(String.valueOf(e.getRoleId()));
            });
        }
        return userRoles;
    }

    /**
     * @Description: 用户id启用或者禁用信息
     * @Author: Kang
     * @Date: 2019/3/27 10:38
     */
    @Override
    public Boolean modifySchoolUserStatusById(Long id, Byte status) {
        SchoolUser schoolUser = new SchoolUser();
        schoolUser.setId(id);
        schoolUser.setStatus(status);
        return schoolUserMapper.updateByPrimaryKeySelective(schoolUser) > 0;
    }

    /**
     * @Description: 修改学校用户信息
     * @Author: Kang
     * @Date: 2019/4/2 11:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifySchoolUser(ModifySchoolUserDto modifySchoolUserDto) {
        //更新基本信息
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(modifySchoolUserDto, schoolUser);
        //设置类型值
        schoolUser.setStatus(modifySchoolUserDto.getSchoolUserStatusEnum().getKey());
        schoolUser.setType(modifySchoolUserDto.getSchoolUserTypeEnum().getKey());
        schoolUser.setSex(modifySchoolUserDto.getSchoolUserSexEnum().getKey());
        schoolUser.setUpdateDate(new Date());
        schoolUserMapper.updateByPrimaryKeySelective(schoolUser);

        //删除学校用户和角色的关系
        schoolUserRoleMapper.delRoleByUserId(schoolUser.getId());

        //增加学校用户和角色的关系
        for (int i = 0; i < modifySchoolUserDto.getRoles().size(); i++) {
            SchoolUserRole schoolUserRole = new SchoolUserRole();
            schoolUserRole.setSchoolId(schoolUser.getSchoolId());
            schoolUserRole.setSchoolCode(schoolUser.getSchoolCode());
            schoolUserRole.setUserId(schoolUser.getId());
            schoolUserRole.setRoleId(Long.valueOf(modifySchoolUserDto.getRoles().get(i).get("id")));
            schoolUserRoleMapper.insertSelective(schoolUserRole);
        }
    }
}
