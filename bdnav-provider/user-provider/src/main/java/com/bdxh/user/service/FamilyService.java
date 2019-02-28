package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 家长信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:41
 **/
public interface FamilyService extends IService<Family> {
    //根据条件分页查询家长信息
     PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto);

    //根据id批量删除家长信息以及学生家长绑定信息
    void deleteBatchesFamilyInfo(String id[]);

    //根据id删除家长信息以及学生家长绑定信息
    void deleteFamilyInfo(String id);

}
