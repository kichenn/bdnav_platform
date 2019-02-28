package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.entity.Role;
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
    //根据条件查询列表
    PageInfo<DictData> findListPage(Map<String,Object> param, int pageNum, int pageSize);
}
