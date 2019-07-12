package com.bdxh.wallet.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.service.WalletAccountService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-07-11 09:40:52
*/
@RestController
@RequestMapping("/walletAccount")
@Slf4j
@Validated
@Api(value = "钱包账户控制器", tags = "钱包账户控制器")
public class WalletAccountController {

	@Autowired
	private WalletAccountService walletAccountService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	@RequestMapping(value = "/AddWalletAccount", method = RequestMethod.POST)
	@ApiOperation(value = "添加钱包账户信息",response = Boolean.class)
	public Object AddWalletAccount(@RequestBody AddWalletAccount addWalletAccount) {
		try {
			WalletAccount walletAccount=new WalletAccount();
			walletAccount.setId(snowflakeIdWorker.nextId());
			walletAccount.setPayPassword(new BCryptPasswordEncoder().encode(addWalletAccount.getPayPassword()));
			BeanUtils.copyProperties(addWalletAccount,walletAccount);
			Boolean result=walletAccountService.save(walletAccount)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@RequestMapping(value = "findWalletAccountInCondition", method = RequestMethod.POST)
	@ApiOperation(value = "带条件分页查询列表信息",response = WalletAccount.class)
	public Object findWalletAccountInCondition(@Validated @RequestBody QueryWalletAccount queryWalletAccount, BindingResult bindingResult) {
		//检验参数
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			Map<String, Object> param = BeanToMapUtil.objectToMap(queryWalletAccount);
			PageInfo<WalletAccount> walletAccounts = walletAccountService.findWalletAccountInCondition(param, queryWalletAccount.getPageNum(), queryWalletAccount.getPageSize());
			return WrapMapper.ok(walletAccounts);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/modifyWalletAccount", method = RequestMethod.POST)
	@ApiOperation(value = "修改钱包账户信息",response = Boolean.class)
	public Object modifyWalletAccount(@RequestBody ModifyWalletAccount modifyWalletAccount) {
		try {
			WalletAccount walletAccount=new WalletAccount();
			BeanUtils.copyProperties(modifyWalletAccount,walletAccount);
			Boolean result=walletAccountService.update(walletAccount)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@RequestMapping(value = "/delWalletAccount", method = RequestMethod.GET)
	@ApiOperation(value = "删除钱包账户信息",response = Boolean.class)
	public Object delWalletAccount(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
		try {
			Boolean result=walletAccountService.delWalletAccount(schoolCode,cardNumber,id);
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

}