package com.bdxh.user.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyStudentService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public void removeFamilyStudentInfo(String schoolCode, String cardNumber, String id) {
        familyStudentMapper.familyRemoveFamilyStudent(schoolCode, cardNumber, id);
    }

    @Override
    public PageInfo<FamilyStudentVo> queryAllFamilyStudent(FamilyStudentQueryDto familyStudentQueryDto) {
        PageHelper.startPage(familyStudentQueryDto.getPageNum(), familyStudentQueryDto.getPageSize());
        List<FamilyStudentVo> familyStudentVoList = familyStudentMapper.queryaAllFamilyStudent(familyStudentQueryDto);
        if (null != familyStudentVoList) {
            for (int i = 0; i < familyStudentVoList.size(); i++) {
                FamilyVo familyVo = familyMapper.selectByCodeAndCard(familyStudentVoList.get(i).getSchoolCode(), familyStudentVoList.get(i).getFCardNumber());
                familyStudentVoList.get(i).setFName(familyVo.getName());
                familyStudentVoList.get(i).setSchoolName(familyVo.getSchoolName());
            }
        }
        PageInfo<FamilyStudentVo> pageInfoFamilyStudent = new PageInfo<FamilyStudentVo>(familyStudentVoList);
        return pageInfoFamilyStudent;
    }


    /**
     * @Description: 学生卡号 +学校code 查询绑定关系
     * @Author: Kang
     * @Date: 2019/6/1 10:13
     */
    @Override
    public FamilyStudentVo studentQueryInfo(String schoolCode, String cardNumber) {
        FamilyStudentVo familyStudentVo = familyStudentMapper.studentQueryInfo(schoolCode, cardNumber);
        return familyStudentVo;
    }

    /**
     * 家长卡号 +学校code  查询绑定关系信息
     *
     * @param familyCardNumber
     * @return
     */
    @Override
    public List<FamilyStudentVo> queryStudentByFamilyCardNumber(String schoolCode,String familyCardNumber) {
        List<FamilyStudentVo> familyStudentVos = familyStudentMapper.queryStudentByFamilyCardNumber(schoolCode,familyCardNumber);
        return familyStudentVos;
    }
}
