package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.fallback.WalletRechargeControllerFallback;
import com.bdxh.wallet.fallback.WalletAccountControllerFallback;
import com.bdxh.wallet.fallback.WalletRechargeControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 消费记录相关
 * @Author: Kang
 * @Date: 2019/7/15 11:53
 */
@Service
@FeignClient(value = "wallet-provider-cluster", path = "/walletRecharge", fallback = WalletRechargeControllerFallback.class)
public interface WalletRechargeControllerClient {

    /**
     * 添加充值记录信息
     *
     * @param addWalletRechargeDto
     * @return
     */
    @PostMapping("/addWalletRecharge")
    @ResponseBody
    Wrapper<String> addWalletRecharge(@RequestBody @Validated AddWalletRechargeDto addWalletRechargeDto);

    /**
     * 绑定充值记录并修改相关状态
     *
     * @param modifyWalletRechargeDto
     */
    @PostMapping("/modifyWalletRechargeByOrderNo")
    @ResponseBody
    Wrapper<Boolean> modifyWalletRechargeByOrderNo(@RequestBody @Validated ModifyWalletRechargeDto modifyWalletRechargeDto);

    /**
     * 绑定充值记录并修改相关状态
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/findWalletRechargeByOrderNo")
    @ResponseBody
    Wrapper<WalletRecharge> findWalletRechargeByOrderNo(@RequestParam("orderNo") Long orderNo);
}
