package com.bdxh.onecard.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.XianAddBlanceDto;
import com.bdxh.onecard.dto.XianQueryBlanceDto;
import com.bdxh.onecard.dto.XianQueryConsListDto;
import com.bdxh.onecard.dto.XianSyscDataDto;
import com.bdxh.onecard.feigin.XianCardControllerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 西安一卡通feigin客户端
 * @author: xuyuan
 * @create: 2019-01-11 18:42
 **/
public class XianCardControllerFallback implements XianCardControllerClient {

    @Override
    public Wrapper syscUser(XianSyscDataDto xianSyscDataDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryBalance(XianQueryBlanceDto xianQueryBlanceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addBalance(XianAddBlanceDto xianAddBlanceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryTradeList(XianQueryConsListDto xianQueryConsListDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryAddResult(String schoolCode, String orderNo) {
        return WrapMapper.error();
    }

}
