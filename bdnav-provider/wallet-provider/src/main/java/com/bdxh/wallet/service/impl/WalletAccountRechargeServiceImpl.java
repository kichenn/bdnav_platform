package com.bdxh.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.base.enums.PayCardStatusEnum;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.dto.WxPayAppOrderDto;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatAppPayControllerClient;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import com.bdxh.wallet.dto.WalletPayAppOrderDto;
import com.bdxh.wallet.dto.WalletPayJsOrderDto;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.persistence.WalletAccountRechargeMapper;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.bdxh.wallet.vo.WalletAppOrderVo;
import com.bdxh.wallet.vo.WalletJsOrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-30 19:24
 **/
@Service
@Slf4j
public class WalletAccountRechargeServiceImpl  extends BaseService<WalletAccountRecharge> implements WalletAccountRechargeService{

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private WalletAccountRechargeMapper walletAccountRechargeMapper;

    @Autowired
    private WechatAppPayControllerClient wechatAppPayControllerClient;

    @Autowired
    private WechatJsPayControllerClient wechatJsPayControllerClient;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public WalletAccountRecharge getByOrderNO(Long orderNo) {
        WalletAccountRecharge walletAccountRecharge=walletAccountRechargeMapper.getByOrderNo(orderNo);
        return walletAccountRecharge;
    }

    @Override
    public int changeRechargeLog(WalletAccountRecharge walletAccountRecharge) {
        int flag= walletAccountRechargeMapper.updateByPrimaryKeySelective(walletAccountRecharge);
        return flag;
    }

    @Override
    public PageInfo<WalletAccountRecharge> getRechargeLogPage(Map<String,Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WalletAccountRecharge> rechargeLogs = walletAccountRechargeMapper.getByCondition(param);
        PageInfo<WalletAccountRecharge> pageInfo=new PageInfo<>(rechargeLogs);
        return pageInfo;
    }

