package com.bdxh.weixiao.controller.trajectory;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.TrajectoryControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
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

import javax.validation.constraints.NotNull;

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

    /**
     * 家长端鹰眼轨迹------查询单个孩子的轨迹信息
     *
     * @param startTime  开始时间       只限查询24个小时内的轨迹点
     * @param endTime    结束时间
     * @param cardNumber 学生卡号
     * @return
     */
    @RequestMapping(value = "/findTrajectoryInfo", method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的轨迹信息")
    public Object findTrajectoryInfo(@RequestParam("startTime") @NotNull(message = "开始时间不能为空") String startTime,
                                     @RequestParam("endTime") @NotNull(message = "结束时间不能为空") String endTime,
                                     @RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            return trajectoryControllerClient.findTrajectoryInfo(startTime, endTime, userInfo.getSchoolCode(), cardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

    /**
     * 家长端鹰眼轨迹------查询单个孩子的实时位置信息
     *
     * @param cardNumber 学生卡号
     *                   用来拼接ENTITY 查询entity的轨迹点
     * @return
     */
    @RequestMapping(value = "/findLatestPoint", method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的实时位置信息")
    public Object findLatestPoint(@RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        try {
            return trajectoryControllerClient.findLatestPoint(userInfo.getSchoolCode(), cardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }
}