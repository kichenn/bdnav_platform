package com.bdxh.wallet.controller;

import com.bdxh.common.base.constant.RedisClusterConstrants;
import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.utils.*;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.configration.redis.RedisUtil;
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
import com.bdxh.wallet.dto.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/forgetPayPwd")
    @ApiOperation(value = "发送忘记支付密码，验证码信息", response = Boolean.class)
    public Object forgetPayPwd(@RequestParam("phone") String phone) {
        if (!ValidatorUtil.isMobile(phone)) {
            return WrapMapper.error("请输入正确的手机号");
        }
        //生成随机数
        String code = RandomUtil.createNumberCode(4);
        redisUtil.setWithExpireTime(AliyunSmsConstants.CodeConstants.CAPTCHA_PHYSICAL_CARD_PREFIX + phone, code, AliyunSmsConstants.CodeConstants.CAPTCHA_TIME);
        SmsUtil.sendMsgHelper(SmsTempletEnum.TEMPLATE_VERIFICATION, phone, code + ",:重置支付密码");
        return WrapMapper.ok(Boolean.TRUE);
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


    @RequestMapping(value = "/AddWalletAccount", method = RequestMethod.POST)
    @ApiOperation(value = "添加钱包账户信息", response = Boolean.class)
    public Object AddWalletAccount(@RequestBody AddWalletAccount addWalletAccount) {
        try {
            WalletAccount walletAccount = new WalletAccount();
            walletAccount.setId(snowflakeIdWorker.nextId());
            walletAccount.setPayPassword(new BCryptPasswordEncoder().encode(addWalletAccount.getPayPassword()));
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