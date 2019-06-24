package com.bdxh.weixiao.controller.user;

import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.feign.FamilyFenceControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private AccountControllerClient accountControllerClient;

    /**
     * 收费服务
     * 修改围栏表信息
     *
     * @param updateFamilyFenceDto
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation(value = "家长电子围栏-----修改围栏信息")
    @RequestMapping(value = "/updateFamilyFenceInfo", method = RequestMethod.POST)
    public Object updateFamilyFenceInfo(@Valid @RequestBody UpdateFamilyFenceDto updateFamilyFenceDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            Boolean isOnTrial = caseCardNumber.contains(updateFamilyFenceDto.getStudentNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            Boolean isBy = thisCardNumbers.contains(updateFamilyFenceDto.getStudentNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }

            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Account account = accountControllerClient.queryAccount(userInfo.getSchoolCode(), updateFamilyFenceDto.getStudentNumber()).getResult();
            updateFamilyFenceDto.setSchoolCode(userInfo.getSchoolCode());
            updateFamilyFenceDto.setSchoolId(userInfo.getSchoolId());
            updateFamilyFenceDto.setFamilyId(userInfo.getFamilyId());
            updateFamilyFenceDto.setCardNumber(userInfo.getFamilyCardNumber());
            Wrapper wrapper = familyFenceControllerClient.updateFamilyFenceInfo(updateFamilyFenceDto);
            return wrapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    /**
     * 收费服务
     * 删除围栏表信息
     *
     * @param id
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation(value = "家长电子围栏-----删除围栏信息")
    @RequestMapping(value = "/removeFamilyFenceInfo", method = RequestMethod.POST)
    public Object removeFamilyFenceInfo(@RequestParam("id") String id, @RequestParam("cardNumber") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Wrapper wrapper = familyFenceControllerClient.removeFamilyFenceInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), id);
            return wrapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    /**
     * 收费服务
     * 获取围栏表所有信息
     *
     * @param cardNumber
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation(value = "家长电子围栏-----获取围栏表所有信息")
    @RequestMapping(value = "/getFamilyFenceInfos", method = RequestMethod.POST)
    public Object getFamilyFenceInfos(@RequestParam("cardNumber") String cardNumber) {

        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            FamilyFenceQueryDto familyFenceQueryDto = new FamilyFenceQueryDto();
            familyFenceQueryDto.setStudentNumber(cardNumber);
            familyFenceQueryDto.setSchoolCode(userInfo.getSchoolCode());
            familyFenceQueryDto.setCardNumber(userInfo.getFamilyCardNumber());
            Wrapper wrapper = familyFenceControllerClient.getFamilyFenceInfos(familyFenceQueryDto);
            return wrapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    /**
     * 收费服务
     * 获取围栏表单个信息
     *
     * @param id
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation(value = "家长电子围栏-----获取围栏表单个信息")
    @RequestMapping(value = "/getFamilyFenceInfo", method = RequestMethod.POST)
    public Object getFamilyFenceInfo(@RequestParam("id") String id, @RequestParam("cardNumber") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Wrapper wrapper = familyFenceControllerClient.getFamilyFenceInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), id);
            return wrapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }

    /**
     * 收费应用
     * 新增围栏设置
     *
     * @param addFamilyFenceDto
     */
    @RolesAllowed({"TEST", "FENCE"})
    @ApiOperation(value = "家长电子围栏-----新增围栏设置")
    @RequestMapping(value = "/addFamilyFenceInfo", method = RequestMethod.POST)
    public Object addFamilyFenceInfo(@Valid @RequestBody AddFamilyFenceDto addFamilyFenceDto) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            Boolean isOnTrial = caseCardNumber.contains(addFamilyFenceDto.getStudentNumber());
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            Boolean isBy = thisCardNumbers.contains(addFamilyFenceDto.getStudentNumber());
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Account account = accountControllerClient.queryAccount(userInfo.getSchoolCode(), addFamilyFenceDto.getStudentNumber()).getResult();
            addFamilyFenceDto.setSchoolCode(userInfo.getSchoolCode());
            addFamilyFenceDto.setSchoolId(userInfo.getSchoolId());
            addFamilyFenceDto.setFamilyId(userInfo.getFamilyId());
            addFamilyFenceDto.setCardNumber(userInfo.getFamilyCardNumber());
            addFamilyFenceDto.setAccoountId(String.valueOf(account.getId()));
            Wrapper wrapper = familyFenceControllerClient.addFamilyFenceInfo(addFamilyFenceDto);
            return wrapper;
        } catch (Exception e) {
            String messge = "";
            if (e instanceof PermitException) {
                messge = "抱歉，您该孩子没开通围栏权限";
                return WrapMapper.notNoTrial(messge);
            }
            return WrapMapper.error(messge);
        }
    }
}