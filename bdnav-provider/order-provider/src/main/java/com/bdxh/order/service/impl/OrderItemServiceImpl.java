package com.bdxh.order.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.persistence.OrderItemMapper;
import com.bdxh.order.service.OrderItemService;
import com.bdxh.order.vo.OrderItemVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @description: 订单明细业务层
 * @author: xuyuan
 * @create: 2019-01-09 15:05
 **/
@Service
@Slf4j
public class OrderItemServiceImpl extends BaseService<OrderItem> implements OrderItemService {


    @Autowired
    private OrderItemMapper orderItemMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getOrderItemAllCount(){
        return orderItemMapper.getOrderItemAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelOrderItemInIds(List<Long> ids){
        return orderItemMapper.delOrderItemInIds(ids) > 0;
    }

    /**
     * 根据订单编号查询订单明细
     * @param orderNo 订单编号
     * @Author: WanMing
     * @Date: 2019/6/3 14:10
     */
    @Override
    public List<OrderItemVo> findOrderItemByOrderNo(Long orderNo) {
        List<OrderItem> orderItems = orderItemMapper.findOrderItemByOrderNo(orderNo);
        List<OrderItemVo> orderItemVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orderItems)){
            orderItems.stream().forEach(item->{
                OrderItemVo orderItemVo = new OrderItemVo();
                BeanUtils.copyProperties(item, orderItemVo);
                orderItemVos.add(orderItemVo);
            });
        }
        return orderItemVos;
    }





}
