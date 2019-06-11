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
    void removeFamilyStudentInfo(String schoolCode, String cardNumber, String id);

    /**
     * 查询学生家长关系数据
     *
     * @param familyStudentQueryDto
     * @return
     * @Author：bin
     */
    PageInfo<FamilyStudentVo> queryAllFamilyStudent(FamilyStudentQueryDto familyStudentQueryDto);


    /**
     * @Description: 学生卡号 +学校code 查询绑定关系
     * @Author: Kang
     * @Date: 2019/6/1 10:13
     */
    FamilyStudentVo studentQueryInfo(String schoolCode, String cardNumber);

    /**
     * 家长卡号 +学校code 查询绑定关系信息
     * @param familyCardNumber
     * @return
     */
    List<FamilyStudentVo> queryStudentByFamilyCardNumber(String schoolCode,String familyCardNumber);

}
