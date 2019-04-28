package com.bdxh.backend.controller.order;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrdersControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @ClassName: com.bdxh.backend.controller.order
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/4/27 12:46.
 */


@RestController
@RequestMapping("/OrderWxWeb")
@Validated
@Slf4j
@Api(value = "订单服务", tags = "订单服务")
public class OrderWebController {

    @Autowired
    private OrdersControllerClient orderscc;



    @ApiOperation("根据订单号查询订单")
    @RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
    public Object queryOrder(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                             @RequestParam(name = "userId") @NotNull(message = "用户id不能为空") Long userId,
                             @RequestParam(name = "orderNo") @NotNull(message = "订单号不能为空") Long orderNo) {

        try {
            Wrapper wrapper = orderscc.queryOrder(schoolCode,userId,orderNo);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation("查询订单")
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    public Object queryUserOrder(@Valid @RequestBody OrderQueryDto orderDto, BindingResult bindingResult){

        try {
            Wrapper wrapper = orderscc.queryUserOrder(orderDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    };


    @ApiOperation("删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam(name = "id") @NotNull(message = "订单id不能为空") Long id) {

        try {
            Wrapper wrapper = orderscc.deleteOrder(schoolCode, userId, id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("更新订单")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public Object updateOrder(@Valid @RequestBody OrderUpdateDto orderUpdateDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }

        try {
            Wrapper wrapper = orderscc.updateOrder(orderUpdateDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
