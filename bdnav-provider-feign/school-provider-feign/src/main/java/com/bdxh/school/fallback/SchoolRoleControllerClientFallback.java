package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolRoleDto;
import com.bdxh.school.dto.ModifySchoolRoleDto;
import com.bdxh.school.dto.SchoolRoleQueryDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.feign.SchoolRoleControllerClient;
import com.bdxh.school.vo.SchoolRoleInfoVo;
import com.bdxh.school.vo.SchoolRoleShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
* @Description: 学校系统角色feign降级服务
* @Author: Kang
* @Date: 2019/3/26 16:00
*/
@Component
public class SchoolRoleControllerClientFallback implements SchoolRoleControllerClient {

    @Override
    public Wrapper<List<String>> findSchoolRolesByUserId(Long userId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<Long>> getRoleIdListByUserId(Long userId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolRoleInfoVo> findSchoolRoleById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolRoleShowVo>> findRolesInConditionPage(SchoolRoleQueryDto roleQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolRole(AddSchoolRoleDto addSchoolRoleDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolRole(ModifySchoolRoleDto ModifySchoolRoleDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolRole(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchSchoolRole(List<Long> ids) {
        return WrapMapper.error();
    }

}
