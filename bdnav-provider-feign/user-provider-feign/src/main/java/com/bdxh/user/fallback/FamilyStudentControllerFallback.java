package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import org.springframework.stereotype.Component;

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
    public Wrapper queryAllFamilyStudent(String studentName, String schoolCode) {
        return WrapMapper.error();
    }
}