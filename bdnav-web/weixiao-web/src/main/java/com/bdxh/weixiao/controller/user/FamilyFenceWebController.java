package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.feign.FamilyFenceControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@RequestMapping("/familyFenceWeb")
@Validated
@Slf4j
@Api(value = "电子围栏-----微校家长围栏API", tags = "电子围栏-----微校家长围栏API")
public class FamilyFenceWebController {
    @Autowired
    private FamilyFenceControllerClient familyFenceControllerClient;

    /**
     * 收费服务
     * 修改围栏表信息
     *
     * @param updateFamilyFenceDto
     * @return
     */
    @ApiOperation(value = "家长电子围栏-----修改围栏信息")
    @RequestMapping(value = "/updateFamilyFenceInfo", method = RequestMethod.POST)
    public Object updateFamilyFenceInfo(@Valid @RequestBody UpdateFamilyFenceDto updateFamilyFenceDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            updateFamilyFenceDto.setSchoolCode(userInfo.getSchoolCode());
            updateFamilyFenceDto.setSchoolId(userInfo.getSchoolId());
            updateFamilyFenceDto.setFamilyId(userInfo.getFamilyId());
            updateFamilyFenceDto.setCardNumber(userInfo.getFamilyCardNumber());
            Wrapper wrapper = familyFenceControllerClient.updateFamilyFenceInfo(updateFamilyFenceDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 收费服务
     * 删除围栏表信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "家长电子围栏-----删除围栏信息")
    @RequestMapping(value = "/removeFamilyFenceInfo", method = RequestMethod.POST)
    public Object removeFamilyFenceInfo(@RequestParam("id") String id) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            Wrapper wrapper = familyFenceControllerClient.removeFamilyFenceInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 收费服务
     * 获取围栏表所有信息
     *
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "家长电子围栏-----获取围栏表所有信息")
    @RequestMapping(value = "/getFamilyFenceInfos", method = RequestMethod.POST)
    public Object getFamilyFenceInfos(@RequestParam("cardNumber") String cardNumber) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            FamilyFenceQueryDto familyFenceQueryDto = new FamilyFenceQueryDto();
            familyFenceQueryDto.setStudentNumber(cardNumber);
            familyFenceQueryDto.setSchoolCode(userInfo.getSchoolCode());
            familyFenceQueryDto.setCardNumber(userInfo.getFamilyCardNumber());
            Wrapper wrapper = familyFenceControllerClient.getFamilyFenceInfos(familyFenceQueryDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 收费服务
     * 获取围栏表单个信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "家长电子围栏-----获取围栏表单个信息")
    @RequestMapping(value = "/getFamilyFenceInfo", method = RequestMethod.POST)
    public Object getFamilyFenceInfo(@RequestParam("id") String id) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            Wrapper wrapper = familyFenceControllerClient.getFamilyFenceInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 收费应用
     * 新增围栏设置
     *
     * @param addFamilyFenceDto
     */
    @ApiOperation(value = "家长电子围栏-----新增围栏设置")
    @RequestMapping(value = "/addFamilyFenceInfo", method = RequestMethod.POST)
    public Object addFamilyFenceInfo(@Valid @RequestBody AddFamilyFenceDto addFamilyFenceDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            addFamilyFenceDto.setSchoolCode(userInfo.getSchoolCode());
            addFamilyFenceDto.setSchoolId(userInfo.getSchoolId());
            addFamilyFenceDto.setFamilyId(userInfo.getFamilyId());
            addFamilyFenceDto.setCardNumber(userInfo.getFamilyCardNumber());
            Wrapper wrapper = familyFenceControllerClient.addFamilyFenceInfo(addFamilyFenceDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}