package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import com.bdxh.user.vo.FenceAlarmVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 15:11
 **/
@Component
public class FenceAlarmControllerFallback implements FenceAlarmControllerClient {
    @Override
    public Wrapper<PageInfo<FenceAlarmVo>> getAllFenceAlarmInfos(FenceAlarmQueryDto fenceAlarmQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<FenceAlarmVo> getFenceAlarmInfo(@NotNull(message = "schoolCode不能为空") String schoolCode, @NotNull(message = "cardNumber不能为空") String cardNumber, @NotNull(message = "id不能为空") String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeFenceAlarmInfo(String id, String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchRemoveFenceAlarmInfo(String ids, String schoolCodes, String cardNumbers) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateFenceAlarmInfo(UpdateFenceAlarmDto updateFenceAlarmDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper insertFenceAlarmInfo(AddFenceAlarmDto addFenceAlarmDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<FenceAlarmVo>> getFenceAlarmInfos(@NotNull(message = "schoolCode不能为空") String schoolCode, @NotNull(message = "cardNumber不能为空") String cardNumber, @NotNull(message = "围栏Id不能为空") String fenceId) {
        return WrapMapper.error();
    }
}