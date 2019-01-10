package com.bdxh.order.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.dto.OrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 订单服务控制器
 * @author: xuyuan
 * @create: 2019-01-09 15:36
 **/
@Controller
@RequestMapping("/order")
@Slf4j
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param orderDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public Object createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            OrderVo orderVo = orderService.createOrder(orderDto);
            return WrapMapper.ok(orderVo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping("/queryOrder")
    @ResponseBody
    public Object queryOrder(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                             @RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId,
                             @RequestParam(name = "orderNo") @NotNull(message = "订单号不能为空") Long orderNo){
        Map<String,Object> param = new HashMap<>();
        param.put("schoolCode",schoolCode);
        param.put("userId",userId);
        param.put("orderNo",orderNo);
        try {
            Order order = orderService.getOrderByOrderNo(param);
            return WrapMapper.ok(order);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping("/queryUserOrder")
    @ResponseBody
    public Object queryUserOrder(@Valid OrderQueryDto orderDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String,Object> param=new HashMap<>();
            param.put("schoolCode",orderDto.getSchoolCode());
            param.put("userId",orderDto.getUserId());
            PageInfo<Order> pageInfo = orderService.getOrderByCondition(param,orderDto.getPageNum(),orderDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
