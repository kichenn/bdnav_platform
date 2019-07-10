package com.bdxh.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.service.WalletRechargeService;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-07-10 18:36:58
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