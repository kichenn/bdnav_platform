package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.entity.TeacherDept;

import java.util.List;

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

    /**
     * 根据学校Code和部门ID查询出当前部门和当前部门下的所有老师部门关系信息
     * @param schoolCode
     * @param deptId
     * @param type (此类型是判断是父节点还是子节点的 如果是父节点就是0,不是父节点就是1)
     * @return
     */
    List<TeacherDept> findTeacherDeptsBySchoolCode(String schoolCode,String deptId,String type);

    /**
     * 批量修改老师部门信息
     * @param teacherDepts
     */
    void batchUpdateTeacherDept(List<TeacherDept> teacherDepts);
}
