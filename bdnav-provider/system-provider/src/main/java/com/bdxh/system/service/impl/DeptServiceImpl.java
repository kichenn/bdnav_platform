package com.bdxh.system.service.impl;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.support.BaseService;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.persistence.DeptMapper;
import com.bdxh.system.service.DeptService;
import com.bdxh.system.vo.DeptDetailsVo;
import com.bdxh.system.vo.DeptVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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



    //部门id查询等级节点列表（一级节点为父节点）
    @Override
    public List<Dept> findParentDeptById(Long deptId) {

        return deptMapper.select(new Dept());
    }

    @Override
    public Dept findDeptByParentId(Long id, Long parentId) {
        return deptMapper.findDeptByParentId(id,parentId);
    }

 /* @Override
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
    }*/


    @Override
    public Boolean delDept(Long id) {
        Dept deptId=deptMapper.selectByPrimaryKey(id);
        List<Dept> depts =deptMapper.selectByParentId(deptId.getId());
        if(depts != null&&!depts.isEmpty()){
            return Boolean.TRUE;
           /*     for (Dept s : depts) {
                        deptMapper.deleteByPrimaryKey(id);
                        Dept delDept=new Dept();
                        delDept.setId(s.getId());
                        deptMapper.delete(delDept);
                }*/
        }else{
            deptMapper.deleteByPrimaryKey(id);
            return Boolean.FALSE;

        }

    }



}
