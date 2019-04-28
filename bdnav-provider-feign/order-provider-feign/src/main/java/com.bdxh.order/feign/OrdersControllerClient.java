package com.bdxh.order.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.fallback.OrdersControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: com.bdxh.order.feign
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/4/27 14:07.
 */


@Service
@FeignClient(value ="order-provider-cluster",fallback = OrdersControllerClientFallback.class)
public interface OrdersControllerClient {


    @RequestMapping(value = "/order/queryOrder",method = RequestMethod.GET)
    Wrapper<Object> queryOrder(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                              @RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId,
                              @RequestParam(name = "orderNo") @NotNull(message = "订单号不能为空") Long orderNo);


    @RequestMapping(value = "/order/deleteOrder", method = RequestMethod.POST)
    Wrapper deleteOrder(@RequestParam(name = "schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                        @RequestParam(name = "userId") @NotNull(message = "userId不能为空") Long userId,
                        @RequestParam(name = "id") @NotNull(message = "订单id不能为空") Long id);



    @RequestMapping(value = "/order/queryUserOrder",method = RequestMethod.POST)
    Wrapper queryUserOrder(@RequestBody OrderQueryDto orderDto);


    @RequestMapping(value = "/order/updateProduct",method = RequestMethod.POST)
    Wrapper updateOrder(@RequestBody OrderUpdateDto orderUpdateDto);

}
