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
     PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto);

    void deleteFamilyByKeys(String id[]);

    void deleteFamilyByKey(String id);

}
