package com.bdxh.user.persistence;

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

    FamilyVo selectBysCodeAndCard(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber);
}