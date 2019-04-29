package com.bdxh.backend.controller.order;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.OrderQueryDto;
import com.bdxh.order.dto.OrderUpdateDto;
import com.bdxh.order.entity.Order;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
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

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    private FamilyControllerClient familyControllerClient;


//queryFamilyInfo


    @ApiOperation("查询订单")
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
    public Object queryUserOrder(@Valid @RequestBody OrderQueryDto orderDto, BindingResult bindingResult){

        try {
            PageInfo<Order> pageInfo= orderscc.queryUserOrder(orderDto).getResult();
           List pageInfoList = pageInfo.getList();

//            for (int i=1;i<pageInfoList.size();i++) {
//                Order order = (Order) pageInfoList.get(i);
//            }
            try {
                Wrapper wrapper = orderscc.queryUserOrder(orderDto);
                return wrapper;
            } catch (Exception e) {
                e.printStackTrace();
                return WrapMapper.error(e.getMessage());
            }


//            return WrapMapper.ok(pageInfo);
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

    @ApiOperation("根据学校id查询学校信息")
    @RequestMapping(value = "/queryschool", method = RequestMethod.POST)
    public Object queryschool(@RequestParam(name = "schoolId") @NotNull(message = "用户id不能为空") Long schoolId){

        try {
            Wrapper wrapper = schoolControllerClient.findSchoolById(schoolId);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
