package com.bdxh.order.persistence;

import com.bdxh.order.entity.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderItemMapper extends Mapper<OrderItem> {
}