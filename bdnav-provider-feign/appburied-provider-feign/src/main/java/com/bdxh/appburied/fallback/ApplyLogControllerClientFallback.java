package com.bdxh.appburied.fallback;

import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.appburied.vo.informationVo;
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
    public Wrapper<PageInfo<ApplyLog>> familyFindApplyLogInfo(FamilyQueryApplyLogDto familyQueryApplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyVerifyApplyLog(ModifyApplyLogDto modifyApplyLogDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<informationVo>> checkMymessages(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<ApplyLog>> findApplyLogInfoByFamily(FamilyQueryApplyLogDto familyQueryApplyLogDto) {
        return WrapMapper.error();
    }
}