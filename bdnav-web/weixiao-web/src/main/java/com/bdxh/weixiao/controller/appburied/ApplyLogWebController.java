package com.bdxh.weixiao.controller.appburied;

import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.configration.aspect.WeiXiaoChargeApp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-25 10:14
 **/
@Slf4j
@RequestMapping(value = "/applyLogWeb")
@RestController
@Api(value = "审批畅玩----微校审批畅玩API", tags = "审批畅玩----微校审批畅玩API")
@Validated
public class ApplyLogWebController {
    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;

    /**
     * 家长查询自己孩子的App申请信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @WeiXiaoChargeApp
    @RequestMapping(value="/familyFindApplyLogInfo",method = RequestMethod.GET)
    @ApiOperation(value = "审批畅玩----家长查询自己孩子的App申请信息")
    public Object familyFindApplyLogInfo(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber")String cardNumber){
    return applyLogControllerClient.familyFindApplyLogInfo(schoolCode,cardNumber);
    }

    /**
     * 家长审批自己孩子的App申请信息
     * @param modifyApplyLogDto
     * @return
     */
    @RequestMapping(value = "/modifyVerifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "审批畅玩----家长审批自己孩子的App申请信息")
    public Object modifyVerifyApplyLog(@RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        List<String> clientId = new ArrayList<>();
        //先给测试默认的clientId
        clientId.add("59dc219038fde0484eebcbb6d5476f0c");
        modifyApplyLogDto.setClientId(clientId);
            return applyLogControllerClient.modifyVerifyApplyLog(modifyApplyLogDto);
    }
}