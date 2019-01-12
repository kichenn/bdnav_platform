package com.bdxh.web.wechatpay.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.XianAddBlanceDto;
import com.bdxh.onecard.dto.XianQueryBlanceDto;
import com.bdxh.onecard.dto.XianQueryConsListDto;
import com.bdxh.onecard.dto.XianSyscDataDto;
import com.bdxh.onecard.feigin.XianCardControllerClient;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.stream.Collectors;

/**
 * @description: 测试
 * @author: xuyuan
 * @create: 2019-01-12 10:06
 **/
@Controller
@RequestMapping("/test")
@Slf4j
@Validated
public class TestController {

    @Autowired
    private XianCardControllerClient xianCardControllerClient;

    /**
     * 一卡通身份验证余额查询接口
     *
     * @return
     */
    @RequestMapping(value = "/syscUser", method = RequestMethod.POST)
    @ResponseBody
    public Object syscUser(@Valid XianSyscDataDto xianSyscDataDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = xianCardControllerClient.syscUser(xianSyscDataDto);
            Preconditions.checkArgument(wrapper.getCode() == 200, wrapper.getMessage());
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 一卡通身份验证余额查询接口
     *
     * @return
     */
    @RequestMapping(value = "/queryBalance", method = RequestMethod.POST)
    @ResponseBody
    public Object queryBalance(@Valid XianQueryBlanceDto xianQueryBlanceDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Thread.sleep(2000);
            Wrapper wrapper = xianCardControllerClient.queryBalance(xianQueryBlanceDto);
            Preconditions.checkArgument(wrapper.getCode() == 200, wrapper.getMessage());
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 一卡通充值接口
     */
    @RequestMapping("/addBalance")
    @ResponseBody
    public Object addBalance(@Valid XianAddBlanceDto xianAddBlanceDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = xianCardControllerClient.addBalance(xianAddBlanceDto);
            Preconditions.checkArgument(wrapper.getCode() == 200, wrapper.getMessage());
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 消费记录查询
     */
    @RequestMapping(value = "/queryTradeList", method = RequestMethod.POST)
    @ResponseBody
    public Object queryTradeList(@Valid XianQueryConsListDto xianQueryConsListDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = xianCardControllerClient.queryTradeList(xianQueryConsListDto);
            Preconditions.checkArgument(wrapper.getCode() == 200, wrapper.getMessage());
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 充值结果查询
     */
    @RequestMapping(value = "/queryAddResult", method = RequestMethod.POST)
    @ResponseBody
    public Object queryAddResult(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                 @RequestParam(name = "orderNo") @NotEmpty(message = "订单号不能为空") String orderNo) {
        try {
            Wrapper wrapper = xianCardControllerClient.queryAddResult(schoolCode, orderNo);
            Preconditions.checkArgument(wrapper.getCode() == 200, wrapper.getMessage());
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
