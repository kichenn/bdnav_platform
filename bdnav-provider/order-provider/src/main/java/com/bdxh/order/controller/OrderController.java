package com.bdxh.order.controller;

import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@Api(value = "用户订单管理", tags = "用户订单管理API")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 创建订单
     * @param addOrderDto
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ApiOperation(value = "创建订单",response = Boolean.class)
    public Object createOrder(@Validated @RequestBody AddOrderDto addOrderDto) {
        //数据拷贝,
        Order order=new Order();
        BeanUtils.copyProperties(addOrderDto, order);
        //设置全局订单id,状态类型
        order.setOrderNo(snowflakeIdWorker.nextId());
        order.setBusinessStatus(addOrderDto.getBusinessStatus().getCode());
        order.setBusinessType(addOrderDto.getBusinessType().getCode());
        order.setPayStatus(addOrderDto.getPayStatus().getCode());
        order.setPayType(addOrderDto.getPayType().getCode());
        order.setTradeStatus(addOrderDto.getTradeStatus().getCode());
        order.setUserType(addOrderDto.getUserType().getCode());
        order.setTradeType(addOrderDto.getTradeType().getCode());
        try {
            return WrapMapper.ok(orderService.save(order)>0);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    /**
     * 根据条件分页显示订单记录
     * @param orderQueryDto
     * @Date: 2019/6/1 16:56
     */
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页显示订单记录",response = Order.class)
    public Object queryUserOrder(@Validated @RequestBody OrderQueryDto orderQueryDto) {
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(orderQueryDto);
            PageInfo<Order> orders = orderService.getOrderByCondition(param, orderQueryDto.getPageNum(),orderQueryDto.getPageSize());
            return WrapMapper.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除订单
     * @param schoolCode
     * @param userId
     * @param orderNo
     * @Date: 2019/6/1 16:58
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    @ApiOperation(value = "删除订单",response = Boolean.class)
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam("orderNo") @NotNull(message = "订单id不能为空") Long orderNo) {
        try {
            return WrapMapper.ok(orderService.deleteOrder(schoolCode,userId,orderNo));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }



    /**
     * 更新订单
     * @param orderUpdateDto
     * @return
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @ApiOperation(value = "更新订单",response = Boolean.class)
    public Object updateOrder(@Validated @RequestBody OrderUpdateDto orderUpdateDto) {
        //数据拷贝
        Order order=new Order();
        BeanUtils.copyProperties(orderUpdateDto, order);
        //设置状态类型
        order.setBusinessStatus(orderUpdateDto.getBusinessStatus().getCode());
        order.setBusinessType(orderUpdateDto.getBusinessType().getCode());
        order.setPayStatus(orderUpdateDto.getPayStatus().getCode());
        order.setPayType(orderUpdateDto.getPayType().getCode());
        order.setTradeStatus(orderUpdateDto.getTradeStatus().getCode());
        order.setTradeType(orderUpdateDto.getTradeType().getCode());
        try {
            return WrapMapper.ok(orderService.update(order)>0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }
}
