package com.bdxh.school.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.SchoolOrg;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-05-31 14:06:08
 */
@Repository
public interface SchoolOrgMapper extends Mapper<SchoolOrg> {

    /**
     * 查询总条数
     */
    Integer getSchoolOrgAllCount();

    /**
     * 批量删除方法
     */
    Integer delSchoolOrgInIds(@Param("ids") List<Long> ids);

    /**
     * 根据条件查询所有的学校组织架构
     *
     * @return List<SchoolOrg>
     */
    List<SchoolOrg> findAllSchoolOrgInfo(Map<String, Object> map);

    /**
     * 根据条件查询单个学校组织架构
     *
     * @return SchoolOrg
     */
    SchoolOrg findSchoolOrgInfo(@Param("id") Long id);


    /**
     * 查询学生的组织架构信息
     *
     * @param schoolId
     * @return List<SchoolOrg>
     */
    List<SchoolOrg> findClassOrg(@Param("schoolId") Long schoolId);

    /**
     * 根据ID删除组织架构信息
     * @param id
     * @return
     */
    int removeSchoolOrgInfo(@Param("id")Long id);


    /**
     * 根据ParentId查询学校组织架构信息
     * @param parentId
     * @return
     */
    List<SchoolOrg> findBySchoolOrgByParentId(@Param("parentId")Long parentId);


    /**
     * 修改学校组织信息
     * @param schoolOrg
     * @return
     */
    int updateSchoolOrg(SchoolOrg schoolOrg);
}
