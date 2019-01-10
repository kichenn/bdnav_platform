package com.bdxh.oneCard.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.oneCard.feign.OneCardControllerClient;

/**
 * 一卡通降级处理
 */
public class OneCardControllerFallback implements OneCardControllerClient {


    @Override
    public Wrapper querySchoolLog(String schoolCode) {
        return WrapMapper.error();
    }
}
