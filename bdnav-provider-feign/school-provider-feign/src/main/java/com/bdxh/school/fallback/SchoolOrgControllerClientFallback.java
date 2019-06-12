package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolOrgAddDto;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.school.vo.SchoolOrgTreeVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-03 20:10
 **/
@Component
public class SchoolOrgControllerClientFallback implements SchoolOrgControllerClient {
    @Override
    public Wrapper<List<SchoolOrg>> findAllSchoolOrgInfo(SchoolOrgQueryDto schoolOrgQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolOrgTreeVo>> findSchoolOrgTreeInfo(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolOrg> findSchoolOrgInfo(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolOrgTreeVo>> findClassOrg(Long schoolId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeSchoolOrgInfo(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> updateSchoolOrgInfo(SchoolOrgUpdateDto schoolOrgUpdateDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolOrg>> findAllOrgInfo() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolOrg>> findBySchoolOrgByParentId(Long parentId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean>  insertSchoolOrgInfo(SchoolOrgAddDto schoolOrgAddDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolOrg>> findClassOrgList(Long schoolId) {
        return WrapMapper.error();
    }
}