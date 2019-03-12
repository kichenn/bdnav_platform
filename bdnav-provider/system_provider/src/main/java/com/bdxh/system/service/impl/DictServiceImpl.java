package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.persistence.DictDataMapper;
import com.bdxh.system.persistence.DictMapper;
import com.bdxh.system.service.DictService;
import com.bdxh.system.vo.DeptVo;
import com.bdxh.system.vo.DictVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 字典类型管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 16:50
 **/
@Service
@Slf4j
public class DictServiceImpl extends BaseService<Dict> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictDataMapper dictDataMapper;


    @Override
    public List<Dict> queryList(Map<String, Object> param) {
        return dictMapper.getByCondition(param);
    }

    @Override
    public PageInfo<Dict> findListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dict> dictLogs = dictMapper.getByCondition(param);
        PageInfo<Dict> pageInfo=new PageInfo<>(dictLogs);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delDict(Long DictId) {
        List<DictData> DictDataList=dictDataMapper.getDictDataById(DictId);
        for (int i = 0; i <DictDataList.size() ; i++) {
          DictData dictData=DictDataList.get(i);
            dictDataMapper.delete(dictData);
        }

    }

    @Override
    public List<Dict> findDictListAll() {
        return dictMapper.selectAll();
    }

    //分页筛选条件查询字典列表
    @Override
    public PageInfo<Dict> findDictsInConditionPaging(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dict> dicts =dictMapper.selectAll();
        return new PageInfo(dicts);
    }





}
