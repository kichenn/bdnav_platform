package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
     * @Description： //批量删除家长信息以及学生家长绑定信息
     * @Date： 17:51 2019/3/4
     * @Param： [schoolCode, cardNumber]
     * @return： void
     **/
    void deleteBatchesFamilyInfo(String schoolCode,String cardNumber);
    /**
     * @Author： binzh
     * @Description： //删除家长信息以及学生家长绑定信息
     * @Date： 12:21 2019/3/7
     * @Param： [schoolCode, cardNumber]
     * @return： void
     **/
    void deleteFamilyInfo(String schoolCode,String cardNumber);

     /**
      * @Author： binzh
      * @Description： //根据家长的ID查询出家长信息以及绑定的孩子信息
      * @Date： 19:28 2019/3/7
      * @Param： [schoolCode, cardNumber]
      * @return： com.bdxh.user.vo.FamilyVo
      **/
     FamilyVo selectBysCodeAndCard(String schoolCode,String cardNumber);

     /**
      * @Author： binzh
      * @Description： //修改家长信息
      * @Date： 15:15 2019/3/8
      * @Param： [familyDto]
      * @return： void
      **/
     void updateFamily(UpdateFamilyDto updateFamilyDto);

     /**
      * @Author： binzh
      * @Description： //查询是否有相同卡号家长
      * @Date： 17:42 2019/3/8
      * @Param： [schoolCode, cardNumber]
      * @return： com.bdxh.user.vo.FamilyVo
      **/
     FamilyVo isNullFamily(String schoolCode,String cardNumber);

    /**
     * 新增家长信息
     * @param family
     */
     void saveFamily(Family family);

    /**
     * 批量新增家长
     * @param familyList
     * @return
     */
    void batchSaveFamilyInfo(List<AddFamilyDto> familyList);

 /**
  * 根据学校Code查询所有卡号
  * @param schoolCode
  * @return
  */
   List<String> queryFamilyCardNumberBySchoolCode( String schoolCode);

    /**
     * 修改学校名字
     * @param schoolCode
     * @param schoolName
     */
    void updateSchoolName(String schoolCode,String schoolName);


}
