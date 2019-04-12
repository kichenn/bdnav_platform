package com.bdxh.user.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.persistence.FamilyFenceMapper;
import com.bdxh.user.service.FamilyFenceService;
import com.bdxh.user.vo.FamilyFenceVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 家长围栏service实现
 * @author: xuyuan
 * @create: 2019-04-10 16:59
 **/
@Service
public class FamilyFenceServiceImpl extends BaseService<FamilyFence> implements FamilyFenceService {
   @Autowired
   private FamilyFenceMapper familyFenceMapper;

    @Override
    public void updateFamilyFenceInfo(UpdateFamilyFenceDto updateFamilyFenceDto) {
        FamilyFence familyFence = BeanMapUtils.map(updateFamilyFenceDto, FamilyFence.class);
        familyFenceMapper.updateFamilyFenceInfo(familyFence);
    }

    @Override
    public void removeFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        familyFenceMapper.removeFamilyFenceInfo(schoolCode,cardNumber,id);
    }

    @Override
    public PageInfo<FamilyFenceVo> getFamilyFenceInfos(FamilyFenceQueryDto familyFenceQueryDto) {
        PageHelper.startPage(familyFenceQueryDto.getPageNum(), familyFenceQueryDto.getPageSize());
        FamilyFence familyFence = BeanMapUtils.map(familyFenceQueryDto, FamilyFence.class);
        List<FamilyFenceVo> listFamilyFence = familyFenceMapper.getFamilyFenceInfos(familyFence);
        PageInfo<FamilyFenceVo> pageInfoFamily = new PageInfo<>(listFamilyFence);
        return pageInfoFamily;
    }

    @Override
    public FamilyFence getFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        return familyFenceMapper.getFamilyFenceInfo(schoolCode,cardNumber,id);
    }

    @Override
    public void addFamilyFenceInfo(AddFamilyFenceDto addFamilyFenceDto) {
        FamilyFence familyFence = BeanMapUtils.map(addFamilyFenceDto, FamilyFence.class);
        familyFenceMapper.insert(familyFence);
    }
}
