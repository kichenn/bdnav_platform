package com.bdxh.weixiao.controller.order;

import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.order.vo.OrderVo;
import com.bdxh.product.entity.Product;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 订单信息
 * @Author: Kang
 * @Date: 2019/7/5 9:59
 */
@RequestMapping("/orderWeb")
@RestController
@Validated
@Api(value = "微校服务----商品订单API", tags = "微校服务----商品订单API")
@Slf4j
public class OrderWebController {

    @Autowired
    private OrdersControllerClient ordersControllerClient;

    @Autowired
    private ProductControllerClient productControllerClient;

    @RequestMapping(value = "/findOrder", method = RequestMethod.POST)
    @ApiOperation(value = "家长端-----查看订单", response = OrderVo.class)
    public Object findOrder(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        OrderQueryDto orderQueryDto = new OrderQueryDto();
        orderQueryDto.setUserId(userInfo.getFamilyId());
        orderQueryDto.setPageNum(pageNum);
        orderQueryDto.setPageSize(pageSize);
        PageInfo<OrderVo> orderVos = ordersControllerClient.queryUserOrder(orderQueryDto).getResult();
        for (OrderVo orderTemp : orderVos.getList()) {
            Product product = productControllerClient.findProductById(Long.valueOf(orderTemp.getProductId())).getResult();
            orderTemp.setProductName(product != null ? product.getProductShowName() : "");
        }
        return orderVos;
    }
}