package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@RequestMapping("/fenceAlarm")
@Validated
@Slf4j
@Api(value = "电子围栏-----微校学生出入围栏日志API", tags = "电子围栏-----微校学生出入围栏日志API")
public class FenceAlarmWebController {
    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    /**
     * 查询所有
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation("电子围栏-----查询所有围栏警报接口")
    @RequestMapping(value = "/getAllFenceAlarmInfos",method = RequestMethod.POST)
    public Object getAllFenceAlarmInfos(@RequestParam("schoolCode")String schoolCode,@RequestParam("cardNumber")String cardNumber){
        try {
            FenceAlarmQueryDto fenceAlarmQueryDto=new FenceAlarmQueryDto();
            fenceAlarmQueryDto.setSchoolCode(schoolCode);
            fenceAlarmQueryDto.setCardNumber(cardNumber);
            return WrapMapper.ok(fenceAlarmControllerClient.getAllFenceAlarmInfos(fenceAlarmQueryDto).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation("电子围栏-----查询单个围栏警报接口")
    @RequestMapping(value="/getFenceAlarmInfo",method = RequestMethod.POST)
    public Object getFenceAlarmInfo(
            @RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空")  String schoolCode,
            @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
            @RequestParam(name="id") @NotNull(message = "id不能为空")  String id){
        try {
            return WrapMapper.ok(fenceAlarmControllerClient.getFenceAlarmInfo(schoolCode,cardNumber,id).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}