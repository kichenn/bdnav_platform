package com.bdxh.weixiao.controller.trajectory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/findTrajectoryInfo")
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的轨迹信息")
    public Object findTrajectoryInfo(@RequestParam("startTime")@NotNull(message = "开始时间不能为空")String startTime,
                                     @RequestParam("endTime")@NotNull(message = "结束时间不能为空")String endTime,
                                     @RequestParam("schoolCode")@NotNull(message = "学校Code不能为空")String schoolCode,
                                     @RequestParam("cardNumber")@NotNull(message = "学生卡号不能为空")String cardNumber){

        return null;
    }
}