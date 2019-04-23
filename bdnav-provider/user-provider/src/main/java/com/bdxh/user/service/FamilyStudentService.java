package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.vo.FamilyStudentVo;
import com.github.pagehelper.PageInfo;

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

     /**
      * 查询学生家长关系数据
      * @param familyStudentQueryDto
      * @Author：bin
      * @return
      */
     PageInfo<FamilyStudentVo> queryAllFamilyStudent(FamilyStudentQueryDto familyStudentQueryDto);

     /**
      * 家长查询自己孩子数据
      * @param schoolCode
      * @param cardNumber
      * @Author：bin
      * @return
      */
     List<FamilyStudentVo> familyFindStudentList(String schoolCode, String cardNumber);

}
