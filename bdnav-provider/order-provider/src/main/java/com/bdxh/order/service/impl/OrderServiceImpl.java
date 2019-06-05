package com.bdxh.order.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.persistence.OrderItemMapper;
import com.bdxh.order.persistence.OrderMapper;
import com.bdxh.order.service.OrderService;
import com.bdxh.order.vo.OrderVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @description: 订单服务
 * @author: xuyuan
 * @create: 2019-01-09 15:04
 **/
@Service
public class OrderServiceImpl extends BaseService<Order> implements OrderService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    public PageInfo<Order> getOrderByCondition(OrderQueryDto orderQueryDto) {
        PageHelper.startPage(orderQueryDto.getPageNum(), orderQueryDto.getPageSize());
        Order order = new Order();
        BeanUtils.copyProperties(orderQueryDto, order);
        List<Order> orders = orderMapper.getOrderByCondition(order);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(String SchoolCode, Long UserId, Long OrderNo) {
        return orderMapper.deleteByOrderId(SchoolCode, UserId, OrderNo) > 0;

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrders(String schoolCodes, String userIds, String ids) {
        String[] schoolCode = schoolCodes.split(",");
        String[] userId = userIds.split(",");
        String[] id = ids.split(",");

        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < schoolCode.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("schoolCode", schoolCode[i]);
            map.put("userId", userId[i]);
            map.put("id", id[i]);
            list.add(map);
        }
        return orderMapper.deleteByOrderIds(list) > 0;

    }

//    /**
//     * 添加订单同时添加订单明细
//     *
//     * @param addOrderDto
//     * @Author: WanMing
//     * @Date: 2019/6/3 11:49
//     */
//    @Override
//    public Boolean addOrder(AddOrderDto addOrderDto) {
//        //添加订单
//        Order order = new Order();
//        BeanUtils.copyProperties(addOrderDto, order);
//        //生成全局id
//        Long orderNo = snowflakeIdWorker.nextId();
//        order.setOrderNo(orderNo);
//        order.setBusinessStatus(addOrderDto.getBusinessStatus().getCode());
//        order.setBusinessType(addOrderDto.getBusinessType().getCode());
//        order.setPayStatus(addOrderDto.getPayStatus().getCode());
//        order.setPayType(addOrderDto.getPayType().getCode());
//        order.setTradeStatus(addOrderDto.getTradeStatus().getCode());
//        order.setUserType(addOrderDto.getUserType().getCode());
//        order.setTradeType(addOrderDto.getTradeType().getCode());
//        //添加订单明细
//        String productId = addOrderDto.getProductId();
//        if(productId.contains(",")){
//            //多个商品
//            String[] productIds = productId.split(",");
//            for (String id : productIds) {
//                OrderItem orderItem = new OrderItem();
//                //商品的具体信息保存
//            }
//        }
//        return null;
//    }


}
