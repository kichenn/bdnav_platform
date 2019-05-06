package com.bdxh.order.controller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.dto.OrderAddDto;
import com.bdxh.order.dto.OrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 订单服务控制器
 * @author: xuyuan
 * @create: 2019-01-09 15:36
 **/
@RestController
@RequestMapping("/order")
@Slf4j
@Validated
@Api(value = "管控订单关系", tags = "管控订单关系")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 创建订单
     *
     * @param orderDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("创建订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Object createOrder(@Valid @RequestBody OrderAddDto orderDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Order order=new Order();
            order.setOrderNo(snowflakeIdWorker.nextId());
            BeanUtils.copyProperties(orderDto, order);
            Boolean flag =orderService.save(order)>0;
            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    //带条件分页所有
    @ApiOperation("带条件分页查询")
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    public Object queryUserOrder(@Valid @RequestBody OrderQueryDto orderDto, BindingResult bindingResult) {
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(orderDto);
            PageInfo<Order> qrders = orderService.getOrderByCondition(param, orderDto.getPageNum(),orderDto.getPageSize());
            return WrapMapper.ok(qrders);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam(name = "orderNo") @NotNull(message = "订单id不能为空") Long orderNo) {
        try {
            Boolean result = orderService.deleteOrder(schoolCode,userId,orderNo);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }



    /**
     * 更新订单
     *
     * @param orderUpdateDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("更新订单")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public Object updateOrder(@Valid @RequestBody OrderUpdateDto orderUpdateDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Order order=new Order();
            BeanUtils.copyProperties(orderUpdateDto, order);
            Boolean result = orderService.update(order)>0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }
}
