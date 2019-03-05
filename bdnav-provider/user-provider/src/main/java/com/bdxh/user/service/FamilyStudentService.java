package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.entity.FamilyStudent;

import java.util.List;

/**
 * @description: 学生家长关联service
 * @author: xuyuan
 * @create: 2019-02-26 10:43
 **/
public interface FamilyStudentService extends IService<FamilyStudent> {
    /**
     * @Author： binzh
     * @Description： //根据家长ID查询出关系表的数据
     * @Date： 18:02 2019/3/4
     * @Param： [familyId]
     * @return： java.util.List<com.bdxh.user.entity.FamilyStudent>
     **/
     List<FamilyStudent> getFamilyStudentByFamilyId(Long familyId);
}
