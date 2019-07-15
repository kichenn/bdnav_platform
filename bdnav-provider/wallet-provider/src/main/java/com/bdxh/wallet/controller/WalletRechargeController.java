package com.bdxh.wallet.controller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

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
     * 添加充值记录信息，不在Swagger展示
     *
     * @param addWalletRechargeDto
     * @return
     */
    @ApiIgnore
    @PostMapping("/addWalletRecharge")
    @ApiOperation(value = "添加充值记录", response = String.class)
    public Object addWalletRecharge(@RequestBody @Validated AddWalletRechargeDto addWalletRechargeDto) {
        WalletRecharge walletRecharge = new WalletRecharge();
        BeanUtils.copyProperties(addWalletRechargeDto, walletRecharge);
        walletRecharge.setOrderNo(snowflakeIdWorker.nextId());
        walletRecharge.setCreateDate(new Date());
        walletRecharge.setUpdateDate(new Date());
        walletRecharge.setRechargeType(addWalletRechargeDto.getRechargeType().toString());
        walletRechargeService.save(walletRecharge);
        return WrapMapper.ok(walletRecharge.getOrderNo());
    }

    /**
     * 根据我方订单号修改相关信息，不在Swagger展示
     *
     * @param modifyWalletRechargeDto
     * @return
     */
    @ApiIgnore
    @PostMapping("/modifyWalletRechargeByOrderNo")
    @ApiOperation(value = "绑定充值记录并修改相关状态", response = String.class)
    public Object modifyWalletRechargeByOrderNo(@RequestBody @Validated ModifyWalletRechargeDto modifyWalletRechargeDto) {
        WalletRecharge walletRecharge = new WalletRecharge();
        BeanUtils.copyProperties(modifyWalletRechargeDto, walletRecharge);
        walletRecharge.setUpdateDate(new Date());
        return WrapMapper.ok(walletRechargeService.modifyWalletRechargeByOrderNo(walletRecharge));
    }

    /**
     * 根据我方订单信息，查询充值记录
     *
     * @return
     */
    @GetMapping("/findWalletRechargeByOrderNo")
    @ApiOperation(value = "绑定充值记录并修改相关状态", response = String.class)
    public Object findWalletRechargeByOrderNo(@RequestParam("orderNo") Long orderNo) {
        return WrapMapper.ok(walletRechargeService.findWalletRechargeByOrderNo(orderNo));
    }


}