package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.feign.BaseUserControllerClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 14:36
 **/
@Component
public class BaseUserControllerFallback implements BaseUserControllerClient {
    @Override
    public Wrapper<List<String>> queryAllUserPhone() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryUserPhoneByPhone(BaseUserQueryDto baseUserQueryDto) {
        return WrapMapper.error();
    }
}