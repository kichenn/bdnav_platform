package com.bdxh.user.persistence;

import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.vo.FamilyFenceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyFenceMapper extends Mapper<FamilyFence> {

    //修改围栏表信息
    int updateFamilyFenceInfo(@Param("familyFence")FamilyFence familyFence);

    //删除围栏表信息
    int removeFamilyFenceInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")String id);

    // 获取围栏表所有信息
    List<FamilyFenceVo> getFamilyFenceInfos(@Param("familyFence") FamilyFence familyFence);

    //获取围栏表单个信息
    FamilyFence getFamilyFenceInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")String id);

    //获取单个孩子的所有围栏Id
    List<Integer> findOneStudentFenceId(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("studentNumber")String studentNumber);
}