    @Override
    public void updatePaying(Long orderNo,Byte status) {
        int result = walletAccountRechargeMapper.updatePaying(orderNo,status);
        Preconditions.checkArgument(result>=1,"更新支付中状态失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WalletAppOrderVo appOrder(WalletPayAppOrderDto walletPayAppOrderDto) {
        //插入充值记录
        WalletAccountRecharge recharge=new WalletAccountRecharge();
        recharge.setSchoolCode(walletPayAppOrderDto.getSchoolCode());
        recharge.setUserId(walletPayAppOrderDto.getUserId());
        recharge.setUserName(walletPayAppOrderDto.getUserName());
        recharge.setCardNumber(walletPayAppOrderDto.getCardNumber());
        Long orderNo = snowflakeIdWorker.nextId();
        recharge.setOrderNo(orderNo);
        recharge.setRechargeMoney(walletPayAppOrderDto.getMoney());
        recharge.setOrderType(WechatPayConstants.APP.trade_type);
        recharge.setStatus(new Byte("1"));
        walletAccountRechargeMapper.insert(recharge);
        //调用微信统一下单接口
        WxPayAppOrderDto wxPayAppOrderDto = new WxPayAppOrderDto();
        wxPayAppOrderDto.setIp(walletPayAppOrderDto.getIp());
        wxPayAppOrderDto.setMoney(walletPayAppOrderDto.getMoney());
        wxPayAppOrderDto.setOrderNo(String.valueOf(orderNo));
        wxPayAppOrderDto.setBody(walletPayAppOrderDto.getBody());
        Wrapper wrapper = wechatAppPayControllerClient.wechatAppPayOrder(wxPayAppOrderDto);
        Preconditions.checkArgument(wrapper.getCode()==200,"调用微信APP支付统一下单失败");
        String prepayId = (String) wrapper.getResult();
        WalletAppOrderVo walletAppOrderVo = new WalletAppOrderVo();
        walletAppOrderVo.setTradeType(WechatPayConstants.APP.trade_type);
        walletAppOrderVo.setOrderNo(orderNo);
        walletAppOrderVo.setPrepayId(prepayId);
        return walletAppOrderVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WalletJsOrderVo jsOrder(WalletPayJsOrderDto walletPayJsOrderDto) {
        //插入充值记录
        WalletAccountRecharge recharge=new WalletAccountRecharge();
        recharge.setSchoolCode(walletPayJsOrderDto.getSchoolCode());
        recharge.setUserId(walletPayJsOrderDto.getUserId());
        recharge.setUserName(walletPayJsOrderDto.getUserName());
        recharge.setCardNumber(walletPayJsOrderDto.getCardNumber());
        Long orderNo = snowflakeIdWorker.nextId();
        recharge.setOrderNo(orderNo);
        recharge.setRechargeMoney(walletPayJsOrderDto.getMoney());
        recharge.setOrderType(WechatPayConstants.JS.trade_type);
        recharge.setStatus(new Byte("1"));
        walletAccountRechargeMapper.insert(recharge);
        //调用微信统一下单接口
        WxPayJsOrderDto wxPayJsOrderDto = new WxPayJsOrderDto();
        wxPayJsOrderDto.setIp(walletPayJsOrderDto.getIp());
        wxPayJsOrderDto.setMoney(walletPayJsOrderDto.getMoney());
        wxPayJsOrderDto.setOrderNo(String.valueOf(orderNo));
        wxPayJsOrderDto.setBody(walletPayJsOrderDto.getBody());
        wxPayJsOrderDto.setOpenid(walletPayJsOrderDto.getOpenid());
        Wrapper wrapper = wechatJsPayControllerClient.wechatJsPayOrder(wxPayJsOrderDto);
        Preconditions.checkArgument(wrapper.getCode()==200,"调用微信JS支付统一下单失败");
        String prepayId = (String) wrapper.getResult();
        WalletJsOrderVo walletJsOrderVo = new WalletJsOrderVo();
        walletJsOrderVo.setAppId(WechatPayConstants.JS.app_id);
        String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        walletJsOrderVo.setTimeStamp(timeStamp);
        walletJsOrderVo.setNonceStr(ObjectUtil.getUuid());
        SortedMap<String, String> sortedMap = BeanToMapUtil.objectToTreeMap(walletJsOrderVo);
        sortedMap.put("package","prepay_id=" + prepayId);
        String sing = MD5.md5(BeanToMapUtil.mapToString(sortedMap)+"&key="+WechatPayConstants.JS.app_key);
        walletJsOrderVo.setPaySign(sing);
        walletJsOrderVo.setPackages("prepay_id=" + prepayId);
        walletJsOrderVo.setOrderNo(String.valueOf(orderNo));
        return walletJsOrderVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void rechargeWallet(Long orderNo, String thirdOrderNo, Byte status) {
        WalletAccountRecharge walletAccountRecharge = walletAccountRechargeMapper.getByOrderNo(orderNo);
        if (walletAccountRecharge!=null&&walletAccountRecharge.getStatus().intValue()< PayCardStatusEnum.RECHARGE_SUCCESS.getCode()){
            //设置充值状态
            walletAccountRecharge.setStatus(status);
            //设置微信订单号
            walletAccountRecharge.setThirdOrderNo(thirdOrderNo);
            //更新充值状态
            walletAccountRechargeMapper.updateByPrimaryKeySelective(walletAccountRecharge);
            //支付成功，发起一卡通充值
            if (PayCardStatusEnum.RECHARGE_SUCCESS.getCode().intValue()==status.intValue()){
                //发送消息异步处理
                String messageStr = JSON.toJSONString(walletAccountRecharge);
                Message message = new Message(RocketMqConstrants.Topic.xiancardWalletRecharge,RocketMqConstrants.Tags.xiancardWalletRecharge_add,messageStr.getBytes(Charset.forName("utf-8")));
                try {
                    defaultMQProducer.send(message);
                }catch (Exception e){
                    log.error("钱包服务充值一卡通消息发送失败："+orderNo);
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    @Override
    public List<WalletAccountRecharge> queryPayingDataForTask(Map<String, Object> param) {
        List<WalletAccountRecharge> walletAccountRecharges = walletAccountRechargeMapper.getPayingDataForTask(param);
        return walletAccountRecharges;
    }

    @Override
    public List<WalletAccountRecharge> querySerailNoNullForTask(Map<String, Object> param) {
        List<WalletAccountRecharge> walletAccountRecharges = walletAccountRechargeMapper.getSerailNoNullForTask(param);
        return walletAccountRecharges;
    }

    @Override
    public void clearRechargeLog() {
        walletAccountRechargeMapper.clearRechargeLog();
    }

}
