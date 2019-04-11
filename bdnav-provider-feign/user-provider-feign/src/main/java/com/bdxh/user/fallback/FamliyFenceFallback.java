package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.feign.FamilyFenceControllerClient;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: binzh
 * @create: 2019-04-11 18:53
 **/
@Component
public class FamliyFenceFallback implements FamilyFenceControllerClient {
    @Override
    public Wrapper updateFamilyFenceInfo( UpdateFamilyFenceDto updateFamilyFenceDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper getFamilyFenceInfos( FamilyFenceQueryDto familyFenceQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<FamilyFence> getFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addFamilyFenceInfo( AddFamilyFenceDto addFamilyFenceDto) {
        return WrapMapper.error();
    }
}