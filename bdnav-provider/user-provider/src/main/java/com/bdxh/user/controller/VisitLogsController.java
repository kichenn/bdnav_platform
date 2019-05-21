package com.bdxh.user.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.service.VisitLogsService;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @Description: 学生网站访问日志控制器
 * @Author Kang
 * @Date 2019-04-17 17:29:24
 */
@RestController
@RequestMapping("/visitLogs")
@Slf4j
@Validated
@Api(value = "学生网站访问日志控制器", tags = "学生网站访问日志控制器")
public class VisitLogsController {

    @Autowired
    private VisitLogsService visitLogsService;

    /**
     * 查询所有浏览网站日志接口
     *
     * @param visitLogsQueryDto
     * @return
     */
    @ApiOperation("查询所有浏览网站日志接口")
    @RequestMapping(value = "/getVisitLogsInfos", method = RequestMethod.POST)
    public Object getVisitLogsInfos(@Valid @RequestBody VisitLogsQueryDto visitLogsQueryDto, BindingResult bindingResult) {
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            PageInfo<VisitLogsVo> visitLogsPageInfo = visitLogsService.getVisitLogsInfos(visitLogsQueryDto);
            log.info(visitLogsPageInfo.toString());
            return WrapMapper.ok(visitLogsPageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个浏览网站日志接口
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation("查询单个浏览网站日志接口")
    @RequestMapping(value = "/getVisitLogsInfo", method = RequestMethod.POST)
    public Object getVisitLogsInfo(@RequestParam(name = "schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "cardNumber不能为空") String cardNumber,
                                   @RequestParam(name = "id") @NotNull(message = "id不能为空") String id) {
        try {
            return WrapMapper.ok(visitLogsService.getVisitLogsInfo(schoolCode, cardNumber, id));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除单个浏览网站日志接口
     *
     * @param id
     * @param schoolCode
     * @param cardNumber
     */
    @ApiOperation("删除单个浏览网站日志接口")
    @RequestMapping(value = "/removeVisitLogsInfo", method = RequestMethod.GET)
    public Object removeVisitLogsInfo(@RequestParam("id") String id, @RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        try {
            visitLogsService.removeVisitLogsInfo(schoolCode, cardNumber, id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除浏览网站日志接口
     *
     * @param ids
     * @param schoolCodes
     * @param cardNumbers
     */
    @ApiOperation("批量删除浏览网站日志接口")
    @RequestMapping(value = "/batchRemoveVisitLogsInfo", method = RequestMethod.POST)
    public Object batchRemoveVisitLogsInfo(@RequestParam("ids") String ids, @RequestParam("schoolCodes") String schoolCodes, @RequestParam("cardNumbers") String cardNumbers) {
        try {
            visitLogsService.batchRemoveVisitLogsInfo(ids, schoolCodes, cardNumbers);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改浏览网站日志接口
     *
     * @param updateVisitLogsDto
     */
    @ApiOperation("修改浏览网站日志接口")
    @RequestMapping(value = "/updateVisitLogsInfo", method = RequestMethod.POST)
    public Object updateVisitLogsInfo(@Valid @RequestBody UpdateVisitLogsDto updateVisitLogsDto, BindingResult bindingResult) {
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            visitLogsService.updateVisitLogsInfo(updateVisitLogsDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增围栏警报接口
     *
     * @param addVisitLogsDto
     */
    @ApiOperation("新增浏览网站日志接口")
    @RequestMapping(value = "/insertVisitLogsInfo", method = RequestMethod.POST)
    public Object insertVisitLogsInfo(@Valid @RequestBody AddVisitLogsDto addVisitLogsDto, BindingResult bindingResult) {
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            visitLogsService.insertVisitLogsInfo(addVisitLogsDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 微校平台--------查询单个孩子的浏览器访问日志
     *
     * @param schoolCode
     * @param cardNumber 学生学号
     * @return
     */
    @ApiOperation("查询单个孩子浏览网站日志接口")
    @RequestMapping(value = "/queryVisitLogByCardNumber", method = RequestMethod.POST)
    public Object queryVisitLogByCardNumber(@RequestParam(name = "schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                                            @RequestParam(name = "cardNumber") @NotNull(message = "cardNumber不能为空") String cardNumber) {
        try {
            PageInfo<VisitLogsVo> visitLogsVoPageInfo = visitLogsService.queryVisitLogByCardNumber(schoolCode, cardNumber);
            return WrapMapper.ok(visitLogsVoPageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}