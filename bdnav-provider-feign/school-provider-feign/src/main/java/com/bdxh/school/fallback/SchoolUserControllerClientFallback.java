package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.dto.ShowSchoolUserModifyPrefixDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.school.vo.SchoolUserShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校系统用户feign降级服务
 * @Author: Kang
 * @Date: 2019/3/26 16:22
 */
@Component
public class SchoolUserControllerClientFallback implements SchoolUserControllerClient {

    @Override
    public Wrapper<SchoolUser> findSchoolUserByName(String userName) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addSchoolUser(AddSchoolUserDto addSchoolUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<ShowSchoolUserModifyPrefixDto> findSchoolUserById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolUser(ModifySchoolUserDto modifySchoolUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SchoolUserShowVo>> findSchoolUsersInConditionPage(SchoolUserQueryDto schoolUserQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSchoolUser(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchSchoolUserInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySchoolUserStatusById(Long id, SchoolUserStatusEnum statusEnum) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<SchoolUser>> findAllSchoolUserInfo() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SchoolUser> findSchoolUserByUserNameAndSchoolId(Long schoolId, String userName) {
        return WrapMapper.error();
    }
}
