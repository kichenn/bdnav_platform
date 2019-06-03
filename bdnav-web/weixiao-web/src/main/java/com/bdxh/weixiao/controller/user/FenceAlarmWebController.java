package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@RequestMapping("/fenceAlarmWeb")
@Validated
@Slf4j
@Api(value = "电子围栏-----微校学生出入围栏日志API", tags = "电子围栏-----微校学生出入围栏日志API")
public class FenceAlarmWebController {
    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;
    /**
     * 收费服务
     * 查询所有
     * @param schoolCode
     * @param cardNumber 学生学号
     * @return
     */
    @ApiOperation("家长电子围栏-----查询所有围栏警报接口")
    @RequestMapping(value = "/getAllFenceAlarmInfos",method = RequestMethod.POST)
    public Object getAllFenceAlarmInfos(@RequestParam("schoolCode")String schoolCode,
                                        @RequestParam("cardNumber")String cardNumber,
                                        @RequestParam("fenceId")String fenceId){
        try {
            return fenceAlarmControllerClient.getFenceAlarmInfos(schoolCode,cardNumber,fenceId);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 收费服务
     * 查询单个
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation("家长电子围栏-----查询单个围栏警报接口")
    @RequestMapping(value="/getFenceAlarmInfo",method = RequestMethod.POST)
    public Object getFenceAlarmInfo(
            @RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空")  String schoolCode,
            @RequestParam(name="cardNumber")@NotNull(message = "学生cardNumber不能为空")  String cardNumber,
            @RequestParam(name="id") @NotNull(message = "id不能为空")  String id){
        try {
            return WrapMapper.ok(fenceAlarmControllerClient.getFenceAlarmInfo(schoolCode,cardNumber,id).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}