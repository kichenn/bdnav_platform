package com.bdxh.order.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.AddOrderItemDto;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.vo.OrderItemVo;
import org.springframework.stereotype.Component;

import java.util.List;

/** orderItem的降级服务
 * @author WanMing
 * @date 2019/6/4 11:18
 */
@Component
public class OrderItemControllerClientFallback implements OrderItemControllerClient {
    @Override
    public Wrapper addOrderItem(AddOrderItemDto addOrderItemDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<OrderItemVo>> findOrderItemByOrderNo(Long orderNo) {
        return WrapMapper.error();
    }
}
