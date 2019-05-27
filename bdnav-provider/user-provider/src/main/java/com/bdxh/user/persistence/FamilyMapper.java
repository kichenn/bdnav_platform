package com.bdxh.user.persistence;

import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.vo.FamilyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface FamilyMapper extends Mapper<Family> {

    //查询家长详细信息
    FamilyVo selectByCodeAndCard(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber);

    //查询家长信息
    Family findFamilyInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //删除家长信息
    int removeFamilyInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //修改家长信息
    int updateFamilyInfo(@Param("family") Family family);

    //查询所有家长信息
    List<Family> selectAllFamilyInfo(@Param("familyQueryDto")FamilyQueryDto familyQueryDto);

    //批量删除家长信息
    int batchRemoveFamilyInfo(@Param("list") List<Map<String,String>> list);

    //批量新增家长信息
    int batchSaveFamilyInfo(List<Family> familyList);

    //根据学校Code查询卡号
    List<String> queryFamilyCardNumberBySchoolCode(@Param("schoolCode")String schoolCode);

    //修改学校名字
    int updateSchoolName(@Param("schoolCode")String schoolCode, @Param("schoolName")String schoolName);


}