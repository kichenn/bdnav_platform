package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddVisitLogsDto;
import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.feign.VisitLogsControllerClient;
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
 * @description:
 * @author: binzh
 * @create: 2019-04-19 16:17
 **/
@RestController
@RequestMapping("/visitLogsWeb")
@Slf4j
@Validated
@Api(value = "学生网站访问日志控制器", tags = "学生网站访问日志控制器")
public class VisitLogsWebController {
    @Autowired
    private VisitLogsControllerClient visitLogsControllerClient;

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
            PageInfo<VisitLogsVo> visitLogsPageInfo = visitLogsControllerClient.getVisitLogsInfos(visitLogsQueryDto).getResult();
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
    @ApiOperation(value = "查询单个浏览网站日志接口",response = VisitLogsVo.class)
    @RequestMapping(value = "/getVisitLogsInfo", method = RequestMethod.POST)
    public Object getVisitLogsInfo(@RequestParam(name = "schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "cardNumber不能为空") String cardNumber,
                                   @RequestParam(name = "id") @NotNull(message = "id不能为空") String id) {
        try {
            return visitLogsControllerClient.getVisitLogsInfo(schoolCode, cardNumber, id);
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
            return visitLogsControllerClient.removeVisitLogsInfo(id,schoolCode,cardNumber );
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
            return visitLogsControllerClient.batchRemoveVisitLogsInfo(ids, schoolCodes, cardNumbers);
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
            return visitLogsControllerClient.updateVisitLogsInfo(updateVisitLogsDto);
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
            return visitLogsControllerClient.insertVisitLogsInfo(addVisitLogsDto);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}