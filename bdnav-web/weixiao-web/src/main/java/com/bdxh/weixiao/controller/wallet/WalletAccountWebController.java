package com.bdxh.weixiao.controller.wallet;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.bdxh.wallet.vo.MyWalletVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 钱包账户相关
 * @Author: Kang
 * @Date: 2019/7/15 15:56
 */
@Slf4j
@RestController("walletAccountWeb")
@Api(value = "钱包账户相关", tags = {"钱包账户相关API"})
@Validated
public class WalletAccountWebController {

    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @GetMapping("/myWallet")
    @ApiOperation(value = "我的钱包", response = MyWalletVo.class)
    public Object myWallet() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setSchoolId(userInfo.getSchoolId());
        walletAccount.setSchoolCode(userInfo.getSchoolCode());
        walletAccount.setSchoolName(userInfo.getSchoolName());
        walletAccount.setUserId(userInfo.getUserId());
        walletAccount.setCardNumber(userInfo.getCardNumber().get(0));
        walletAccount.setUserName(userInfo.getName());
        walletAccount.setUserType(Byte.valueOf(userInfo.getIdentityType()));
        walletAccount.setOrgId(userInfo.getOrgId());
        return WrapMapper.ok(walletAccountControllerClient.myWallet(walletAccount).getResult());
    }

    @GetMapping("/childrenWallet")
    @ApiOperation(value = "孩子钱包", response = MyWalletVo.class)
    public Object childrenWallet(@RequestParam("cardNumber") String cardNumber, @RequestParam("schoolCode") String schoolCode) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        if (!userInfo.getIdentityType().equals("1") || !userInfo.getCardNumber().contains(cardNumber)) {
            return WrapMapper.error("不能查看非自己孩子的钱包信息,请绑定关系后重试");
        }
        //查询学校信息
        School school = schoolControllerClient.findSchoolBySchoolCode(schoolCode).getResult();
        Preconditions.checkArgument(school != null, "学校编码异常");

        //查询孩子信息
        StudentVo studentVo1 = studentControllerClient.queryStudentInfo(schoolCode, cardNumber).getResult();
        Preconditions.checkArgument(studentVo1 != null, "孩子信息异常");

        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setSchoolId(school.getId());
        walletAccount.setSchoolCode(school.getSchoolCode());
        walletAccount.setSchoolName(school.getSchoolName());
        walletAccount.setUserId(studentVo1.getSId());
        walletAccount.setCardNumber(cardNumber);
        walletAccount.setUserName(studentVo1.getSName());
        walletAccount.setUserType(Byte.valueOf(userInfo.getIdentityType()));
        walletAccount.setOrgId(Long.valueOf(studentVo1.getClassId()));
        return WrapMapper.ok(walletAccountControllerClient.myWallet(walletAccount).getResult());
    }

    @GetMapping("/findPayPwd")
    @ApiOperation(value = "查询是否设置支付密码", response = Boolean.class)
    public Object findPayPwd() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        return WrapMapper.ok(walletAccountControllerClient.findPayPwd(userInfo.getSchoolCode(), userInfo.getCardNumber().get(0)).getResult());
    }

    @PostMapping("/setPayPwd")
    @ApiOperation(value = "设置支付密码", response = Boolean.class)
    public Object setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.setPayPwd(setPayPwdDto).getResult());
    }

    @PostMapping("/modifyPayPwd")
    @ApiOperation(value = "修改支付密码", response = Boolean.class)
    public Object modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.modifyPayPwd(modifyPayPwdDto).getResult());
    }

    @PostMapping("/forgetPayPwd")
    @ApiOperation(value = "忘记支付密码", response = Boolean.class)
    public Object forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.forgetPayPwd(forgetPayPwdDto).getResult());
    }

    @GetMapping("/forgetPayPwdSendCode")
    @ApiOperation(value = "发送忘记支付密码，验证码信息", response = Boolean.class)
    public Object forgetPayPwdSendCode(@RequestParam("phone") String phone) {
        return WrapMapper.ok(walletAccountControllerClient.forgetPayPwdSendCode(phone).getResult());
    }

    @PostMapping("/setNoPwdPay")
    @ApiOperation(value = "设置小额免密支付", response = Boolean.class)
    public Object setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto) {
        return WrapMapper.ok(walletAccountControllerClient.setNoPwdPay(setNoPwdPayPwdDto).getResult());
    }
}
