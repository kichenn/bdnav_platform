package com.bdxh.order.service.impl;

import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.order.enums.OrderTradeStatusEnum;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.order.dto.OrderDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.persistence.OrderItemMapper;
import com.bdxh.order.persistence.OrderMapper;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 订单服务
 * @author: xuyuan
 * @create: 2019-01-09 15:04
 **/
@Service
public class OrderServiceImpl extends BaseService<Order> implements OrderService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderVo createOrder(OrderDto orderDto) {
        //订单号
        Long orderNo=snowflakeIdWorker.nextId();
        Order order = BeanMapUtils.map(orderDto, Order.class);
        order.setOrderNo(orderNo);
        order.setBusinessStatus(BusinessStatusEnum.NO_PROCESS.getCode());
        order.setTradeStatus(OrderTradeStatusEnum.TRADING.getCode());
        order.setPayStatus(OrderPayStatusEnum.NO_PAY.getCode());
        List<OrderItem> orderItems = BeanMapUtils.mapList(orderDto.getItems(), OrderItem.class);
        String productIds = orderItems.stream().map(u -> u.getProductItem()).collect(Collectors.joining(","));
        order.setProductIds(productIds);
        orderMapper.insertSelective(order);
        for (int i=0;i<orderItems.size();i++){
            //明细主键
            OrderItem orderItem = orderItems.get(i);
            Long itemNo=snowflakeIdWorker.nextId();
            orderItem.setItemNo(itemNo);
            orderItem.setOrderNo(orderNo);
            orderItem.setSchoolCode(order.getSchoolCode());
            orderItem.setUserId(order.getUserId());
            orderItemMapper.insertSelective(orderItem);
        }
        OrderVo orderVo =new OrderVo();
        orderVo.setSchoolCode(order.getSchoolCode());
        orderVo.setUserId(order.getUserId());
        orderVo.setOrderNo(orderNo);
        return orderVo;
    }

    @Override
    public Order getOrderByOrderNo(Map<String, Object> param) {
        Order order = orderMapper.getOrderByOrderNo(param);
        return order;
    }

    @Override
    public PageInfo<Order> getOrderByCondition(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getOrderByCondition(param);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

}
