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

@Service
@FeignClient(value = "wallet-provider-cluster", fallback = WalletAccountControllerFallback.class)
public interface WalletAccountControllerClient {

    /**
     * 我的钱包
     *
     * @param cardNumber
     * @param schoolCode
     * @return
     */
    @GetMapping("/walletAccount/myWallet")
    @ResponseBody
    Wrapper<MyWalletVo> myWallet(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode);

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
     * @param forgetPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/forgetPayPwd")
    @ResponseBody
    Wrapper forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto);

    /**
     * 发送忘记支付密码，验证码信息
     * @param phone
     * @return
     */
    @GetMapping("/walletAccount/forgetPayPwdSendCode")
    @ResponseBody
    Wrapper forgetPayPwdSendCode(@RequestParam("phone") String phone);

    /**
     * 设置小额免密支付
     * @param setNoPwdPayPwdDto
     * @return
     */
    @PostMapping("/walletAccount/setNoPwdPay")
    @ResponseBody
    Wrapper setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto);


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


}
