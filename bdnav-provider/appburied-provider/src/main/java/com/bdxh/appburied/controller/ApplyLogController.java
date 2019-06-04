package com.bdxh.appburied.controller;

import com.bdxh.appburied.dto.*;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.appburied.service.ApplyLogService;

import java.util.Date;

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
    private ApplyLogService applyLogService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @RequestMapping(value = "/addApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "增加APP上报日志信息", response = Boolean.class)
    public Object addApplyLog(@Validated @RequestBody AddApplyLogDto addApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(addApplyLogDto, applyLog);
        //设置状态值
        applyLog.setPlatform(addApplyLogDto.getInstallAppsPlatformEnum().getKey());
        applyLog.setModel(addApplyLogDto.getApplyLogModelEnum().getKey());
        applyLog.setOperatorStatus(addApplyLogDto.getApplyLogOperatorStatusEnum().getKey());
        //设置id
        applyLog.setId(snowflakeIdWorker.nextId());
        Boolean result=applyLogService.save(applyLog) > 0;
        return WrapMapper.ok(result);
    }

    @RequestMapping(value = "/modifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "修改APP上报日志信息", response = Boolean.class)
    public Object modifyApplyLog(@Validated @RequestBody ModifyApplyLogDto modifyApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(modifyApplyLogDto, applyLog);
        //设置状态值
        if (modifyApplyLogDto.getInstallAppsPlatformEnum() != null) {
            applyLog.setPlatform(modifyApplyLogDto.getInstallAppsPlatformEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogModelEnum() != null) {
            applyLog.setModel(modifyApplyLogDto.getApplyLogModelEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogOperatorStatusEnum() != null) {
            applyLog.setOperatorStatus(modifyApplyLogDto.getApplyLogOperatorStatusEnum().getKey());
        }
        applyLog.setUpdateDate(new Date());
        return WrapMapper.ok(applyLogService.update(applyLog) > 0);
    }

    @RequestMapping(value = "/delApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "删除APP上报日志信息", response = Boolean.class)
    public Object delApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto AddapplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(AddapplyLogDto, applyLog);
        return WrapMapper.ok(applyLogService.delete(applyLog) > 0);
    }

    @RequestMapping(value = "/findApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP上报日志信息", response = AppStatus.class)
    public Object findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(findApplyLogDto, applyLog);
        return WrapMapper.ok(applyLogService.select(applyLog));
    }

    @RequestMapping(value = "/findApplyLogInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App状态日志查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto) {
        return WrapMapper.ok(applyLogService.findApplyLogInConationPaging(applyLogQueryDto));
    }

    @RequestMapping(value = "/familyFindApplyLogInfo", method = RequestMethod.GET)
    @ApiOperation(value = "家长查询自己孩子的App申请信息")
    public Object familyFindApplyLogInfo(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        try {
            return WrapMapper.ok(applyLogService.familyFindApplyLogInfo(schoolCode, cardNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }
    @RequestMapping(value = "/modifyVerifyApplyLog", method = RequestMethod.POST)
    @ApiOperation(value = "家长审批自己孩子的App申请信息")
    public Object modifyVerifyApplyLog(@RequestBody  ModifyApplyLogDto modifyApplyLogDto) {
        try {
            applyLogService.modifyVerifyApplyLog(modifyApplyLogDto);
            return WrapMapper.ok("审批完成");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error();
        }
    }

}