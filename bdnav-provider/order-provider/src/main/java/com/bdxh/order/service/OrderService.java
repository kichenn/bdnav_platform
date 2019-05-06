package com.bdxh.order.service;

import com.bdxh.common.support.IService;
import com.bdxh.order.entity.Order;
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
     * @param param
     * @return
     */
    PageInfo<Order> getOrderByCondition(Map<String,Object> param, Integer pageNum, Integer pageSize);

    /**
     * 根据主键删除商品
     */
    boolean deleteOrder(String SchoolCode,Long UserId,Long OrderNo);

    /**
     * 批量删除商品
     *
     */
    boolean deleteOrders(String schoolCodes,String userIds,String ids);





}
