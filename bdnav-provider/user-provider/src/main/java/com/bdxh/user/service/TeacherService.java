package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.vo.TeacherVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 老师信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:39
 **/
public interface TeacherService extends IService<Teacher> {

    /**
     * @Author： binzh
     * @Description： //根据条件分页查询老师信息
     * @Date： 14:49 2019/3/6
     * @Param： [teacherQueryDto]
     * @return： com.github.pagehelper.PageInfo<com.bdxh.user.entity.Teacher>
     **/
    PageInfo<TeacherVo> getTeacherList(TeacherQueryDto teacherQueryDto);


    /**
     * @Author： binzh
     * @Description： //根据id删除老师信息以及老师部门关系绑定信息
     * @Date： 14:49 2019/3/6
     * @Param： [id]
     * @return： void
     **/
    void deleteTeacherInfo(String schoolCode,String cardNumber);

    /**
     * @Author： binzh
     * @Description： //根据id批量删除老师信息以及老师部门关系绑定信息
     * @Date： 14:49 2019/3/6
     * @Param： [id]
     * @return： void
     **/
    void deleteBatchesTeacherInfo(String schoolCode,String cardNumber);

    /**
     * @Author： binzh
     * @Description： //保存老师信息以及绑定部门信息
     * @Date： 15:15 2019/3/6
     * @Param： [teacherDto]
     * @return： void
     **/
    void saveTeacherDeptInfo(AddTeacherDto teacherDto);

    /**
     * @Author： binzh
     * @Description： //查询单个老师信息
     * @Date： 15:01 2019/3/9
     * @Param： [schoolCode, cardNumber]
     * @return： TeacherVo
     **/
    TeacherVo selectTeacherInfo(String schoolCode, String cardNumber);

    /**
     * @Author： binzh
     * @Description： //修改老师信息
     * @Date： 18:09 2019/3/9
     * @Param： [teacherDto]
     * @return： void
     **/
    void updateTeacherInfo(UpdateTeacherDto updateTeacherDto);

    /**
     * 批量新增老师
     * @param teacherList
     */
    void batchSaveTeacherInfo(List<AddTeacherDto> teacherList);

    /**
     * 根据学校Code查询所有卡号
     * @param schoolCode
     * @return
     */
    List<String> queryTeacherCardNumberBySchoolCode( String schoolCode);

    /**
     * 查询出某个部门下面的老师
     * @param schoolCode
     * @param parentIds
     * @return
     */
    List<Teacher> findTeacherInfoByDeptOrg(@Param("schoolCode")String schoolCode, @Param("parentIds")String parentIds);

    /**
     * 修改学校名字
     * @param schoolCode
     * @param schoolName
     */
    void updateSchoolName(String schoolCode,String schoolName);


}
