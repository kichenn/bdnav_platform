package com.bdxh.wallet.controller;

import com.bdxh.common.base.enums.WxPayCardStatusEnum;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.WalletPayOkDto;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wallet")
@Slf4j
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
    public Object addRechargeLog(@RequestParam(name="schoolCode") String schoolCode, @RequestParam(name="userId") Long userId,
                                 @RequestParam(name="userName") String userName, @RequestParam(name="cardNumber") String cardNumber,
                                 @RequestParam(name="amount") BigDecimal amount, @RequestParam("orderType") String orderType,
                                 @RequestParam(name = "orderStatus",defaultValue = "1") Byte orderStatus){
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
            Preconditions.checkArgument(userId!=null,"用户id不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(userName),"姓名不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(cardNumber),"学号不能为空");
            Preconditions.checkArgument(amount!=null&&amount.doubleValue()>0,"金额输入不正确");
            Preconditions.checkArgument(StringUtils.isNotEmpty(orderType),"订单类型不能为空");
            WalletAccountRecharge recharge=new WalletAccountRecharge();
            recharge.setSchoolCode(schoolCode);
            recharge.setUserId(userId);
            recharge.setUserName(userName);
            recharge.setCardNumber(cardNumber);
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
    public Object changeRechargeLog(@RequestParam(name="orderNo") Long orderNo, @RequestParam(name="status") Byte status){
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
     * 微信回调时更改用户充值记录状态及第三方支付号
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
     * 根据用户条件查询充值记录
     * @param
     * @return
     */
    @RequestMapping("/rechargeLogPage")
    @ResponseBody
    public Object changeRechargeLog(HttpServletRequest request){
        try {
            String schoolCode=request.getParameter("schoolCode");
            String userId=request.getParameter("userId");
            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
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
            param.put("schoolCode",schoolCode);
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
    public Object getLogByOrderNo(@RequestParam("orderNo") Long orderNo){
        try {
            WalletAccountRecharge walletAccountRecharge = walletAccountRechargeService.getByOrderNO(orderNo);
            return WrapMapper.ok(walletAccountRecharge);
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据订单号修改支付状态中
     * @param
     * @return
     */
    @RequestMapping("/updatePaying")
    @ResponseBody
    public Object updatePaying(@RequestParam("orderNo") Long orderNo,@RequestParam("status") Byte status){
        try {
            Preconditions.checkNotNull(orderNo,"订单号不能为空");
            Preconditions.checkNotNull(status,"订单状态不能为空");
            walletAccountRechargeService.updatePaying(orderNo,status);
            return WrapMapper.ok();
        }catch (Exception e){
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 用户支付完成请求接口
     * @param wxPayOkDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/ok")
    @ResponseBody
    public Object wechatAppPayOk(@Valid WalletPayOkDto wxPayOkDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        //更新订单状态
        try {
            Byte status=wxPayOkDto.getStatus();
            if(status.intValue()==1){
                walletAccountRechargeService.updatePaying(wxPayOkDto.getOrderNo(), WxPayCardStatusEnum.PAYING.getCode());
            }else if (status.intValue()==2){
                walletAccountRechargeService.updatePaying(wxPayOkDto.getOrderNo(), WxPayCardStatusEnum.PAY_FAIL.getCode());
            }
            return WrapMapper.ok("更新支付中状态成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

}

