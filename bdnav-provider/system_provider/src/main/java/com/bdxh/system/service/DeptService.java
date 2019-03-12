package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.vo.DeptDetailsVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: 部门管理service
 * @author: xuyuan
 * @create: 2019-02-22 16:45
 **/
public interface DeptService extends IService<Dept> {
    //列表查询
    List<Dept> queryList(Map<String,Object> param);

    //根据条件查询列表
    PageInfo<Dept> findListPage(Map<String,Object> param, int pageNum, int pageSize);

    //部门id查询等级节点列表（一级节点为父节点）
    List<Dept> findParentDeptById(Long deptId);

    //根据父级id查询信息
    Dept findDeptByParentId(Long id,Long parentId);

    //根据id删除部门信息
    Boolean delDept(Long id);


}
