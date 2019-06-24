package com.bdxh.order.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.ModifyPayOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.order.vo.OrderVo;
import com.bdxh.order.vo.OrderVo1;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;


/**
 * @Description: 订单feign降级服务
 * @Author: Kang
 * @Date: 2019/6/20 16:28
 */
@Component
public class OrdersControllerClientFallback implements OrdersControllerClient {

    @Override
    public Wrapper deleteOrder(String schoolCode, Long userId, Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<OrderVo>> queryUserOrder(OrderQueryDto orderDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateOrder(OrderUpdateDto orderUpdateDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper createOrder(AddOrderDto addOrderDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<OrderVo> findThirdOrderByOrderNo(String thirdOrderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<OrderVo> findOrderByOrderNo(Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<OrderVo1> findOrderByOrderNo1(Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> modifyBindOrder(ModifyPayOrderDto modifyPayOrderDto) {
        return WrapMapper.error();
    }
}
