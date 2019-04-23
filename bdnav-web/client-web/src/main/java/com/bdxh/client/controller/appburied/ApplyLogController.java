package com.bdxh.client.controller.appburied;

import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/applyLog")
@Slf4j
@Validated
@Api(value = "应用上报日志信息", tags = "应用上报日志信息交互API")
public class ApplyLogController {

    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;


    @RequestMapping(value = "/addApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "增加APP上报日志信息", response = Boolean.class)
    public Object addApplyLog(@Validated @RequestBody AddApplyLogDto addApplyLogDto) {

        Wrapper wrapper=applyLogControllerClient.addApplyLog(addApplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/modifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "修改APP上报日志信息", response = Boolean.class)
    public Object modifyApplyLog(@Validated @RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        Wrapper wrapper=applyLogControllerClient.modifyApplyLog(modifyApplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/delApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "删除APP上报日志信息", response = Boolean.class)
    public Object delApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto AddapplyLogDto) {
       Wrapper wrapper= applyLogControllerClient.delApplyLogById(AddapplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/findApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP上报日志信息", response = AppStatus.class)
    public Object findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto) {
        Wrapper wrapper= applyLogControllerClient.findApplyLogById(findApplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/findApplyLogInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App状态日志查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto) {
        return WrapMapper.ok(applyLogControllerClient.findApplyLogInContionPaging(applyLogQueryDto));
    }
}