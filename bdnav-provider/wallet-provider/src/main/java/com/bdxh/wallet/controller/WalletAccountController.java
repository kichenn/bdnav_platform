package com.bdxh.wallet.controller;

import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.configration.redis.RedisUtil;
import com.bdxh.wallet.dto.ModifyPayPwdDto;
import com.bdxh.wallet.dto.SetNoPwdPayPwdDto;
import com.bdxh.wallet.dto.SetPayPwdDto;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.enums.AccountStatusEnum;
import com.bdxh.wallet.service.WalletAccountService;
import com.bdxh.wallet.vo.MyWalletVo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.dto.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    private RedisUtil redisUtil;


    /**
     * @param walletAccountParam
     * @return
     */
    @PostMapping("/myWallet")
    @ApiOperation(value = "我的钱包", response = MyWalletVo.class)
    public Object myWallet(@RequestBody WalletAccount walletAccountParam) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(walletAccountParam.getCardNumber(), walletAccountParam.getSchoolCode());
        if (walletAccount == null) {
            //钱包不存在，新增钱包信息
            walletAccount = new WalletAccount();
            walletAccount.setId(snowflakeIdWorker.nextId());
            walletAccount.setSchoolId(walletAccountParam.getSchoolId());
            walletAccount.setSchoolCode(walletAccountParam.getSchoolCode());
            walletAccount.setSchoolName(walletAccountParam.getSchoolName());
            walletAccount.setUserId(walletAccountParam.getUserId());
            walletAccount.setCardNumber(walletAccountParam.getCardNumber());
            walletAccount.setUserName(walletAccountParam.getUserName());
            walletAccount.setUserType(walletAccountParam.getUserType());
            walletAccount.setOrgId(walletAccountParam.getOrgId());
            walletAccount.setAmount(new BigDecimal("0"));
            walletAccount.setQuickPayMoney(new BigDecimal("0"));
            walletAccount.setStatus(AccountStatusEnum.NORMAL_KEY);
            walletAccount.setCreateDate(new Date());
            walletAccount.setUpdateDate(new Date());
            walletAccount.setRemark("钱包");
            walletAccountService.save(walletAccount);
        }
        MyWalletVo myWalletVo = new MyWalletVo();
        myWalletVo.setIdentityType(String.valueOf(walletAccount.getUserType()));
        BeanUtils.copyProperties(walletAccount, myWalletVo);
        return WrapMapper.ok(myWalletVo);
    }

    @GetMapping("/findPayPwd")
    @ApiOperation(value = "查询是否设置支付密码", response = Boolean.class)
    public Object findPayPwd(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(cardNumber, schoolCode);
        return WrapMapper.ok(!(walletAccount.getPayPassword() == null || walletAccount.getPayPassword().isEmpty()));
    }


    @PostMapping("/setPayPwd")
    @ApiOperation(value = "设置支付密码", response = Boolean.class)
    public Object setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        String pwd = AESUtils.deCode(setPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY);
        Preconditions.checkArgument(pwd.length() == 6, "请输入6位长度的密码");
        String payPwd = new BCryptPasswordEncoder().encode(pwd);
        Boolean result = walletAccountService.setPayPwd(setPayPwdDto.getCardNumber(), setPayPwdDto.getSchoolCode(), payPwd);
        if (result) {
            //查询是否设置小额免密支付
            WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(setPayPwdDto.getCardNumber(), setPayPwdDto.getSchoolCode());
            Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");
            return WrapMapper.ok(!(walletAccount.getQuickPayMoney() == null || walletAccount.getQuickPayMoney().compareTo(new BigDecimal("0")) == 0));
        }
        return WrapMapper.error("设置支付密码失败");
    }

    @PostMapping("/modifyPayPwd")
    @ApiOperation(value = "修改时效验支付密码", response = Boolean.class)
    public Object modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(modifyPayPwdDto.getCardNumber(), modifyPayPwdDto.getSchoolCode());
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");

        //matches(第一个参数为前端传递的值，未加密，后一个为数据库已经加密的串对比)
        if (!new BCryptPasswordEncoder().matches(AESUtils.deCode(modifyPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY), walletAccount.getPayPassword())) {
            return WrapMapper.ok(Boolean.FALSE);
        }
        return WrapMapper.ok(Boolean.TRUE);
    }

    @PostMapping("/forgetPayPwd")
    @ApiOperation(value = "忘记支付密码", response = Boolean.class)
    public Object forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto) {
        //获取新密码加密串
        String code = redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PHYSICAL_CARD_PREFIX + forgetPayPwdDto.getPhone());
        if (!forgetPayPwdDto.getCode().equals(code)) {
            return WrapMapper.error("验证码错误或已过期，请检查！");
        }
        String payPwd = new BCryptPasswordEncoder().encode(AESUtils.deCode(forgetPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY));
        return WrapMapper.ok(walletAccountService.setPayPwd(forgetPayPwdDto.getCardNumber(), forgetPayPwdDto.getSchoolCode(), payPwd));
    }

    @GetMapping("/forgetPayPwdSendCode")
    @ApiOperation(value = "发送忘记支付密码，验证码信息", response = Boolean.class)
    public Object forgetPayPwdSendCode(@RequestParam("phone") String phone) {
        if (!ValidatorUtil.isMobile(phone)) {
            return WrapMapper.error("请输入正确的手机号");
        }
        //生成随机数
        String code = RandomUtil.createNumberCode(4);
        redisUtil.setWithExpireTime(AliyunSmsConstants.CodeConstants.CAPTCHA_PHYSICAL_CARD_PREFIX + phone, code, AliyunSmsConstants.CodeConstants.CAPTCHA_TIME);
        SmsUtil.sendMsgHelper(SmsTempletEnum.TEMPLATE_VERIFICATION, phone, code + ",:重置支付密码");
        return WrapMapper.ok(Boolean.TRUE);
    }


    @PostMapping("/setNoPwdPay")
    @ApiOperation(value = "设置小额免密支付", response = Boolean.class)
    public Object setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(setNoPwdPayPwdDto.getCardNumber(), setNoPwdPayPwdDto.getSchoolCode());
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");

        String pwd = AESUtils.deCode(setNoPwdPayPwdDto.getPayPwd(), AESUtils.AesConstant.WEIXIAO_KEY);
        Preconditions.checkArgument(pwd.length() == 6, "请输入6位长度的密码");
        //matches(第一个参数为前端传递的值，未加密，后一个为数据库已经加密的串对比)
        if (!new BCryptPasswordEncoder().matches(pwd, walletAccount.getPayPassword())) {
            return WrapMapper.ok(Boolean.FALSE);
        }
        WalletAccount walletAccountParam = new WalletAccount();
        walletAccountParam.setUpdateDate(new Date());
        walletAccountParam.setSchoolCode(setNoPwdPayPwdDto.getSchoolCode());
        walletAccountParam.setCardNumber(setNoPwdPayPwdDto.getCardNumber());
        walletAccountParam.setQuickPayMoney(setNoPwdPayPwdDto.getQuickPayMoney());
        walletAccountParam.setOperator(setNoPwdPayPwdDto.getOperator());
        walletAccountParam.setOperatorName(setNoPwdPayPwdDto.getOperatorName());
        walletAccountParam.setRemark(setNoPwdPayPwdDto.getRemark());

        return WrapMapper.ok(walletAccountService.noPwdPay(walletAccountParam));
    }

    @PostMapping("/findNoPwdPay")
    @ApiOperation(value = "查询小额免密支付金额", response = Boolean.class)
    public Object findNoPwdPay(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        //查询钱包信息
        WalletAccount walletAccount = walletAccountService.findWalletByCardNumberAndSchoolCode(cardNumber, schoolCode);
        Preconditions.checkArgument(walletAccount != null, "该钱包不存在，请检查cardNumber，schoolCode");
        return WrapMapper.ok(walletAccount.getQuickPayMoney());
    }

    /**
     * 不在swagger展示，支付回调成功后充值金额信息
     *
     * @param modifyAccountMoeny
     * @return
     */
    @ApiIgnore
    @PostMapping("/rechargeWalletAccount")
    @ApiOperation(value = "账户钱包充值", response = Boolean.class)
    public Object rechargeWalletAccount(@RequestBody @Validated ModifyAccountMoeny modifyAccountMoeny) {
        return WrapMapper.ok(walletAccountService.walletAccountRecharge(modifyAccountMoeny.getCardNumber(), modifyAccountMoeny.getSchoolCode(), modifyAccountMoeny.getAmount()));
    }


    @RequestMapping(value = "/AddWalletAccount", method = RequestMethod.POST)
    @ApiOperation(value = "添加钱包账户信息", response = Boolean.class)
    public Object AddWalletAccount(@RequestBody AddWalletAccount addWalletAccount) {
        try {
            WalletAccount walletAccount = new WalletAccount();
            walletAccount.setId(snowflakeIdWorker.nextId());
            walletAccount.setPayPassword(new BCryptPasswordEncoder().encode(addWalletAccount.getPayPassword()));
            if (addWalletAccount.getAccountStatusEnum() != null) {
                walletAccount.setStatus(addWalletAccount.getAccountStatusEnum().getKey());
            }
            BeanUtils.copyProperties(addWalletAccount, walletAccount);
            Boolean result = walletAccountService.save(walletAccount) > 0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "findWalletAccountInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "带条件分页查询列表信息", response = WalletAccount.class)
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
    @ApiOperation(value = "修改钱包账户信息", response = Boolean.class)
    public Object modifyWalletAccount(@RequestBody ModifyWalletAccount modifyWalletAccount) {
        try {
            WalletAccount walletAccount = new WalletAccount();
            if (modifyWalletAccount.getAccountStatusEnum() != null) {
                walletAccount.setStatus(modifyWalletAccount.getAccountStatusEnum().getKey());
            }
            BeanUtils.copyProperties(modifyWalletAccount, walletAccount);
            Boolean result = walletAccountService.update(walletAccount) > 0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delWalletAccount", method = RequestMethod.GET)
    @ApiOperation(value = "删除钱包账户信息", response = Boolean.class)
    public Object delWalletAccount(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
        try {
            Boolean result = walletAccountService.delWalletAccount(schoolCode, cardNumber, id);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




}