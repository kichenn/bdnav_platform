package com.bdxh.user.persistence;

import com.bdxh.user.dto.FamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.vo.FamilyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyMapper extends Mapper<Family> {

    //查询家长详细信息
    FamilyVo selectByCodeAndCard(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber);

    //删除家长信息
    int removeFamilyInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //修改家长信息
    int updateFamilyInfo(@Param("familyDto")FamilyDto familyDto);

}