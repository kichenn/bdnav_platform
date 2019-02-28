package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Dict;
import com.github.pagehelper.PageInfo;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.List;
import java.util.Map;

/**
 * @description: 字典类型管理service
 * @author: xuyuan
 * @create: 2019-02-22 16:50
 **/
public interface DictService extends IService<Dict> {
    //列表查询
    List<Dict> queryList(Map<String,Object> param);
    //根据条件查询列表
    PageInfo<Dict> findListPage(Map<String,Object> param, int pageNum, int pageSize);
    //删除字典
    void delDict(Long DictId);

}
