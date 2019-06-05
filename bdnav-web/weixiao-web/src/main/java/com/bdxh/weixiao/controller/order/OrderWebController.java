package com.bdxh.weixiao.controller.order;

import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrdersControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-03 19:24
 **/
@RequestMapping("/orderWeb")
@RestController
@Validated
@Api(value = "微校服务----商品订单API", tags = "微校服务----商品订单API")
@Slf4j
public class OrderWebController {

    @Autowired
    private OrdersControllerClient ordersControllerClient;

    /**
     * 确定购买的商品添加订单
     *
     * @param addOrderDto
     * @return
     */
    @RequestMapping(value = "/saveOrder",method = RequestMethod.POST)
    @ApiModelProperty(value = "家长端-----确定购买的商品添加订单")
    public Object saveOrder(@RequestBody AddOrderDto addOrderDto) {
        return ordersControllerClient.createOrder(addOrderDto);
    }

    /**
     * 家长端-----查看订单
     *
     * @param orderQueryDto
     * @return
     */
    @RequestMapping(value = "/findOrder",method = RequestMethod.GET)
    @ApiModelProperty(value = "家长端-----查看订单")
    public Object findOrder(@RequestBody OrderQueryDto orderQueryDto) {

        return ordersControllerClient.queryUserOrder(orderQueryDto);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ApiModelProperty(value = "家长端-----修改订单")
    public Object updateOrder(@RequestBody OrderUpdateDto orderUpdateDto){
        return ordersControllerClient.updateOrder(orderUpdateDto);
    }
}