package com.bdxh.order.service;

import com.bdxh.common.support.IService;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @description: 订单服务接口类
 * @author: xuyuan
 * @create: 2019-01-09 14:59
 **/
public interface OrderService extends IService<Order> {

    /**
     * 根据条件查询用户订单列表
     * @param orderQueryDto
     * @return
     */
    PageInfo<Order> getOrderByCondition(OrderQueryDto orderQueryDto);

    /**
     * 根据主键删除商品
     */
    boolean deleteOrder(String SchoolCode,Long UserId,Long OrderNo);

    /**
     * 批量删除商品
     *
     */
    boolean deleteOrders(String schoolCodes,String userIds,String ids);

//    /**
//     * 添加订单同时添加订单明细
//     * @Author: WanMing
//     * @Date: 2019/6/3 11:49
//     */
//    Boolean addOrder(AddOrderDto addOrderDto);

}
