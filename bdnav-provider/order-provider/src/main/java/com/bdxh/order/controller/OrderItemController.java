package com.bdxh.order.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.AddOrderItemDto;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.service.OrderItemService;
import com.bdxh.order.vo.OrderItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: binzh
 * @create: 2019-06-01 10:48
 **/
@RestController
@RequestMapping("/orderItem")
@Slf4j
@Validated
@Api(value = "管控订单商品详情表", tags = "管控订单商品详情表")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * 根据订单编号查询订单明细
     * @Author: WanMing
     * @Date: 2019/6/3 15:44
     */
    @RequestMapping(value = "/findOrderItemByOrderNo",method = RequestMethod.GET)
    @ApiOperation(value = "根据订单编号查询订单明细",response = OrderItemVo.class)
    public Object findOrderItemByOrderNo(@Validated @RequestParam("orderNo") Long orderNo){
        return WrapMapper.ok(orderItemService.findOrderItemByOrderNo(orderNo));
    }

//    /**
//     * 修改订单明细
//     * @Author: WanMing
//     * @Date: 2019/6/3 18:05
//     */
//    @RequestMapping(value = "/modifyOrderItem",method = RequestMethod.POST)
//    @ApiOperation(value = "修改订单明细",response = Boolean.class)
//    public Object modifyOrderItem(@Validated @RequestBody ModifyOrderItemDto modifyOrderItemDto){
//        //数据拷贝
//        OrderItem orderItem = new OrderItem();
//        BeanUtils.copyProperties(modifyOrderItemDto, orderItem);
//        return WrapMapper.ok(orderItemService.update(orderItem));
//    }

    /**
     * 添加订单明细
     * @Author: WanMing
     * @Date: 2019/6/4 9:33
     */
    @RequestMapping(value = "/addOrderItem",method = RequestMethod.POST)
    @ApiOperation(value = "添加订单明细",response = String.class)
    public Object addOrderItem(@Validated @RequestBody AddOrderItemDto addOrderItemDto){
        //数据拷贝
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(addOrderItemDto, orderItem);
        orderItem.setProductNum(new Byte("1"));
        orderItem.setProductType(new Byte("1"));
        orderItem.setId(snowflakeIdWorker.nextId());
        orderItem.setProductItem(addOrderItemDto.getProductId().toString());
        try {
            return WrapMapper.ok(orderItemService.save(orderItem));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据订单明细编号删除订单明细
     * @Author: WanMing
     * @Date: 2019/6/5 16:46
     */
    @RequestMapping(value = "/delOrderItem",method = RequestMethod.GET)
    @ApiOperation(value = "删除订单明细",response = Boolean.class)
    public Object delOrderItem(@Validated @RequestParam("id")Long id){
        return WrapMapper.ok(orderItemService.deleteByKey(id));
    }















}