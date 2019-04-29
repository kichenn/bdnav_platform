package com.bdxh.order.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrdersControllerClient;
import org.springframework.stereotype.Component;


/**
 * @description: 应用分类feign降级服务
 * @author: xuyuan
 * @create: 2019-04-11 15:50
 **/
@Component
public class OrdersControllerClientFallback implements OrdersControllerClient {
    @Override
    public Wrapper queryOrder(String schoolCode, Long userId, Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteOrder(String schoolCode, Long userId, Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryUserOrder(OrderQueryDto orderDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateOrder(OrderUpdateDto orderUpdateDto) {
        return WrapMapper.error();
    }
}
