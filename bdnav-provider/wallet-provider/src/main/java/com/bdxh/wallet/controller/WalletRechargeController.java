package com.bdxh.wallet.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.wallet.service.WalletRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 账户充值记录管理控制器
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@RestController
@RequestMapping("/walletRecharge")
@Slf4j
@Validated
@Api(value = "账户充值记录管理", tags = "账户充值记录管理API")
public class WalletRechargeController {

	@Autowired
	private WalletRechargeService walletRechargeService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	/**
	 * 添加充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 9:40
	 */
	@RequestMapping(value = "/addWalletRecharge",method = RequestMethod.POST)
	@ApiOperation(value = "添加充值记录",response = Boolean.class)
	public Object addWalletRecharge(){
        return null;
	}

}