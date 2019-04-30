package com.bdxh.school.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSinglePermission;
import com.bdxh.school.dto.ModifySinglePermission;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.school.vo.SinglePermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 学校门禁单eign降级服务
 * @Author: Kang
 * @Date: 2019/3/27 16:43
 */
@Component
public class SinglePermissionControllerClientFallback implements SinglePermissionControllerClient {


    @Override
    public Wrapper addSinglePermission(AddSinglePermission addSinglePermission) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifySinglePermission(ModifySinglePermission modifySinglePermission) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delSinglePermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchSinglePermissionByInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<SinglePermission> findSinglePermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<SinglePermissionShowVo>> findSinglePermissionInConditionPage(SinglePermissionQueryDto singlePermissionQueryDto) {
        return WrapMapper.error();
    }
}
