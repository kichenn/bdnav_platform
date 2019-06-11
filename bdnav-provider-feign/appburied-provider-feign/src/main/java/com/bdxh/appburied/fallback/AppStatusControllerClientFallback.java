package com.bdxh.appburied.fallback;

import com.bdxh.appburied.dto.AddAppStatusDto;
import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyAppStatusDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.weixiao.dto.WeiXiaoAppStatusUnlockOrLokingDto;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Component
public class AppStatusControllerClientFallback implements AppStatusControllerClient {
    @Override
    public Wrapper addAppStatus(AddAppStatusDto addAppStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyAppStatus(ModifyAppStatusDto modifyAppStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delAppStatusById(DelOrFindAppBuriedDto appStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<AppStatus> findAppStatusById(DelOrFindAppBuriedDto findAppStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<AppStatus>> findAppStatusInContionPaging(AppStatusQueryDto appStatusQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<AppStatus>> findAppStatusInfoBySchoolCodeAndCardNumber(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> appStatusLockingAndUnlock( WeiXiaoAppStatusUnlockOrLokingDto weiXiaoAppStatusUnlockOrLokingDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> findAppStatusInByAccount(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }
}