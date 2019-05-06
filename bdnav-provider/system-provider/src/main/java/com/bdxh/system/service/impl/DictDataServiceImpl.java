package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.persistence.DictDataMapper;
import com.bdxh.system.service.DictDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 字典管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 17:01
 **/
@Service
@Slf4j
public class DictDataServiceImpl extends BaseService<DictData> implements DictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBatchDictData(List<Long> dictDataIds) {
        if (dictDataIds!=null&&!dictDataIds.isEmpty()){
            for (int i=0;i<dictDataIds.size();i++){
                Long dictDataId = dictDataIds.get(i);
                dictDataMapper.deleteByPrimaryKey(dictDataId);
            }
        }

    }

    @Override
    public List<DictData> queryList(Map<String, Object> param) {
        return dictDataMapper.getByCondition(param);
    }

    @Override
    public PageInfo<DictData> findListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<DictData> dictDataLogs= dictDataMapper.getByCondition(param);
        return new PageInfo(dictDataLogs);
    }


    @Override
    public PageInfo<DictData> findDictListBydictId(Long dictId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<DictData> dictDatas =dictDataMapper.getDictDataById(dictId);
        return new PageInfo(dictDatas);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictDataById(Long id) {
      dictDataMapper.deleteDictDataById(id);
    }

    @Override
    public DictData getByDictDataName(Long dictId, String dataName) {
         return dictDataMapper.getByDictDataName(dictId,dataName);
    }


    @Override
    public List<DictData> getDictDataByIdList(Long dictId) {
        return dictDataMapper.getDictDataById(dictId);
    }


}
