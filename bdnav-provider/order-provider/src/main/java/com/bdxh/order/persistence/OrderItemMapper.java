package com.bdxh.order.persistence;

import com.bdxh.order.entity.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * 订单明细的持久层接口
 * @Author: WanMing
 * @Date: 2019/6/3 11:37
 */
@Repository
public interface OrderItemMapper extends Mapper<OrderItem> {



    /**
     *查询总条数
     */
    Integer getOrderItemAllCount();

    /**
     *批量删除方法
     */
    Integer delOrderItemInIds(@Param("ids") List<Long> ids);

    /**
     * 查询订单明细
     * @Author: WanMing
     * @Date: 2019/6/3 15:21
     */
    List<OrderItem> findOrderItemByOrderNo(@Param("orderNo")Long orderNo);
}