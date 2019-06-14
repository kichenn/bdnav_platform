package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.ActivationBaseUserDto;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.vo.BaseEchartsVo;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 14:36
 **/
@Component
public class BaseUserControllerFallback implements BaseUserControllerClient {
    @Override
    public Wrapper<List<String>> queryAllUserPhone(BaseUserUnqiue baseUserUnqiue) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryUserPhoneByPhone(BaseUserQueryDto baseUserQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<BaseUser> queryBaseUserInfoByPhone(String phone) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<BaseUser>> findAllBaseUserInfo() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<BaseUser> queryBaseUserBySchoolCodeAndCardNumber(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> baseUserActivation(ActivationBaseUserDto activationBaseUserDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> findSchoolNumberBySchool(String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> getPhoneCode(@NotNull(message = "手机号码不能为空") String phone) {
        return WrapMapper.error();
    }


    @Override
    public Wrapper<List<BaseEchartsVo>> querySchoolUserCategoryCount(String schoolCode) {
        return WrapMapper.error();
    }
}