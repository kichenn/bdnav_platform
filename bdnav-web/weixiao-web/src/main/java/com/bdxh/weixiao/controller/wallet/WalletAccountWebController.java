package com.bdxh.weixiao.controller.wallet;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 钱包账户相关
 * @Author: Kang
 * @Date: 2019/7/15 15:56
 */
@Slf4j
@RestController("Web")
@Api(value = "钱包账户相关", tags = {"钱包账户相关API"})
@Validated
public class WalletAccountWebController {

    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;

    /**
     * 我的钱包
     *
     * @param cardNumber
     * @param schoolCode
     * @return
     */
    @GetMapping("/myWallet")
    public Object myWallet(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode) {
        return WrapMapper.ok(walletAccountControllerClient.myWallet(cardNumber, schoolCode));
    }

    /**
     * 设置支付密码
     *
     * @param setPayPwdDto
     * @return
     */
    @PostMapping("/setPayPwd")
    public Object setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.setPayPwd(setPayPwdDto));
    }

    /**
     * 修改支付密码
     *
     * @param modifyPayPwdDto
     * @return
     */
    @PostMapping("/modifyPayPwd")
    public Object modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.modifyPayPwd(modifyPayPwdDto));
    }

    /**
     * 忘记支付密码
     *
     * @param forgetPayPwdDto
     * @return
     */
    @PostMapping("/forgetPayPwd")
    public Object forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.forgetPayPwd(forgetPayPwdDto));
    }

    /**
     * 发送忘记支付密码，验证码信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/forgetPayPwdSendCode")
    public Object forgetPayPwdSendCode(@RequestParam("phone") String phone) {
        return WrapMapper.ok(walletAccountControllerClient.forgetPayPwdSendCode(phone));
    }

    /**
     * 设置小额免密支付
     *
     * @param setNoPwdPayPwdDto
     * @return
     */
    @PostMapping("/setNoPwdPay")
    public Object setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.setNoPwdPay(setNoPwdPayPwdDto));
    }
}
