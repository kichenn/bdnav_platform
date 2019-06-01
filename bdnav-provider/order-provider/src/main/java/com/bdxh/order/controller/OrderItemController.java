package com.bdxh.order.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}