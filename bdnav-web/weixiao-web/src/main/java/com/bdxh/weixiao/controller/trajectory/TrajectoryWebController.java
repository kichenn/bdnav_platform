package com.bdxh.weixiao.controller.trajectory;

import com.bdxh.account.entity.Account;
import com.bdxh.account.feign.AccountControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.TrajectoryControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-23 17:16
 **/
@Slf4j
@RequestMapping(value = "/trajectoryWeb")
@RestController
@Api(value = "位置管理----鹰眼轨迹", tags = "位置管理----鹰眼轨迹")
@Validated
public class TrajectoryWebController {

    @Autowired
    private TrajectoryControllerClient trajectoryControllerClient;

    @Autowired
    private AccountControllerClient accountControllerClient;

    /**
     * 家长端鹰眼轨迹------查询单个孩子的轨迹信息
     *
     * @param startTime  开始时间       只限查询24个小时内的轨迹点
     * @param endTime    结束时间
     * @param cardNumber 学生卡号
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @RequestMapping(value = "/findTrajectoryInfo", method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的轨迹信息")
    public Object findTrajectoryInfo(@RequestParam("startTime") @NotNull(message = "开始时间不能为空") String startTime,
                                     @RequestParam("endTime") @NotNull(message = "结束时间不能为空") String endTime,
                                     @RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber=caseCardNumber==null ? new ArrayList<>() :caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            thisCardNumbers=thisCardNumbers==null ? new ArrayList<>() :thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Account account = accountControllerClient.queryAccount(userInfo.getSchoolCode(), cardNumber).getResult();
            return trajectoryControllerClient.findTrajectoryInfo(startTime, endTime, String.valueOf(account.getId()));
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
     * 家长端鹰眼轨迹------查询单个孩子的实时位置信息
     *
     * @param cardNumber 学生卡号
     *                   用来拼接ENTITY 查询entity的轨迹点
     * @return
     */
    @RolesAllowed({"TEST", "FENCE"})
    @RequestMapping(value = "/findLatestPoint", method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的实时位置信息")
    public Object findLatestPoint(@RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        try {
            //查看此孩子是否开通权限
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取试用孩子列表信息
            List<String> caseCardNumber = mapAuthorities.get("ROLE_TEST");
            caseCardNumber=caseCardNumber==null ? new ArrayList<>() :caseCardNumber;
            Boolean isOnTrial = caseCardNumber.contains(cardNumber);
            //获取正式购买孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_FENCE");
            thisCardNumbers=thisCardNumbers==null ? new ArrayList<>() :thisCardNumbers;
            Boolean isBy = thisCardNumbers.contains(cardNumber);
            if (!(isBy || isOnTrial)) {
                throw new PermitException();
            }
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            Account account = accountControllerClient.queryAccount(userInfo.getSchoolCode(), cardNumber).getResult();
            return trajectoryControllerClient.findLatestPoint(String.valueOf(account.getId()));
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