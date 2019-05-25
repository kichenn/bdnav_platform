package com.bdxh.appburied.fallback;

import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Component
public class ApplyLogControllerClientFallback implements ApplyLogControllerClient {
    @Override
    public Wrapper addApplyLog(AddApplyLogDto addApplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyApplyLog(ModifyApplyLogDto modifyApplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delApplyLogById(DelOrFindAppBuriedDto AddapplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<ApplyLog> findApplyLogById(DelOrFindAppBuriedDto findApplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ApplyLog>> findApplyLogInContionPaging(ApplyLogQueryDto applyLogQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<ApplyLog>> familyFindApplyLogInfo(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyVerifyApplyLog(ModifyApplyLogDto modifyApplyLogDto) {
        return WrapMapper.error();
    }
}