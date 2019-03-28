package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.dto.AddSchoolPermissionDto;
import com.bdxh.school.dto.ModifySchoolPermissionDto;
import com.bdxh.school.feign.SchoolPermissionControllerClient;
import com.bdxh.school.vo.SchoolPermissionTreeVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @Description: 学校权限feign降级服务
* @Author: Kang
* @Date: 2019/3/28 14:54
*/
@Component
public class SchoolPermissionControllerClientFallback implements SchoolPermissionControllerClient {


    @Override
    public Wrapper<List<SchoolPermissionTreeVo>> findSchoolPermissionByRoleId(Long roleId, Byte type,Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolPermission(AddSchoolPermissionDto addSchoolPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolPermission(ModifySchoolPermissionDto modifySchoolPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addRolePermissionBindMenu(AddRolePermissionBindMenuDto addRolePermissionBindMenu) {
        return WrapMapper.error();
    }

}
