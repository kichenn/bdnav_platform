package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.vo.FamilyVo;

import java.util.List;

/**
 * @description: 学生家长关联service
 * @author: xuyuan
 * @create: 2019-02-26 10:43
 **/
public interface FamilyStudentService extends IService<FamilyStudent> {

     /**
      * @Author： binzh
      * @Description： //删除家长学生关系表数据
      * @Date： 14:30 2019/3/7
      * @Param： [schoolCode, cardNumber]
      * @return： void
      **/
     void removeFamilyStudentInfo(String schoolCode,String cardNumber,String id);




}
