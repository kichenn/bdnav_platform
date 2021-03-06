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
import com.bdxh.order.vo.OrderVo1;
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
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 根据条件分页查询
     *
     * @Author: WanMing
     * @Date: 2019/6/5 18:42
     */
    @Override
    public PageInfo<OrderVo> getOrderByCondition(OrderQueryDto orderQueryDto) {
        Page page = PageHelper.startPage(orderQueryDto.getPageNum(), orderQueryDto.getPageSize());
        Order order = new Order();
        BeanUtils.copyProperties(orderQueryDto, order);
        List<Order> orders = orderMapper.getOrderByCondition(order);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        List<OrderVo> orderVos = new ArrayList<>();
        orders.stream().forEach(item -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(item, orderVo);
            orderVos.add(orderVo);
        });
        PageInfo<OrderVo> pageOrderVo = new PageInfo<>(orderVos);
        pageOrderVo.setPageNum(pageInfo.getPageNum());
        pageOrderVo.setPageSize(pageInfo.getPageSize());
        pageOrderVo.setSize(pageInfo.getSize());
        pageOrderVo.setPageSize(pageInfo.getPageSize());
        pageOrderVo.setTotal(pageInfo.getTotal());
        pageOrderVo.setHasNextPage(pageInfo.isHasNextPage());
        return pageOrderVo;
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

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderNo
     * @Author: WanMing
     * @Date: 2019/6/5 18:39
     */
    @Override
    public OrderVo findOrderByOrderNo(Long orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        //数据拷贝
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        return orderVo;
    }

    /**
     * @Description: 根据订单编号查询订单信息，支付成功后订单编号，查询订单部分信息
     * @Author: Kang
     * @Date: 2019/6/21 12:24
     */
    @Override
    public OrderVo1 findOrderByOrderNo1(Long orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        //数据拷贝
        OrderVo1 orderVo1 = new OrderVo1();
        BeanUtils.copyProperties(order, orderVo1);
        return orderVo1;
    }

    /**
     * @Description: 第三方订单查询订单信息
     * @Author: Kang
     * @Date: 2019/6/20 16:22
     */
    @Override
    public OrderVo findThirdOrderByOrderNo(String thirdOrderNo) {
        Order order = new Order();
        order.setThirdOrderNo(thirdOrderNo);
        order = orderMapper.selectOne(order);
        //数据拷贝
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        return orderVo;
    }

}
