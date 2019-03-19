package com.bdxh.order.service;

import com.bdxh.common.support.IService;
import com.bdxh.order.dto.OrderDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 订单服务接口类
 * @author: xuyuan
 * @create: 2019-01-09 14:59
 **/
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    OrderVo createOrder(OrderDto orderDto);

    /**
     * 根据订单号查询订单
     * @param param
     * @return
     */
    Order getOrderByOrderNo(Map<String,Object> param);

    /**
     * 根据条件查询用户订单列表
     * @param param
     * @return
     */
    PageInfo<Order> getOrderByCondition(Map<String,Object> param, int pageNum, int pageSize);

}
