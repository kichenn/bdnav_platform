package com.bdxh.wallet.controller;

import com.bdxh.common.base.enums.WxPayCardStatusEnum;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.WalletPayAppOrderDto;
import com.bdxh.wallet.dto.WalletPayJsOrderDto;
import com.bdxh.wallet.dto.WalletPayOkDto;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.bdxh.wallet.vo.WalletAppOrderVo;
import com.bdxh.wallet.vo.WalletJsOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @description: 钱包充值服务
 * @author: xuyuan
 * @create: 2019-01-14 18:32
 **/
@Controller
@RequestMapping("/walletPay")
@Slf4j
public class WalletPayController {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @RequestMapping("/appOrder")
    @ResponseBody
    public Object appOrder(@Valid WalletPayAppOrderDto walletPayAppOrderDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            WalletAppOrderVo walletAppOrderVo = walletAccountRechargeService.appOrder(walletPayAppOrderDto);
            return WrapMapper.ok(walletAppOrderVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping("/jsOrder")
    @ResponseBody
    public Object jsOrder(@Valid WalletPayJsOrderDto walletPayJsOrderDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            WalletJsOrderVo walletJsOrderVo = walletAccountRechargeService.jsOrder(walletPayJsOrderDto);
            return WrapMapper.ok(walletJsOrderVo);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
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
