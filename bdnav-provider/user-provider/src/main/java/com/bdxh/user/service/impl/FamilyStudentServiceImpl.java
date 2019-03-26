package com.bdxh.user.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyStudentService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 学生家长关联service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:43
 **/
@Service
@Slf4j
public class FamilyStudentServiceImpl extends BaseService<FamilyStudent> implements FamilyStudentService {

    @Autowired
    private FamilyStudentMapper familyStudentMapper;

    @Autowired
    private FamilyMapper familyMapper;

    @Override
    public void removeFamilyStudentInfo(String schoolCode, String cardNumber,String id) {
    familyStudentMapper.familyRemoveFamilyStudent(schoolCode,cardNumber,id);
    }

    @Override
    public List<FamilyStudentVo> queryaAllFamilyStudent(String studentName, String schoolCode) {

        List<FamilyStudentVo> familyStudentVoList= familyStudentMapper.selectFamilyStudentInfo(studentName,schoolCode);
        for (int i = 0; i < familyStudentVoList.size(); i++) {
           FamilyVo familyVo= familyMapper.selectByCodeAndCard(familyStudentVoList.get(i).getFCardNumber(),familyStudentVoList.get(i).getSchoolCode());
            familyStudentVoList.get(i).setFName(familyVo.getName());
        }
        return familyStudentVoList;
    }
}
