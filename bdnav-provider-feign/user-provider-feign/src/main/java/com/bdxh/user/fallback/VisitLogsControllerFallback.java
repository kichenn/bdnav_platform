package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddVisitLogsDto;
import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.feign.VisitLogsControllerClient;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-19 16:10
 **/
@Component
public class VisitLogsControllerFallback implements VisitLogsControllerClient {
    @Override
    public Wrapper<PageInfo<VisitLogsVo>> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<VisitLogsVo> getVisitLogsInfo(@NotNull(message = "schoolCode不能为空") String schoolCode, @NotNull(message = "cardNumber不能为空") String cardNumber, @NotNull(message = "id不能为空") String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeVisitLogsInfo(String id, String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchRemoveVisitLogsInfo(String ids, String schoolCodes, String cardNumbers) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateVisitLogsInfo(UpdateVisitLogsDto updateVisitLogsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper insertVisitLogsInfo(AddVisitLogsDto addVisitLogsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<VisitLogsVo>> queryVisitLogByCardNumber(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }
}