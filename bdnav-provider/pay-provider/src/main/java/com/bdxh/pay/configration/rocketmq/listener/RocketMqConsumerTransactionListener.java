package com.bdxh.pay.configration.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.ModifyPayOrderDto;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.order.enums.OrderTradeStatusEnum;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.order.vo.OrderItemVo1;
import com.bdxh.order.vo.OrderVo1;
import com.bdxh.pay.configration.redis.RedisUtil;
import com.bdxh.pay.controller.WechatCommonController;
import com.bdxh.pay.vo.WechatOrderQueryVo;
import com.bdxh.servicepermit.dto.AddPayServiceUserDto;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.servicepermit.properties.ServiceUserConstant;
import com.bdxh.user.entity.Family;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.user.vo.FamilyVo1;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.user.vo.StudentVo1;
import com.bdxh.wallet.dto.ModifyAccountMoeny;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: rocketMqConsumer 消费者监听器
 * @Author: Kang
 * @Date: 2019/4/28 18:36
 */
@Slf4j
@Component
public class RocketMqConsumerTransactionListener implements MessageListenerConcurrently {

    @Autowired
    private WechatCommonController wechatCommonController;

    @Autowired
    private OrdersControllerClient ordersControllerClient;

    @Autowired
    private OrderItemControllerClient orderItemControllerClient;

