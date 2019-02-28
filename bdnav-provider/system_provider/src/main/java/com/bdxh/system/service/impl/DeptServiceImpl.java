package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.entity.Dept;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.persistence.DeptMapper;
import com.bdxh.system.service.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

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
}
