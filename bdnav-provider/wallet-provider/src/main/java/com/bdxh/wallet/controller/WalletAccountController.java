package com.bdxh.wallet.controller;

import com.bdxh.wallet.service.WalletAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@RestController
@RequestMapping("/walletAccount")
@Slf4j
@Validated
@Api(value = "钱包账户", tags = "钱包账户交互API")
public class WalletAccountController {

    @Autowired
    private WalletAccountService walletAccountService;


    @GetMapping("/myWallet")
    @ApiOperation(value = "我的钱包", response = Boolean.class)
    public Object myWallet(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode) {

        return null;
    }
}