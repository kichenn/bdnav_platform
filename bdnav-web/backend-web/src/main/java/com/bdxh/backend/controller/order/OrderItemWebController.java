package com.bdxh.backend.controller.order;

import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.vo.OrderItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 订单明细控制器
 * @author WanMing
 * @date 2019/6/4 14:51
 */
@RestController
@RequestMapping("/orderItemWebController")
@Validated
@Slf4j
@Api(value = "订单管理--订单明细", tags = "订单管理--订单明细API")
public class OrderItemWebController {


    @Autowired
    private OrderItemControllerClient orderItemControllerClient;



    /**
     * 查询订单明细记录
     * @Author: WanMing
     * @Date: 2019/6/4 14:46
     */
    @ApiOperation(value = "根据订单号查询订单明细",response = OrderItemVo.class)
    @RequestMapping(value = "/queryOrderItemByOrderNo", method = RequestMethod.GET)
    public Object queryOrderItemByOrderNo(@RequestParam("orderNo") Long orderNo){
        return orderItemControllerClient.findOrderItemByOrderNo(orderNo);
    }

}
