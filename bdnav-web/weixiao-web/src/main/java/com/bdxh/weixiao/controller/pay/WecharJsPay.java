package com.bdxh.weixiao.controller.pay;

import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.order.dto.AddOrderDto;
import com.bdxh.order.dto.AddOrderItemDto;
import com.bdxh.order.dto.AddPayOrderDto;
import com.bdxh.order.enums.*;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping("/wechatJsPayWeiXiaoWeb")
@Api(value = "JSAPI微校支付", tags = "JSAPI微校支付交互API")
@Validated
@Slf4j
public class WecharJsPay {

    @Autowired
    private WechatJsPayControllerClient wechatJsPayControllerClient;

    @Autowired
    private OrdersControllerClient ordersControllerClient;

    @Autowired
    private OrderItemControllerClient orderItemControllerClient;

    @Autowired
    private ProductControllerClient productControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;


    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "JS统一下单购买的商品", response = String.class)
    public Object wechatJsPayOrder(@Validated @RequestBody AddPayOrderDto addPayOrderDto, HttpServletRequest request) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        //查询学生信息
        StudentVo studentVo = studentControllerClient.queryStudentInfo(userInfo.getSchoolCode(), addPayOrderDto.getCardNumber()).getResult();
        Preconditions.checkArgument(studentVo != null, "查询学生信息失败");
        //生成我方订单号信息
        AddOrderDto addOrderDto = new AddOrderDto();
        //预生成第三方订单
        addOrderDto.setThirdOrderNo("test_no");
        //学校id
        addOrderDto.setSchoolId(userInfo.getSchoolId());
        //学校code
        addOrderDto.setSchoolCode(userInfo.getSchoolCode());
        //学校名称
        addOrderDto.setSchoolName(userInfo.getSchoolName());
        //家长id
        addOrderDto.setUserId(userInfo.getFamilyId());
        //家长姓名
        addOrderDto.setUserName(userInfo.getFamilyName());
        //学生卡号
        addOrderDto.setCardNumber(addPayOrderDto.getCardNumber());
        //用户类型(默认为家长，此处购买服务的用户只会是为家长不需要做判决)
        addOrderDto.setUserType(BaseUserTypeEnum.FAMILY);
        //微信openid
        addOrderDto.setOpenId(addPayOrderDto.getOpenId());
        //订单总金额
        addOrderDto.setTotalMoney(addPayOrderDto.getTotalMoney());
        //订单金额
        addOrderDto.setOrderMoney(addPayOrderDto.getOrderMoney());
        //支付金额
        addOrderDto.setPayMoney(addPayOrderDto.getPayMoney());
        //交易状态，默认为进行中
        addOrderDto.setTradeStatus(OrderTradeStatusEnum.TRADING);
        //支付状态，默认为未支付
        addOrderDto.setPayStatus(OrderPayStatusEnum.NO_PAY);
        //业务状态,默认为未处理
        addOrderDto.setBusinessStatus(BusinessStatusEnum.NO_PROCESS);
        //业务类型，管控服务
        addOrderDto.setBusinessType(OrderBusinessTypeEnum.CONTROL_SERVICE);
        //支付渠道，微信支付
        addOrderDto.setPayType(OrderPayTypeEnum.WECHAT_PAY);
        //订单类型，JSAPI
        addOrderDto.setTradeType(OrderTradeTypeEnum.WECHAT_JSAPI);
        //支付时间
        addOrderDto.setPayTime(new Date());
        //支付结束时间，此处默认成支付时间，支付结束后设置时间
        addOrderDto.setPayEndTime(new Date());
        //商品id
        addOrderDto.setProductId(addPayOrderDto.getProductId());
        //操作人
        addOrderDto.setOperator(addPayOrderDto.getOperator());
        //操作人名称
        addOrderDto.setOperatorName(addPayOrderDto.getOperatorName());
        //备注
        addOrderDto.setRemark(addPayOrderDto.getRemark());

        //1.增加订单
        Wrapper wrapper = ordersControllerClient.createOrder(addOrderDto);
        if (null == wrapper.getResult()) {
            return WrapMapper.error("订单添加失败");
        }
        //我方订单号
        Long orderNo = Long.valueOf(wrapper.getResult().toString());
        //查询商品信息详情
        ProductDetailsVo detailsVo = productControllerClient.findProductDetails(Long.valueOf(addOrderDto.getProductId())).getResult();
        //增加商品明细记录
        if (null != detailsVo && ProductTypeEnum.GROUP.getCode().equals(detailsVo.getProductType())) {
            //套餐
            detailsVo.getProductList().stream().forEach(item -> {
                AddOrderItemDto addOrderItemDto = new AddOrderItemDto();
                BeanUtils.copyProperties(item, addOrderItemDto);
                addOrderItemDto.setProductId(item.getId());
                addOrderItemDto.setOrderNo(orderNo);
                orderItemControllerClient.addOrderItem(addOrderItemDto);
            });
        } else {
            //单品
            AddOrderItemDto addOrderItemDto = new AddOrderItemDto();
            BeanUtils.copyProperties(detailsVo, addOrderItemDto);
            addOrderItemDto.setProductId(detailsVo.getId());
            addOrderItemDto.setOrderNo(orderNo);
            orderItemControllerClient.addOrderItem(addOrderItemDto);
        }
        //内部订单下单成功，到微信端下单预订单
        WxPayJsOrderDto wxPayJsOrderDto = new WxPayJsOrderDto();
        //我方订单号
        wxPayJsOrderDto.setOrderNo(orderNo.toString());
        //商品金额
        wxPayJsOrderDto.setMoney(addPayOrderDto.getPayMoney());
        //商品描述
        wxPayJsOrderDto.setBody(addPayOrderDto.getBody());
        //ip地址
        wxPayJsOrderDto.setIp(request.getRemoteAddr());
        //openid
        wxPayJsOrderDto.setOpenid(addPayOrderDto.getOpenId());
        //返回预订单信息
        String jsOrderResponse = wechatJsPayControllerClient.wechatJsPayOrder(wxPayJsOrderDto).getResult();
        return WrapMapper.ok(jsOrderResponse);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ApiOperation(value = "根据微信code返回授权信息", response = String.class)
    public Object auth(@RequestParam("code") String code) {
        return WrapMapper.ok(wechatJsPayControllerClient.auth(code).getResult());
    }

    /**
     * @Description: redirectUri回调地址
     * @Author: Kang
     * @Date: 2019/6/17 19:12
     */
    @RequestMapping(value = "/getWechatUrl", method = RequestMethod.GET)
    @ApiOperation(value = "返回微信支付授权地址信息", response = String.class)
    public Object getWechatUrl(@RequestParam("redirectUri") String redirectUri) {

        return WrapMapper.ok(wechatJsPayControllerClient.getWechatUrl(redirectUri).getResult());
    }
}
