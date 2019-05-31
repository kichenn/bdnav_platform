package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.dto.DeptDto;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.persistence.DeptMapper;
import com.bdxh.system.service.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public Boolean delDept(Long id) {
        Dept deptId=deptMapper.selectByPrimaryKey(id);
        List<Dept> depts =deptMapper.selectByParentId(deptId.getId());
        if(depts != null&&!depts.isEmpty()){
            return Boolean.TRUE;
        }else{
            deptMapper.deleteByPrimaryKey(id);
            return Boolean.FALSE;

        }

    }

    @Override
    public Boolean addDept(DeptDto deptDto) {
        Dept dDo = new Dept();
        BeanUtils.copyProperties(deptDto, dDo);
        if (new Long("-1").equals(dDo.getParentId())) {
            dDo.setParentIds("");
        } else {
            Dept depts=findDeptById(dDo.getParentId());
            dDo.setParentIds(depts.getParentIds() + "," + depts.getId());
        }
        return deptMapper.insertSelective(dDo)>0;
    }


    @Override
    public Boolean modifyDept(DeptDto deptDto) {
        Dept dDo = new Dept();
        BeanUtils.copyProperties(deptDto, dDo);
        if (new Long("-1").equals(dDo.getParentId())) {
            dDo.setParentIds("");
        } else {
            Dept depts=findDeptById(dDo.getParentId());
            dDo.setParentIds(depts.getParentIds() + "," + depts.getId());
        }
        return deptMapper.updateByPrimaryKeySelective(dDo)>0;
    }


    //根据id查询权限信息
    @Override
    public Dept findDeptById(Long id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public Dept getByDeptName(String deptFullName) {
        return deptMapper.getByDeptName(deptFullName);
    }
}
