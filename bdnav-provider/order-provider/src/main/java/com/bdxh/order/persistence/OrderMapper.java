package com.bdxh.order.persistence;

import com.bdxh.order.entity.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper extends Mapper<Order> {

    /**
     * 根据订单号查询订单 schoolCode userId 必传
     * @param param
     * @return
     */
    Order getOrderByOrderNo(Map<String,Object> param);

    /**
     * 根据条件查询订单列表 schoolCode userId 必传
     * @param param
     * @return
     */
    List<Order> getOrderByCondition(Map<String,Object> param);

}