package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.vo.DeptVo;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.persistence.DeptMapper;
import com.bdxh.system.service.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 部门管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 16:45
 **/
@Service
@Slf4j
public class DeptServiceImpl extends BaseService<Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> queryList(Map<String, Object> param) {
        return deptMapper.getByCondition(param);
    }

    @Override
    public PageInfo<Dept> findListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dept> DeptLogs = deptMapper.getByCondition(param);
        PageInfo<Dept> pageInfo=new PageInfo<>(DeptLogs);
        return pageInfo;
    }

    //递归查询关系节点
    @Override
    public List<DeptVo> findDeptRelation(DeptVo deptVo) {
        List<DeptVo> deptVos = new ArrayList<>();
        if (deptVo.getId()!=null&&deptVo.getId()!=0) {
            deptVos.addAll(findDeptByParentId(deptVo.getId()));
            if (CollectionUtils.isNotEmpty(deptVos)) {
                deptVos.stream().forEach(e -> {
                    if (e.getId()!=null&&e.getId()!=0) {
                        e.setDeptVos(findDeptRelation(e));
                    }
                });
            }
        }
        return deptVos;
    }

    //部门id查询等级节点列表（一级节点为父节点）
    @Override
    public List<Dept> findParentDeptById(Long deptId, Byte level) {
        Dept dept=new Dept();
        dept.setLevel(level);
        return deptMapper.select(dept);
    }

    //根据父级部门id查询部门信息
    @Override
    public List<DeptVo> findDeptByParentId(Long parentId) {
        List<DeptVo> deptVos = new ArrayList<>();
        List<Dept> depts = deptMapper.findDeptByParentId(parentId);
        if (CollectionUtils.isNotEmpty(depts)) {
            depts.stream().forEach(e -> {
                DeptVo deptVo = new DeptVo();
                BeanUtils.copyProperties(e, deptVo);
                deptVos.add(deptVo);
            });
        }
        return deptVos;
    }
}
