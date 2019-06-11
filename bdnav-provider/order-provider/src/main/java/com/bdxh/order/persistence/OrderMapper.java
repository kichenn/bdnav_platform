package com.bdxh.order.persistence;

import com.bdxh.order.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper extends Mapper<Order> {


    /**
     * 根据条件查询订单列表
     * @param order
     * @return
     */
    List<Order> getOrderByCondition(@Param("order")Order order);

    int deleteByOrderId(@Param("SchoolCode")String SchoolCode,@Param("UserId") Long UserId,@Param("OrderNo")Long OrderNo);

    //批量删除订单信息
    int deleteByOrderIds(@Param("list") List<Map<String,String>> list);

}