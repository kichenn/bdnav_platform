package com.bdxh.wallet.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wallet")
@Slf4j
@Valid
public class WalletController {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    /**
     * 添加用户充值记录
     * @param userId
     * @param amount
     * @return
     */
    @RequestMapping("/addRechargeLog")
    @ResponseBody
    public Object addRechargeLog(@RequestParam(name="userId") Long userId,@RequestParam(name="amount") BigDecimal amount,
                                @RequestParam("orderType") String orderType,@RequestParam(name = "orderStatus",defaultValue = "0") Byte orderStatus){
        try {
            Preconditions.checkArgument(userId!=null,"用户信息不能为空");
            Preconditions.checkArgument(amount!=null&&amount.doubleValue()>0,"金额输入不正确");
            Preconditions.checkArgument(StringUtils.isNotEmpty(orderType),"订单类型不能为空");
            WalletAccountRecharge recharge=new WalletAccountRecharge();
            recharge.setUserId(userId);
            Long orderNo = snowflakeIdWorker.nextId();
            recharge.setOrderNo(orderNo);
            recharge.setRechargeMoney(amount);
            recharge.setStatus(Byte.valueOf("0"));
            recharge.setOrderType(orderType);
            recharge.setStatus(orderStatus);
            //调用service
            walletAccountRechargeService.save(recharge);
            return WrapMapper.ok(orderNo);
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }

    }

    /**
     *更改用户充值记录状态
     * @param orderNo
     * @return
     */
    @RequestMapping("/changeRechargeLogStatus")
    @ResponseBody
    public Object changeRechargeLog(@RequestParam(name="orderNo") Long orderNo,@RequestParam(name="status") Byte status){
        try {
            Preconditions.checkArgument(orderNo!=null,"订单号不能为空");
            Preconditions.checkArgument(status!=null,"充值状态不能为空");
            //调用service
            WalletAccountRecharge walletAccountRecharge=walletAccountRechargeService.getByOrderNO(orderNo);
            Preconditions.checkNotNull(walletAccountRecharge,"订单信息不存在");
            walletAccountRecharge.setStatus(Byte.valueOf("status"));
            walletAccountRechargeService.update(walletAccountRecharge);
            return WrapMapper.ok();
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 用户回调时更改用户充值记录状态及第三方支付号
     * @param orderNo
     * @return
     */
    @RequestMapping("/changeRechargeLog")
    @ResponseBody
    public Object changeRechargeLog(@RequestParam(name="orderNo") Long orderNo,@RequestParam(name="status") Byte status,
                                    @RequestParam(name="thirdOrderNo") String thirdOrderNo){
        try {
            Preconditions.checkArgument(orderNo!=null,"订单号不能为空");
            Preconditions.checkArgument(status!=null,"充值状态不能为空");
            Preconditions.checkArgument(StringUtils.isNoneEmpty(thirdOrderNo),"第三方支付账单号不能为空");
            //调用service
            WalletAccountRecharge walletAccountRecharge=walletAccountRechargeService.getByOrderNO(orderNo);
            Preconditions.checkNotNull(walletAccountRecharge,"订单信息不存在");
            walletAccountRecharge.setStatus(status);
            walletAccountRecharge.setThirdOrderNo(thirdOrderNo);
            walletAccountRechargeService.changeRechargeLog(walletAccountRecharge);
            return WrapMapper.ok();
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据用户id查询充值记录
     * @param
     * @return
     */
    @RequestMapping("/rechargeLogPage")
    @ResponseBody
    public Object changeRechargeLog(HttpServletRequest request){
        try {
            String userId=request.getParameter("userId");
            Preconditions.checkArgument(StringUtils.isNotEmpty(userId),"用户id不能为空");
            String pageNum = request.getParameter("pageNum");
            String pageSize = request.getParameter("pageSize");
            if (StringUtils.isEmpty(pageNum)){
                pageNum="1";
            }
            if (StringUtils.isEmpty(pageSize)){
                pageSize="15";
            }
            //调用service
            Map<String,Object> param=new HashMap<>();
            param.put("userId",Long.valueOf(userId));
            PageInfo<WalletAccountRecharge> rechargeLogPage = walletAccountRechargeService.getRechargeLogPage(param, Integer.valueOf(pageNum), Integer.valueOf(pageSize));
            return WrapMapper.ok(rechargeLogPage);
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据订单号查询充值记录
     * @param
     * @return
     */
    @RequestMapping("/getLogByOrderNo")
    @ResponseBody
    public Object getLogByOrderNo(@Param("orderNo") Long orderNo){
        try {
            WalletAccountRecharge walletAccountRecharge = walletAccountRechargeService.getByOrderNO(orderNo);
            return WrapMapper.ok(walletAccountRecharge);
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

}

