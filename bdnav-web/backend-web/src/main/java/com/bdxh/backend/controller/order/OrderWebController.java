package com.bdxh.backend.controller.order;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.AddOrderItemDto;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.order.vo.OrderItemVo;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private OrdersControllerClient ordersControllerClient;

    @Autowired
    private ProductControllerClient productControllerClient;

    @Autowired
    private OrderItemControllerClient orderItemControllerClient;






    @ApiOperation("查询订单")
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    public Object queryUserOrder(@Validated @RequestBody OrderQueryDto orderDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
            try {
                Wrapper wrapper = ordersControllerClient.queryUserOrder(orderDto);
                return wrapper;
            } catch (Exception e) {
                e.printStackTrace();
                return WrapMapper.error(e.getMessage());
            }

    };


    @ApiOperation("删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public Object deleteOrder(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                              @RequestParam("userId") @NotNull(message = "userId不能为空") Long userId,
                              @RequestParam(name = "orderNo") @NotNull(message = "订单id不能为空") Long orderNo) {

        try {
            Wrapper wrapper = ordersControllerClient.deleteOrder(schoolCode, userId, orderNo);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 添加订单,写订单明细
     * @Author: WanMing
     * @Date: 2019/6/4 15:46
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ApiOperation(value = "添加订单",response = Boolean.class)
    public Object addOrder(@Validated @RequestBody AddOrderDto addOrderDto){
        User user = SecurityUtils.getCurrentUser();
        addOrderDto.setOperator(user.getId());
        addOrderDto.setOperatorName(user.getUserName());
        try {
            //1.增加订单记录
            Wrapper wrapper = ordersControllerClient.createOrder(addOrderDto);
            if(null==wrapper.getResult()){
                return WrapMapper.error("订单添加失败");
            }
            Long orderNo = (Long) wrapper.getResult();
            //2.查询商品信息详情
            ProductDetailsVo detailsVo = productControllerClient.findProductDetails(Long.valueOf(addOrderDto.getProductId())).getResult();
            //3.增加商品明细记录
            if(null!=detailsVo && ProductTypeEnum.GROUP.getCode().equals(detailsVo.getProductType())){
                //套餐
                detailsVo.getProductList().stream().forEach(item->{
                    AddOrderItemDto addOrderItemDto = new AddOrderItemDto();
                    BeanUtils.copyProperties(item, addOrderItemDto);
                    addOrderItemDto.setProductId(item.getId());
                    addOrderItemDto.setOrderNo(orderNo);
                    orderItemControllerClient.addOrderItem(addOrderItemDto);
                });
            }else {
                //单品
                AddOrderItemDto addOrderItemDto = new AddOrderItemDto();
                BeanUtils.copyProperties(detailsVo, addOrderItemDto);
                addOrderItemDto.setProductId(detailsVo.getId());
                addOrderItemDto.setOrderNo(orderNo);
                orderItemControllerClient.addOrderItem(addOrderItemDto);
            }
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @ApiOperation("更新订单")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public Object updateOrder(@Validated @RequestBody OrderUpdateDto orderUpdateDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            orderUpdateDto.setOperator(user.getId());
            orderUpdateDto.setOperatorName(user.getUserName());
            Wrapper wrapper = ordersControllerClient.updateOrder(orderUpdateDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
