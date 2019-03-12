package com.bdxh.user.persistence;

import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.vo.FamilyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface FamilyMapper extends Mapper<Family> {

    //查询家长详细信息
    FamilyVo selectByCodeAndCard(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber);

    //删除家长信息
    int removeFamilyInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //修改家长信息
    int updateFamilyInfo(@Param("updateFamilyDto") UpdateFamilyDto updateFamilyDto);

}