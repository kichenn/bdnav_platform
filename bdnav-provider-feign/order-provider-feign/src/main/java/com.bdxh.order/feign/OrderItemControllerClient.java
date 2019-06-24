package com.bdxh.order.feign;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.AddOrderItemDto;
import com.bdxh.order.fallback.OrderItemControllerClientFallback;
import com.bdxh.order.vo.OrderItemVo;
import com.bdxh.order.vo.OrderItemVo1;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单明细的feign接口
 *
 * @author WanMing
 * @date 2019/6/4 11:16
 */
@Service
@FeignClient(name = "order-provider-cluster", fallback = OrderItemControllerClientFallback.class)
public interface OrderItemControllerClient {

    /**
     * 添加订单明细
     *
     * @Author: WanMing
     * @Date: 2019/6/4 9:33
     */
    @RequestMapping(value = "/orderItem/addOrderItem", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addOrderItem(@Validated @RequestBody AddOrderItemDto addOrderItemDto);


    /**
     * 根据订单编号查询订单明细
     *
     * @Author: WanMing
     * @Date: 2019/6/3 15:44
     */
    @RequestMapping(value = "/orderItem/findOrderItemByOrderNo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<OrderItemVo>> findOrderItemByOrderNo(@Validated @RequestParam("orderNo") Long orderNo);

    /**
     * 根据订单编号查询订单详情信息1(此方法不在swagger展示，给支付成功后我方订单查询部分信息)
     *
     * @Description:
     * @Author: Kang
     * @Date: 2019/6/21 16:22
     */
    @RequestMapping(value = "/orderItem/findOrderItemByOrderNo1", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<OrderItemVo1>> findOrderItemByOrderNo1(@Validated @RequestParam("orderNo") Long orderNo);
}
