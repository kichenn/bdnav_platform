package com.bdxh.weixiao.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JsOrderPayResponse;
import com.bdxh.order.dto.*;
import com.bdxh.order.enums.*;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.user.entity.Family;
import com.bdxh.user.feign.FamilyControllerClient;
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
import java.util.SortedMap;


/**
 * @Description: 微校购买商品 支付
 * @Author: Kang
 * @Date: 2019/6/21 14:32
 */
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

    @Autowired
    private FamilyControllerClient familyControllerClient;


    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "JS统一下单购买的商品", response = String.class)
    public Object wechatJsPayOrder(@Validated @RequestBody AddPayOrderDto addPayOrderDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        //查询学生信息
        StudentVo studentVo = studentControllerClient.queryStudentInfo(userInfo.getSchoolCode(), addPayOrderDto.getCardNumber()).getResult();
        Preconditions.checkArgument(studentVo != null, "查询学生信息失败");
        //查询家长信息
        Family family = familyControllerClient.queryFamilyInfoById(userInfo.getFamilyId()).getResult();
        Preconditions.checkArgument(family != null, "查询家长信息失败");
        //查询商品信息
        ProductDetailsVo productDetailsVo = productControllerClient.findProductDetails(Long.valueOf(addPayOrderDto.getProductId())).getResult();
        Preconditions.checkArgument(productDetailsVo != null, "查询商品信息失败");

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
        addOrderDto.setUserId(family.getId());
        //家长姓名
        addOrderDto.setUserName(family.getName());
        //学生卡号
        addOrderDto.setCardNumber(addPayOrderDto.getCardNumber());
        //用户类型(默认为家长，此处购买服务的用户只会是为家长不需要做判决)
        addOrderDto.setUserType(BaseUserTypeEnum.FAMILY);
        //微信openid
        addOrderDto.setOpenId(addPayOrderDto.getOpenId());
        //如果商品支付金额 与售价不相等 ，说明金额异常
        if (!productDetailsVo.getProductSellPrice().equals(addPayOrderDto.getPayMoney())) {
            return WrapMapper.error("商品支付金额与售价不相等 ，金额异常");
        }
        //订单总金额 (商品定价)
        addOrderDto.setTotalMoney(productDetailsVo.getProductPrice());
        //订单金额（商品售价）
        addOrderDto.setOrderMoney(productDetailsVo.getProductSellPrice());
        //支付金额(根据商品id查询并非前端传递)
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
        //支付结束时间，预订单时间默认有效2小时，支付最晚时间应该是当前时间增加俩小时
        addOrderDto.setPayEndTime(DateUtil.addDateMinut(addOrderDto.getPayTime(), 2));
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
        wxPayJsOrderDto.setIp(addPayOrderDto.getIp());
        //openid
        wxPayJsOrderDto.setOpenid(addPayOrderDto.getOpenId());

        Wrapper jsOrderWrapper = wechatJsPayControllerClient.wechatJsPayOrder(wxPayJsOrderDto);

        //返回预下单信息
        JSONObject jsonObject = JSONObject.parseObject(jsOrderWrapper.getResult().toString());

        //封装前端 微信 H5吊起支付对象,返回预订单信息
        JsOrderPayResponse jsOrderPayResponse = new JsOrderPayResponse();
        jsOrderPayResponse.setAppId(jsonObject.getString("appid"));
        jsOrderPayResponse.setTimeStamp(System.currentTimeMillis() / 1000L + "");
        jsOrderPayResponse.setNonceStr(jsonObject.getString("nonce_str"));
        jsOrderPayResponse.setPackages("prepay_id=" + jsonObject.getString("prepay_id"));
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(jsOrderPayResponse);
        if (paramMap.containsKey("packages")) {
            paramMap.put("package", paramMap.get("packages"));
            paramMap.remove("packages");
        }
        if (paramMap.containsKey("paySign")) {
            paramMap.remove("paySign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.APP_KEY);
        jsOrderPayResponse.setPaySign(sign);

        //我方订单号绑定微信第三方订单号信息
        ModifyPayOrderDto modifyPayOrderDto = new ModifyPayOrderDto();
        modifyPayOrderDto.setOrderNo(orderNo);
        modifyPayOrderDto.setThirdOrderNo(jsonObject.getString("prepay_id"));
        modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAYING);
        modifyPayOrderDto.setBusinessStatus(BusinessStatusEnum.HASE_PROCESS);
        ordersControllerClient.modifyBindOrder(modifyPayOrderDto);

        return WrapMapper.ok(jsOrderPayResponse);
    }

    @RequestMapping(value = "/continueOrder", method = RequestMethod.POST)
    @ApiOperation(value = "JS继续支付（开始未完成支付）", response = String.class)
    public Object continueOrder(@RequestParam("orderNo") String orderNo, @RequestParam("prepayId") String prepayId) {
        return WrapMapper.ok(wechatJsPayControllerClient.continueOrder(orderNo, prepayId).getResult());
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ApiOperation(value = "根据微信code返回授权信息", response = String.class)
    public Object auth(@RequestParam("code") String code) {
        return WrapMapper.ok(wechatJsPayControllerClient.auth(code).getResult());
    }

    /**
     * @Description: 前端redirectUri回调地址
     * @Author: Kang
     * @Date: 2019/6/17 19:12
     */
    @RequestMapping(value = "/getWechatUrl", method = RequestMethod.GET)
    @ApiOperation(value = "返回微信支付授权地址信息", response = String.class)
    public Object getWechatUrl(@RequestParam("redirectUri") String redirectUri) {
        return WrapMapper.ok(wechatJsPayControllerClient.getWechatUrl(redirectUri).getResult());
    }
}
