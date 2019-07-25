package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.fallback.WalletAccountControllerFallback;
import com.bdxh.wallet.vo.MyWalletVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 钱包账户
 * @Author: Kang
 * @Date: 2019/7/15 17:23
 */
@Service
@FeignClient(value = "wallet-provider-cluster", fallback = WalletAccountControllerFallback.class)
public interface WalletAccountControllerClient {

    /**
     * 查询钱包信息
     */
    @GetMapping("/walletAccount/findWalletByCardNumberAndSchoolCode")
    @ResponseBody
    Wrapper<WalletAccount> findWalletByCardNumberAndSchoolCode(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode);

    /**
     * 创建钱包
     */
    @PostMapping("/walletAccount/createWallet")
    @ResponseBody
    Wrapper createWallet(@Validated @RequestBody CreateWalletDto createWalletDto);

    /**
     * 我的钱包
     *
     * @return
     */
    @PostMapping("/walletAccount/myWallet")
    @ResponseBody
    Wrapper<MyWalletVo> myWallet(@RequestBody WalletAccount walletAccountParam);

    /**
     * 查询是否设置支付密码
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @GetMapping("/walletAccount/findPayPwd")
    @ResponseBody
    Wrapper findPayPwd(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber);

    /**
     * 设置支付密码
     *
     * @param setPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/setPayPwd")
    @ResponseBody
    Wrapper setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto);

    /**
     * 修改支付密码
     *
     * @param modifyPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/modifyPayPwd")
    @ResponseBody
    Wrapper modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto);

    /**
     * 忘记支付密码
     *
     * @param forgetPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/forgetPayPwd")
    @ResponseBody
    Wrapper forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto);

    /**
     * 发送忘记支付密码，验证码信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/walletAccount/forgetPayPwdSendCode")
    @ResponseBody
    Wrapper forgetPayPwdSendCode(@RequestParam("phone") String phone);

    /**
     * 设置小额免密支付
     *
     * @param setNoPwdPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/setNoPwdPay")
    @ResponseBody
    Wrapper setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto);


    /**
     * 查询小额免密支付金额值
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @PostMapping("/walletAccount/findNoPwdPay")
    @ResponseBody
    Wrapper findNoPwdPay(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber);

    /**
     * 账户钱包充值
     *
     * @param modifyAccountMoeny
     * @return
     */
    @PostMapping("/walletAccount/rechargeWalletAccount")
    @ResponseBody
    Wrapper rechargeWalletAccount(@RequestBody @Validated ModifyAccountMoeny modifyAccountMoeny);


    /**
     * 分页查询
     *
     * @param queryWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/findWalletAccountInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<WalletAccount>> findWalletAccountInCondition(@RequestBody QueryWalletAccount queryWalletAccount);


    /**
     * 添加
     *
     * @param addWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/addWalletAccount", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addWalletAccount(@RequestBody AddWalletAccount addWalletAccount);


    /**
     * 修改
     *
     * @param modifyWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/modifyWalletAccount", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyWalletAccount(@RequestBody ModifyWalletAccount modifyWalletAccount);


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/walletAccount/delWalletAccount", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delWalletAccount(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id);

    /**
     * 根据id查询详情
     *
     * @return
     */
    @RequestMapping(value = "/walletAccount/findWalletAccountById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<WalletAccount> findWalletAccountById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id);


}
