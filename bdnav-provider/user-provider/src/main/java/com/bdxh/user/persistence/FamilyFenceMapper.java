package com.bdxh.user.persistence;

import com.bdxh.user.entity.FamilyFence;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyFenceMapper extends Mapper<FamilyFence> {
    /**
     * 修改围栏表信息
     * @param familyFence
     * @return
     */
    int updateFamilyFenceInfo(@Param("familyFence")FamilyFence familyFence);

    /**
     * 删除围栏表信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    int removeFamilyFenceInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")String id);

    /**
     * 获取围栏表所有信息
     * @param familyFence
     * @return
     */
    List<FamilyFence> getFamilyFenceInfos(@Param("familyFence") FamilyFence familyFence);

    /**
     * 获取围栏表单个信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    FamilyFence getFamilyFenceInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")String id);

}