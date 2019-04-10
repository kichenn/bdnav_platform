package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.entity.TeacherDept;

/**
 * @description: 老师部门关联表service
 * @author: xuyuan
 * @create: 2019-02-26 10:46
 **/
public interface TeacherDeptService extends IService<TeacherDept> {
    /**
     * @description: 老师部门关联表service
     * @author: xuyuan
     * @create: 2019-02-26 10:46
     **/
    void deleteTeacherDeptInfo(String schoolCode, String cardNumber, Integer deptId);

    /**
     * @Description: 学校code，学校id，部门id查询老师信息
     * @Author: Kang
     * @Date: 2019/3/23 11:40
     */
    TeacherDept findTeacherBySchoolDeptId(String schoolCode, Long schoolId, Long deptId);
}
