package com.bdxh.order.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.ModifyPayOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderBindResultVo;
import com.bdxh.order.vo.OrderVo;
import com.bdxh.order.vo.OrderVo1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

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
     *
     * @param addOrderDto
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ApiOperation(value = "创建订单")
    public Object createOrder(@Validated @RequestBody AddOrderDto addOrderDto) {
        //数据拷贝
        Order order = new Order();
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
            orderService.save(order);
            return WrapMapper.ok(order.getOrderNo());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error();
        }
    }


    /**
     * 根据条件查询订单列表数据
     *
     * @Author: WanMing
     * @Date: 2019/6/4 16:22
     */
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询订单列表数据", response = OrderVo.class)
    public Object queryUserOrder(@Validated @RequestBody OrderQueryDto orderQueryDto) {
        try {
            return WrapMapper.ok(orderService.getOrderByCondition(orderQueryDto));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除订单
     *
     * @param schoolCode
     * @param userId
     * @param orderNo
     * @Date: 2019/6/1 16:58
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    @ApiOperation(value = "删除订单", response = Boolean.class)
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam("orderNo") @NotNull(message = "订单id不能为空") Long orderNo) {
        try {
            return WrapMapper.ok(orderService.deleteOrder(schoolCode, userId, orderNo));
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
     * @return
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @ApiOperation(value = "更新订单", response = Boolean.class)
    public Object updateOrder(@Validated @RequestBody OrderUpdateDto orderUpdateDto) {
        //数据拷贝
        Order order = new Order();
        BeanUtils.copyProperties(orderUpdateDto, order);
        //设置状态类型
        order.setPayStatus(orderUpdateDto.getPayStatus().getCode());
        order.setTradeStatus(orderUpdateDto.getTradeStatus().getCode());
        try {
            return WrapMapper.ok(orderService.update(order) > 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @Author: WanMing
     * @Date: 2019/6/5 18:50
     */
    @RequestMapping(value = "/findOrderByOrderNo", method = RequestMethod.GET)
    @ApiOperation(value = "根据订单编号查询订单信息", response = OrderVo.class)
    public Object findOrderByOrderNo(@RequestParam("orderNo") Long orderNo) {
        return WrapMapper.ok(orderService.findOrderByOrderNo(orderNo));
    }

    /**
     * @Description: 根据订单编号查询订单信息
     * @Author: Kang
     * @Date: 2019/6/21 12:19
     */
    @ApiIgnore
    @RequestMapping(value = "/findOrderByOrderNo1", method = RequestMethod.GET)
    @ApiOperation(value = "根据订单编号查询订单信息1(此方法不在swagger展示，给支付成功后我方订单查询部分信息)", response = OrderVo1.class)
    public Object findOrderByOrderNo1(@RequestParam("orderNo") Long orderNo) {
        return WrapMapper.ok(orderService.findOrderByOrderNo1(orderNo));
    }

    /**
     * @Description: 第三方订单查询订单信息
     * @Author: Kang
     * @Date: 2019/6/20 16:22
     */
    @RequestMapping(value = "/findThirdOrderByOrderNo", method = RequestMethod.GET)
    @ApiOperation(value = "第三方订单查询订单信息", response = OrderVo.class)
    public Object findThirdOrderByOrderNo(@RequestParam("thirdOrderNo") String thirdOrderNo) {
        return WrapMapper.ok(orderService.findThirdOrderByOrderNo(thirdOrderNo));
    }

    /**
     * @Description: 我方订单号绑定微信第三方订单号信息
     * @Author: Kang
     * @Date: 2019/6/19 18:30
     */
    @RequestMapping(value = "/modifyBindOrder", method = RequestMethod.POST)
    @ApiOperation(value = "我方订单号绑定微信第三方订单号信息", response = Boolean.class)
    public Object modifyBindOrder(@Validated @RequestBody ModifyPayOrderDto modifyPayOrderDto) {
        //数据拷贝
        Order order = new Order();
        BeanUtils.copyProperties(modifyPayOrderDto, order);
        if (modifyPayOrderDto.getTradeStatus() != null) {
            //交易状态不为空
            order.setTradeStatus(modifyPayOrderDto.getTradeStatus().getCode());
        }
        if (modifyPayOrderDto.getPayStatus() != null) {
            //支付状态不为空
            order.setPayStatus(modifyPayOrderDto.getPayStatus().getCode());
        }
        if (modifyPayOrderDto.getBusinessStatus() != null) {
            //业务状态不为空
            order.setBusinessStatus(modifyPayOrderDto.getBusinessStatus().getCode());
        }
        //返参包装成对象，在mq消费者中，返参获取不到故修改
        OrderBindResultVo orderBindResultVo = new OrderBindResultVo();
        orderBindResultVo.setResult(Long.valueOf(orderService.update(order)));
        return WrapMapper.ok(orderBindResultVo);
    }
}
