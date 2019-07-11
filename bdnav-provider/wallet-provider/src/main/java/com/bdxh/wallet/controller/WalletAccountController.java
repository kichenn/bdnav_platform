package com.bdxh.wallet.controller;

import com.bdxh.wallet.service.WalletAccountService;
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
@RequestMapping("/walletAccount")
@Slf4j
@Validated
@Api(value = "WalletAccount控制器", tags = "WalletAccount")
public class WalletAccountController {

	@Autowired
	private WalletAccountService walletAccountService;

}