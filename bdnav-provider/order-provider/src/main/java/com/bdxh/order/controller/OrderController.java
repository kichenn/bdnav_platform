package com.bdxh.order.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.dto.OrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     *
     * @param orderDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("创建订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            OrderVo orderVo = orderService.createOrder(orderDto);
            return WrapMapper.ok(orderVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

//    @ApiOperation("订单号查询")
//    @RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
//    @ResponseBody
//    public Object queryOrder(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
//                             @RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId,
//                             @RequestParam(name = "orderNo") @NotNull(message = "订单号不能为空") Long orderNo) {
//        Map<String, Object> param = new HashMap<>();
//        param.put("schoolCode", schoolCode);
//        param.put("userId", userId);
//        param.put("orderNo", orderNo);
//        try {
//            Order order = orderService.getOrderByOrderNo(param);
//            return WrapMapper.ok(order);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return WrapMapper.error(e.getMessage());
//        }
//    }


    //分页所有
    @ApiOperation("分页查询")
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object queryUserOrder(@Valid @RequestBody OrderQueryDto orderDto, BindingResult bindingResult) {

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("schoolCode", orderDto.getSchoolCode());
            param.put("userId", orderDto.getUserId());
            PageInfo<Order> pageInfo = orderService.getOrderByCondition(param, orderDto.getPageNum(), orderDto.getPageSize());
            return WrapMapper.ok(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam(name = "id") @NotNull(message = "订单id不能为空") Long id) {
        try {

            Map<String, Object> param = new HashMap<>();
            param.put("schoolCode", schoolCode);
            param.put("userId", userId);
            param.put("id", id);

            Boolean result = orderService.deleteOrder(param);
//            orderService.deleteOrder(schoolCode,userId,id);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("批量删除订单")
    @RequestMapping(value = "/deleteOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOrders(@RequestParam("schoolCodes") @NotNull(message = "schoolCode不能为空") String schoolCodes,
                              @RequestParam("userIds") @NotNull(message = "userId不能为空") Long userIds,
                              @RequestParam(name = "ids") @NotNull(message = "订单id不能为空") Long ids) {
        try {

//            Boolean result = orderService.deleteOrders(schoolCodes,userIds,ids);
//            orderService.deleteOrder(schoolCode,userId,id);
            return WrapMapper.ok();
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
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public Object updateOrder(@Valid @RequestBody OrderUpdateDto orderUpdateDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }

        try {
            Boolean result = orderService.updateOrder(orderUpdateDto);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }
}
