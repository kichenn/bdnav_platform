package com.bdxh.wallet.controller;

import com.bdxh.common.utils.AESUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.ModifyPayPwdDto;
import com.bdxh.wallet.dto.NoPwdPayPwdDto;
import com.bdxh.wallet.dto.SetPayPwdDto;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.service.WalletAccountService;
import com.bdxh.wallet.vo.MyWalletVo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@RestController
@RequestMapping("/walletAccount")
@Slf4j
@Validated
@Api(value = "钱包账户", tags = "钱包账户交互API")
public class WalletAccountController {

    @Autowired
    private WalletAccountService walletAccountService;

    @GetMapping("/myWallet")
    @ApiOperation(value = "我的钱包", response = MyWalletVo.class)
    public Object myWallet(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(cardNumber, schoolCode);
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");

        MyWalletVo myWalletVo = new MyWalletVo();
        myWalletVo.setIdentityType(String.valueOf(walletAccount.getUserType()));
        BeanUtils.copyProperties(walletAccount, myWalletVo);
        return WrapMapper.ok(myWalletVo);
    }

    @PostMapping("/setPayPwd")
    @ApiOperation(value = "设置支付密码", response = Boolean.class)
    public Object setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        String payPwd = new BCryptPasswordEncoder().encode(AESUtils.deCode(setPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY));
        return WrapMapper.ok(walletAccountService.setPayPwd(setPayPwdDto.getCardNumber(), setPayPwdDto.getSchoolCode(), payPwd));
    }

    @PostMapping("/modifyPayPwd")
    @ApiOperation(value = "修改支付密码", response = Boolean.class)
    public Object modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(modifyPayPwdDto.getCardNumber(), modifyPayPwdDto.getSchoolCode());
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");

        //matches(第一个参数为前端传递的值，未加密，后一个为数据库已经加密的串对比)
        if (!new BCryptPasswordEncoder().matches(AESUtils.deCode(modifyPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY), walletAccount.getPayPassword())) {
            return WrapMapper.error("支付密码输入错误请检查");
        }
        //获取新密码加密串
        String newPayPwd = new BCryptPasswordEncoder().encode(AESUtils.deCode(modifyPayPwdDto.getNewPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY));
        return WrapMapper.ok(walletAccountService.setPayPwd(modifyPayPwdDto.getCardNumber(), modifyPayPwdDto.getSchoolCode(), newPayPwd));
    }

    @PostMapping("/noPwdPay")
    @ApiOperation(value = "设置小额免密支付", response = Boolean.class)
    public Object noPwdPay(@Validated @RequestBody NoPwdPayPwdDto noPwdPayPwdDto) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(noPwdPayPwdDto.getCardNumber(), noPwdPayPwdDto.getSchoolCode());
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");

        //matches(第一个参数为前端传递的值，未加密，后一个为数据库已经加密的串对比)
        if (!new BCryptPasswordEncoder().matches(AESUtils.deCode(noPwdPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY), walletAccount.getPayPassword())) {
            return WrapMapper.error("支付密码输入错误请检查");
        }
        WalletAccount walletAccountParam = new WalletAccount();
        walletAccountParam.setUpdateDate(new Date());
        walletAccountParam.setSchoolCode(noPwdPayPwdDto.getSchoolCode());
        walletAccountParam.setCardNumber(noPwdPayPwdDto.getCardNumber());
        walletAccountParam.setQuickPayMoney(noPwdPayPwdDto.getQuickPayMoney());
        walletAccountParam.setOperator(noPwdPayPwdDto.getOperator());
        walletAccountParam.setOperatorName(noPwdPayPwdDto.getOperatorName());
        walletAccountParam.setRemark(noPwdPayPwdDto.getRemark());

        return WrapMapper.ok(walletAccountService.noPwdPay(walletAccountParam));
    }
}