    @Autowired
    private FamilyControllerClient familyControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @Autowired
    private WalletRechargeControllerClient walletRechargeControllerClient;

    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Description: 消息监听
     * @Author: Kang
     * @Date: 2019/4/28 18:37
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                log.info("收到消息:,topic:{}, tags:{},msg:{}", topic, tags, msgBody);
                //微信支付后 异步通知处理
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                //获取我方订单
                String orderNo = jsonObject.getString("orderNo");
                //微信方订单号
                String thirdOrderNo = jsonObject.getString("thirdOrderNo");
                //支付类型
                String payType = jsonObject.getString("payType");
                if (payType.equals("1")) {
                    //家长端服务购买
                    log.info("服务购买。。。。");
                    servicePay(orderNo);
                } else if (payType.equals("2")) {
                    //钱包充值
                    log.info("钱包充值。。。。");
                    wlletRechargePay(orderNo);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理微信回调异常:" + e.getMessage());
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 钱包充值
     */
    public void wlletRechargePay(String orderNo) throws Exception {
        //根据订单查询微信方相关信息
        Wrapper wrapper = (Wrapper) wechatCommonController.wechatAppPayOrderQuery(orderNo);
        log.info("查询微信订单详情信息:{}", JSONObject.toJSONString(wrapper));
        if (wrapper.getCode() == Wrapper.SUCCESS_CODE) {
            //查询我方订单信息
            WalletRecharge walletRecharge = walletRechargeControllerClient.findWalletRechargeByOrderNo(Long.valueOf(orderNo)).getResult();
            log.info("查询我方订单成功:{}", JSONObject.toJSONString(walletRecharge));
            //查询成功
            WechatOrderQueryVo wechatOrderQueryVo = (WechatOrderQueryVo) wrapper.getResult();
            log.info("查询三方订单成功:{}", JSONObject.toJSONString(wechatOrderQueryVo));

            //我方订单号绑定微信订单号信息
            ModifyWalletRechargeDto modifyWalletRechargeDto = new ModifyWalletRechargeDto();
            modifyWalletRechargeDto.setOrderNo(Long.valueOf(orderNo));
            //将微信预订单修改为微信实际订单信息
            modifyWalletRechargeDto.setOutOrderNo(wechatOrderQueryVo.getThirdOrderNo());
            //修改充值记录信息
            switch (wechatOrderQueryVo.getPayResult()) {
                case "SUCCESS":
                    //支付成功
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAY_SUCCESS.getCode());
                    break;
                case "NOTPAY":
                    //未支付
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.NO_PAY.getCode());
                    break;
                case "CLOSED":
                    //已关闭
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAY_CLOSE.getCode());
                    break;
                case "USERPAYING":
                    //支付中
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAYING.getCode());
                    break;
                case "PAYERROR":
                    //支付失败;
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAY_FAIL.getCode());
                    break;
                case "REFUND":
                    //已转入退款
                    modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAY_REFUND.getCode());
                    break;
            }
            Boolean modifyWalletResult = walletRechargeControllerClient.modifyWalletRechargeByOrderNo(modifyWalletRechargeDto).getResult();
            if (modifyWalletResult && OrderPayStatusEnum.PAY_SUCCESS.getCode().equals(modifyWalletRechargeDto.getRechargeStatus())) {
                //支付成功 并且修改 钱包充值状态 成功时，增加钱包金额值
                ModifyAccountMoeny modifyAccountMoeny = new ModifyAccountMoeny();
                modifyAccountMoeny.setSchoolCode(walletRecharge.getSchoolCode());
                modifyAccountMoeny.setCardNumber(walletRecharge.getCardNumber());
                modifyAccountMoeny.setAmount(walletRecharge.getRechargeAmount());
                walletAccountControllerClient.rechargeWalletAccount(modifyAccountMoeny);
                log.info("学校schoolCode:{},用户cardNumber:{},支付成功，增加钱包金额值:{}", modifyAccountMoeny.getSchoolCode(), modifyAccountMoeny.getCardNumber(), modifyAccountMoeny.getAmount());
            }
        }

    }

    /**
     * 家长端服务购买
     */
    public void servicePay(String orderNo) throws Exception {
        //根据订单号查询订单信息
        OrderVo1 orderVo = ordersControllerClient.findOrderByOrderNo1(Long.valueOf(orderNo)).getResult();
        Wrapper wrapper = (Wrapper) wechatCommonController.wechatAppPayOrderQuery(orderNo);
        log.info("查询微信订单详情信息:{}", JSONObject.toJSONString(wrapper));
        if (wrapper.getCode() == Wrapper.SUCCESS_CODE) {
            //查询成功
            WechatOrderQueryVo wechatOrderQueryVo = (WechatOrderQueryVo) wrapper.getResult();
            log.info("查询订单成功:{}", JSONObject.toJSONString(wechatOrderQueryVo));
            //订单与订单子项
            List<OrderItemVo1> orderItems = orderItemControllerClient.findOrderItemByOrderNo1(orderVo.getOrderNo()).getResult();
            //修改订单信息，并且增加相应商品权限，并重新授权
            ModifyPayOrderDto modifyPayOrderDto = new ModifyPayOrderDto();
            //我方订单
            modifyPayOrderDto.setOrderNo(orderVo.getOrderNo());
            //将微信预订单修改为微信实际订单信息
            modifyPayOrderDto.setThirdOrderNo(wechatOrderQueryVo.getThirdOrderNo());
            //支付结束时间
            modifyPayOrderDto.setPayEndTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //业务状态
            modifyPayOrderDto.setBusinessStatus(BusinessStatusEnum.YES_PROCESS);
            switch (wechatOrderQueryVo.getPayResult()) {
                case "SUCCESS":
                    //支付成功
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAY_SUCCESS);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.SUCCESS);
                    break;
                case "NOTPAY":
                    //未支付
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.NO_PAY);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.TRADING);
                    break;
                case "CLOSED":
                    //已关闭
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAY_CLOSE);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.CANCLE);
                    break;
                case "USERPAYING":
                    //支付中
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAYING);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.TRADING);
                    break;
                case "PAYERROR":
                    //支付失败;
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAY_FAIL);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.DELETE);
                    break;
                case "REFUND":
                    //已转入退款
                    modifyPayOrderDto.setPayStatus(OrderPayStatusEnum.PAY_REFUND);
                    modifyPayOrderDto.setTradeStatus(OrderTradeStatusEnum.DELETE);
                    break;
            }
            modifyPayOrderDto.setBusinessStatus(BusinessStatusEnum.YES_PROCESS);
            //修改订单状态
            Boolean result = ordersControllerClient.modifyBindOrder(modifyPayOrderDto).getResult();
            if (result) {
                //查询家长信息 (此处一定有值，新增订单已效验过)
                FamilyVo1 family = familyControllerClient.queryFamilyInfoById1(orderVo.getUserId()).getResult();
                //查询学生信息(此处一定有值，新增订单已效验过)
                StudentVo1 studentVo = studentControllerClient.queryStudentInfo1(orderVo.getSchoolCode(), orderVo.getCardNumber()).getResult();
                //添加各个不同商品的权限
                for (OrderItemVo1 orderItem : orderItems) {
                    //增加商品权限
                    AddPayServiceUserDto addPayServiceUserDto = new AddPayServiceUserDto();
                    addPayServiceUserDto.setSchoolId(orderVo.getSchoolId());
                    addPayServiceUserDto.setSchoolCode(orderVo.getSchoolCode());
                    addPayServiceUserDto.setSchoolName(orderVo.getSchoolName());
                    addPayServiceUserDto.setFamilyId(family.getId());
                    addPayServiceUserDto.setCardNumber(family.getCardNumber());
                    addPayServiceUserDto.setFamilyName(family.getName());
                    addPayServiceUserDto.setStudentNumber(orderVo.getCardNumber());
                    addPayServiceUserDto.setStudentName(studentVo.getSName());
                    addPayServiceUserDto.setDays(ServiceUserConstant.PAY_DAYS);
                    addPayServiceUserDto.setProductId(orderItem.getProductId());
                    addPayServiceUserDto.setProductName(orderItem.getProductName());
                    addPayServiceUserDto.setOrderNo(orderItem.getOrderNo());
                    addPayServiceUserDto.setRemark("微信购买");
                    serviceUserControllerClient.createPayService(addPayServiceUserDto);
                    //服务权限添加完成，重新登录授权
                    log.info("服务权限添加完成，重新登录授权------------");
                }
                log.info("购买后,移除token:{}", family.getId());
                redisUtil.delete("weixiao_token:" + family.getId());
            }
        } else {
            log.error("查询此订单异常:{}", wrapper.getMessage());
        }
    }
}
