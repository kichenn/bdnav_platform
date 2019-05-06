package com.bdxh.order.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.order.entity.Order;
import com.bdxh.order.persistence.OrderMapper;
import com.bdxh.order.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public PageInfo<Order> getOrderByCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getOrderByCondition(param);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrder(String SchoolCode, Long UserId, Long OrderNo) {
        return orderMapper.deleteByOrderId(SchoolCode,UserId,OrderNo) > 0;

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOrders(String schoolCodes, String userIds, String ids) {
        String[] schoolCode =schoolCodes.split(",");
        String[] userId =userIds.split(",");
        String[] id =ids.split(",");

            List<Map<String,String>>list =new ArrayList<>();
            for (int i = 0; i < schoolCode.length; i++) {
                Map<String,String> map=new HashMap<>();
                map.put("schoolCode",schoolCode[i]);
                map.put("userId",userId[i]);
                map.put("id",id[i]);
                list.add(map);
            }
            return orderMapper.deleteByOrderIds(list) > 0;

    }

}
