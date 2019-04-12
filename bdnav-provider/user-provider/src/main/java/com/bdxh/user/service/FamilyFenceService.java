package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.vo.FamilyFenceVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 家长围栏service
 * @author: xuyuan
 * @create: 2019-04-10 16:53
 **/
public interface FamilyFenceService extends IService<FamilyFence> {

    /**
     * 修改围栏表信息
     * @param updateFamilyFenceDto
     * @return
     */
    void updateFamilyFenceInfo(UpdateFamilyFenceDto updateFamilyFenceDto);

    /**
     *  删除围栏表信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    void removeFamilyFenceInfo(String schoolCode,String cardNumber,String id);

    /**
     * 获取围栏表所有信息
     * @param familyFenceQueryDto
     * @return
     */
    PageInfo<FamilyFenceVo> getFamilyFenceInfos(FamilyFenceQueryDto familyFenceQueryDto);

    /**
     * 获取围栏表单个信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    FamilyFence getFamilyFenceInfo(String schoolCode,String cardNumber,String id);

    /**
     * 新增围栏设置
     * @param addFamilyFenceDto
     */
    void addFamilyFenceInfo(AddFamilyFenceDto addFamilyFenceDto);
}
