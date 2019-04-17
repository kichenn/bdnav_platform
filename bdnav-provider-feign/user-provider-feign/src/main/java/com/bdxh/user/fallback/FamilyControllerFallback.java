package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.vo.FamilyVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:17
 **/
@Component
public class FamilyControllerFallback implements FamilyControllerClient {
    @Override
    public Wrapper addFamily(AddFamilyDto addFamilyDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeFamily(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeFamilys(String schoolCodes, String cardNumbers) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateFamily(UpdateFamilyDto updateFamilyDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<FamilyVo> queryFamilyInfo(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryFamilyListPage(FamilyQueryDto familyQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper batchSaveFamilyInfo(List<AddFamilyDto> familyList) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryFamilyCardNumberBySchoolCode(String schoolCode) {
            return WrapMapper.error();
    }
}