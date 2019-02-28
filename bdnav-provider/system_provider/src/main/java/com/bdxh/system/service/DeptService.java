package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Dept;
import com.github.pagehelper.PageInfo;

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
}
