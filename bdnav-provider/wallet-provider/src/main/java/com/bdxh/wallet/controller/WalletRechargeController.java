package com.bdxh.wallet.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.service.WalletRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public Object addWalletRecharge(@Validated @RequestBody AddWalletRechargeDto addWalletRechargeDto){
		WalletRecharge walletRecharge = new WalletRecharge();
		BeanUtils.copyProperties(addWalletRechargeDto, walletRecharge);
		walletRecharge.setId(snowflakeIdWorker.nextId());
		walletRecharge.setOrderNo(snowflakeIdWorker.nextId());
        return WrapMapper.ok(walletRechargeService.save(walletRecharge));
	}

	/**
	 * 删除充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 10:40
	 */
	@RequestMapping(value = "/delWalletRecharge",method = RequestMethod.GET)
	@ApiOperation(value = "删除充值记录",response = Boolean.class)
	public Object delWalletRecharge(@RequestParam String schoolCode,@RequestParam String cardNumber
			,@RequestParam Long id){
		return WrapMapper.ok(walletRechargeService.delWalletRecharge(schoolCode,cardNumber,id));
	}

	/**
	 * 修改充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 11:13
	 */
	@RequestMapping(value = "/modifyWalletRecharge",method = RequestMethod.POST)
	@ApiOperation(value = "修改充值记录",response = Boolean.class)
	public Object modifyWalletRecharge(@RequestBody ModifyWalletRechargeDto modifyWalletRecharge){
		WalletRecharge walletRecharge = new WalletRecharge();
		BeanUtils.copyProperties(modifyWalletRecharge, walletRecharge);
		return WrapMapper.ok(walletRechargeService.update(walletRecharge));
	}


	/**
	 * 根据条件分页查询充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 11:13
	 */
	@RequestMapping(value = "/findWalletRechargeByCondition",method = RequestMethod.POST)
	@ApiOperation(value = "根据条件分页查询充值记录",response = Boolean.class)
	public Object findWalletRechargeByCondition(@RequestBody QueryWalletRechargeDto queryWalletRechargeDto){
		return WrapMapper.ok(walletRechargeService.findWalletRechargeByCondition(queryWalletRechargeDto));
	}

	/**
	 * 根据id查询单条充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 11:13
	 */
	@RequestMapping(value = "/findWalletRechargeById",method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询单条充值记录",response = Boolean.class)
	public Object findWalletRechargeById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id){
		return WrapMapper.ok(walletRechargeService.findWalletRechargeById(schoolCode,cardNumber,id));
	}


	/**
	 * 导出充值记录
	 * @Author: WanMing
	 * @Date: 2019/7/15 11:13
	 */
	@RequestMapping(value = "/exportWalletRechargeList",method = RequestMethod.GET)
	@ApiOperation(value = "导出充值记录",response = Boolean.class)
	public void exportWalletRechargeList(){

	}

}