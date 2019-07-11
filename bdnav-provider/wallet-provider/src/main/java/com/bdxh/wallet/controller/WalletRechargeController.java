package com.bdxh.wallet.controller;

import com.bdxh.wallet.service.WalletRechargeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@Controller
@RequestMapping("/walletRecharge")
@Slf4j
@Validated
@Api(value = "WalletRecharge控制器", tags = "WalletRecharge")
public class WalletRechargeController {

	@Autowired
	private WalletRechargeService walletRechargeService;

}