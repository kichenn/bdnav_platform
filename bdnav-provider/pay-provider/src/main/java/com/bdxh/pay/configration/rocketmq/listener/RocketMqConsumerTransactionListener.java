package com.bdxh.pay.configration.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.order.dto.ModifyPayOrderDto;
import com.bdxh.order.entity.OrderItem;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.order.enums.OrderTradeStatusEnum;
import com.bdxh.order.feign.OrderItemControllerClient;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.order.vo.OrderItemVo;
import com.bdxh.order.vo.OrderVo;
import com.bdxh.pay.configration.redis.RedisUtil;
import com.bdxh.pay.controller.WechatCommonController;
import com.bdxh.pay.vo.WechatOrderQueryVo;
import com.bdxh.servicepermit.dto.AddNoTrialServiceUserDto;
import com.bdxh.servicepermit.dto.AddPayServiceUserDto;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.servicepermit.properties.ServiceUserConstant;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
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
                //获取wx预订单
                String wxOrderNo = jsonObject.getString("orderNo");
                //预支付订单查询我方订单号
                OrderVo orderVo = ordersControllerClient.findThirdOrderByOrderNo(wxOrderNo).getResult();
                if (orderVo == null) {
                    log.error("预支付订单查询我方订单失败,预订单:{}", wxOrderNo);
                    continue;
                }
                //根据订单号查询订单信息
                try {
                    Wrapper wrapper = (Wrapper) wechatCommonController.wechatAppPayOrderQuery(String.valueOf(orderVo.getOrderNo()));
                    if (wrapper.getCode() == Wrapper.SUCCESS_CODE) {
                        //查询成功
                        WechatOrderQueryVo wechatOrderQueryVo = (WechatOrderQueryVo) wrapper.getResult();
                        log.info("查询订单成功:{}", JSONObject.toJSONString(wechatOrderQueryVo));
                        //订单与订单子项
                        List<OrderItemVo> orderItems = orderItemControllerClient.findOrderItemByOrderNo(orderVo.getOrderNo()).getResult();
                        //修改订单信息，并且增加相应商品权限，并重新授权
                        ModifyPayOrderDto modifyPayOrderDto = new ModifyPayOrderDto();
                        //我方订单
                        modifyPayOrderDto.setOrderNo(orderVo.getOrderNo());
                        //将微信预订单修改为微信实际订单信息
                        modifyPayOrderDto.setThirdOrderNo(wechatOrderQueryVo.getThirdOrderNo());
                        //支付结束时间
                        Date payEndTime = wechatOrderQueryVo.getTimeEnd() != null ?
                                DateUtil.format(wechatOrderQueryVo.getTimeEnd(), "yyyy-MM-dd HH:mm:ss") : null;
                        modifyPayOrderDto.setPayEndTime(payEndTime);
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
                        //修改订单状态
                        Boolean result = ordersControllerClient.modifyBindOrder(modifyPayOrderDto).getResult();
                        if (result) {
                            //查询家长信息 (此处一定有值，新增订单已效验过)
                            Family family = familyControllerClient.queryFamilyInfoById(orderVo.getUserId()).getResult();
                            //查询学生信息(此处一定有值，新增订单已效验过)
                            StudentVo studentVo = studentControllerClient.queryStudentInfo(orderVo.getSchoolCode(), orderVo.getCardNumber()).getResult();
                            //添加各个不同商品的权限
                            for (OrderItemVo orderItem : orderItems) {
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
                                addPayServiceUserDto.setProductId(orderItem.getId());
                                addPayServiceUserDto.setProductName(orderItem.getProductName());
                                addPayServiceUserDto.setOrderNo(orderItem.getOrderNo());
                                addPayServiceUserDto.setRemark("微信购买");
                                serviceUserControllerClient.createPayService(addPayServiceUserDto);
                                //服务权限添加完成，重新登录授权
                                redisUtil.delete("weixiao_token:" + addPayServiceUserDto.getFamilyId());
                            }
                        }
                    } else {
                        log.error("查询此订单异常:{}", wrapper.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("查询微信订单信息异常:{}", e.getMessage());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
