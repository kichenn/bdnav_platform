package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.DictData;
import com.github.pagehelper.PageInfo;


import java.util.List;
import java.util.Map;

/**
 * @description: 字典管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:01
 **/
public interface DictDataService extends IService<DictData> {

    //批量删除
    void delBatchDictData(List<Long> DictDataIds);

    //列表查询
    List<DictData> queryList(Map<String,Object> param);

    //根据条件分页查询列表
    PageInfo<DictData> findListPage(Map<String,Object> param, int pageNum, int pageSize);

    //根据字典id查询列表或分页查询全部数据
    PageInfo<DictData> findDictListBydictId(Long dictId,Integer pageNum, Integer pageSize);

    //根据id删除
    void deleteDictDataById(Long id);

    //根据字典数据名称查询当前数据
    DictData getByDictDataName(Long dictId,String dataName);

    //根据dictId查找字典列表
    List<DictData> getDictDataByIdList(Long dictId);
}
