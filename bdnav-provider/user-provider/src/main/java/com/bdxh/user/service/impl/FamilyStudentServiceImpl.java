package com.bdxh.user.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyStudentService;
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

    @Override
    public List<FamilyStudent> getFamilyStudentByFamilyId(Long familyId) {
        return familyStudentMapper.getFamilyStudentByFamilyId(familyId);
    }
}
