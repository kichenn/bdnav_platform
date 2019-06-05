package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:17
 **/
@Component
public class FamilyStudentControllerFallback implements FamilyStudentControllerClient {
    @Override
    public Wrapper bindingStudent(AddFamilyStudentDto addFamilyStudentDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper removeFamilyOrStudent(String schoolCode, String cardNumber, String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<FamilyStudentVo>> queryAllFamilyStudent(FamilyStudentQueryDto familyStudentQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> getPhoneCode(@NotNull(message = "手机号码不能为空") String phone) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<FamilyStudentVo> studentQueryInfo(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<FamilyStudentVo>> queryStudentByFamilyCardNumber(String schoolCode,String familyCardNumber) {
        return WrapMapper.error();
    }
}