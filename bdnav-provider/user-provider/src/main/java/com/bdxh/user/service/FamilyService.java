package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.FamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 家长信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:41
 **/
public interface FamilyService extends IService<Family> {
   /**
    * @Author： binzh
    * @Description： //根据条件分页查询家长信息
    * @Date： 17:52 2019/3/4
    * @Param： [familyQueryDto]
    * @return： com.github.pagehelper.PageInfo<com.bdxh.user.vo.FamilyVo>
    **/
     PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto);
    /**
     * @Author： binzh
     * @Description： //根据id批量删除家长信息以及学生家长绑定信息
     * @Date： 17:51 2019/3/4
     * @Param： [id]
     * @return： void
     **/

    void deleteBatchesFamilyInfo(String id[]);
    /**
     * @Author： binzh
     * @Description：  //根据id删除家长信息以及学生家长绑定信息
     * @Date： 17:51 2019/3/4
     * @Param： [id]
     * @return： void
     **/

    void deleteFamilyInfo(String id);

    /**
     * @Author： binzh
     * @Description： 修改家长信息已经学生绑定孩子信息
     * @Date： 17:50 2019/3/4
     * @Param： [familyDto]
     * @return： void
     **/
     void updateFamilyAndStudent(FamilyDto familyDto);

     /**
      * @Author： binzh
      * @Description： //新增家长信息绑定孩子
      * @Date： 18:40 2019/3/4
      * @Param： [familyDto]
      * @return： void
      **/
     void saveFamilyAndStudent(FamilyDto familyDto);

     /**
      * @Author： binzh
      * @Description： //根据家长的ID查询出家长信息以及绑定的孩子信息
      * @Date： 10:05 2019/3/5
      * @Param： [id]
      * @return： com.bdxh.user.vo.FamilyVo
      **/
     FamilyVo selectBysCodeAndCard(Long id);

}
