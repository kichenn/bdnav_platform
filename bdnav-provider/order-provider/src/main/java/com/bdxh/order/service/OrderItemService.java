package com.bdxh.order.service;

import com.bdxh.common.support.IService;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.vo.OrderItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description: 订单明细业务层接口
 * @author: xuyuan
 * @create: 2019-01-09 15:00
 **/

public interface OrderItemService extends IService<OrderItem> {


    /**
     *查询所有数量
     */
    Integer getOrderItemAllCount();

    /**
     *批量删除方法
     */
    Boolean batchDelOrderItemInIds(List<Long> id);

    /**
     * 根据订单号查询订单明细
     * @Author: WanMing
     * @Date: 2019/6/3 14:10
     */
    List<OrderItemVo> findOrderItemByOrderNo(Long orderNo);







}
