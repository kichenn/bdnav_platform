package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dept;
import com.bdxh.system.vo.DeptDetailsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface DeptMapper extends Mapper<Dept> {

    //根据条件字典数据
    List<Dept> getByCondition(Map<String,Object> param);

    //parentId查询部门关系
    Dept findDeptByParentId(@Param("id") Long id,@Param("parentId") Long parentId);

    //根据父id查询所有子集合
    List<Dept> selectByParentId(@Param("parentId") Long parentId);


    //根据部门名称查询当前部门
    Dept getByDeptName(@Param("deptFullName") String deptFullName);





}