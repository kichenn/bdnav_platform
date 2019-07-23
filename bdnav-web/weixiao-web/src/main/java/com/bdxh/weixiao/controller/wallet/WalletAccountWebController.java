package com.bdxh.weixiao.controller.wallet;

import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.user.vo.TeacherVo;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.bdxh.wallet.vo.MyWalletVo;
import com.bdxh.weixiao.configration.redis.RedisUtil;
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

    @Autowired
    private TeacherControllerClient teacherControllerClient;

    @Autowired
    private SchoolOrgControllerClient schoolOrgControllerClient;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/myWallet")
    @ApiOperation(value = "我的钱包", response = MyWalletVo.class)
    public Object myWallet() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        WalletAccount walletAccount = new WalletAccount();
        walletAccount.setUserType(Byte.valueOf(userInfo.getIdentityType()));
        walletAccount.setSchoolId(userInfo.getSchoolId());
        walletAccount.setSchoolCode(userInfo.getSchoolCode());
        walletAccount.setSchoolName(userInfo.getSchoolName());
        walletAccount.setOrgId(userInfo.getOrgId());
        MyWalletVo myWalletVo = null;
        //如果为老师身份，则判断他是否班主任
        if (walletAccount.getUserType().equals(new Byte("3"))){
            //老师钱包
            walletAccount.setUserId(userInfo.getFamilyId());
            walletAccount.setCardNumber(userInfo.getFamilyCardNumber());
            walletAccount.setUserName(userInfo.getFamilyName());

            myWalletVo = walletAccountControllerClient.myWallet(walletAccount).getResult();
            SchoolOrg schoolOrg = schoolOrgControllerClient.findOrgByManageId(walletAccount.getUserId(), walletAccount.getSchoolCode()).getResult();
            myWalletVo.setMyType(schoolOrg == null ? false : true);
            TeacherVo teacherVo = teacherControllerClient.queryTeacherInfo(myWalletVo.getSchoolCode(), myWalletVo.getCardNumber()).getResult();
            Preconditions.checkArgument(teacherVo != null, "老师信息为空");
            myWalletVo.setUserId(teacherVo.getId().toString());
        } else if (walletAccount.getUserType().equals(new Byte("2"))) {
            //学生钱包
            walletAccount.setUserId(userInfo.getUserId());
            walletAccount.setCardNumber(userInfo.getCardNumber().get(0));
            walletAccount.setUserName(userInfo.getName());
            myWalletVo = walletAccountControllerClient.myWallet(walletAccount).getResult();
            StudentVo studentVo = studentControllerClient.queryStudentInfo(myWalletVo.getSchoolCode(), myWalletVo.getCardNumber()).getResult();
            Preconditions.checkArgument(studentVo != null, "学生信息为空");
            myWalletVo.setUserId(studentVo.getSId().toString());
        }
        return WrapMapper.ok(myWalletVo);
    }

    @PostMapping("/childrenWallet")
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

        MyWalletVo myWalletVo = walletAccountControllerClient.myWallet(walletAccount).getResult();
        StudentVo studentVo = studentControllerClient.queryStudentInfo(myWalletVo.getSchoolCode(), myWalletVo.getCardNumber()).getResult();
        Preconditions.checkArgument(studentVo != null, "学生信息为空");
        myWalletVo.setUserId(studentVo.getSId().toString());
        return WrapMapper.ok(myWalletVo);
    }

    @GetMapping("/findPayPwd")
    @ApiOperation(value = "查询是否设置支付密码", response = Boolean.class)
    public Object findPayPwd() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        return walletAccountControllerClient.findPayPwd(userInfo.getSchoolCode(), cardNumber).getResult();
    }

    @PostMapping("/setPayPwd")
    @ApiOperation(value = "设置支付密码", response = Boolean.class)
    public Object setPayPwd(@Validated @RequestBody SetPayPwdDto setPayPwdDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        setPayPwdDto.setSchoolCode(userInfo.getSchoolCode());
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        setPayPwdDto.setCardNumber(cardNumber);
        return walletAccountControllerClient.setPayPwd(setPayPwdDto);
    }

    @PostMapping("/modifyPayPwd")
    @ApiOperation(value = "修改时效验支付密码", response = Boolean.class)
    public Object modifyPayPwd(@Validated @RequestBody ModifyPayPwdDto modifyPayPwdDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        modifyPayPwdDto.setSchoolCode(userInfo.getSchoolCode());
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        modifyPayPwdDto.setCardNumber(cardNumber);
        return walletAccountControllerClient.modifyPayPwd(modifyPayPwdDto);
    }

    @PostMapping("/forgetPayPwd")
    @ApiOperation(value = "忘记支付密码", response = Boolean.class)
    public Object forgetPayPwd(@Validated @RequestBody ForgetPayPwdDto forgetPayPwdDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        forgetPayPwdDto.setPhone(userInfo.getPhone());
        forgetPayPwdDto.setSchoolCode(userInfo.getSchoolCode());
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        forgetPayPwdDto.setCardNumber(cardNumber);
        return walletAccountControllerClient.forgetPayPwd(forgetPayPwdDto);
    }

    @GetMapping("/forgetPayPwdPhone")
    @ApiOperation(value = "忘记支付密码,找回密码所绑定的手机号", response = Boolean.class)
    public Object forgetPayPwdPhone() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        return WrapMapper.ok(userInfo.getPhone());
    }

    @GetMapping("/forgetPayPwdSendCode")
    @ApiOperation(value = "发送忘记支付密码，验证码信息", response = Boolean.class)
    public Object forgetPayPwdSendCode() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        String phone = userInfo.getPhone();
        return walletAccountControllerClient.forgetPayPwdSendCode(phone);
    }

    @GetMapping("/checkCode")
    @ApiOperation(value = "效验验证码", response = Boolean.class)
    public Object checkCode(@RequestParam("code") String code) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        String tempCode = redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PHYSICAL_CARD_PREFIX + userInfo.getPhone());
        return WrapMapper.ok(code.equals(tempCode));
    }

    @PostMapping("/setNoPwdPay")
    @ApiOperation(value = "设置小额免密支付", response = Boolean.class)
    public Object setNoPwdPay(@Validated @RequestBody SetNoPwdPayPwdDto setNoPwdPayPwdDto) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        setNoPwdPayPwdDto.setSchoolCode(userInfo.getSchoolCode());
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        setNoPwdPayPwdDto.setCardNumber(cardNumber);
        return walletAccountControllerClient.setNoPwdPay(setNoPwdPayPwdDto);
    }

    @PostMapping("/findNoPwdPay")
    @ApiOperation(value = "查询小额免密金额", response = Boolean.class)
    public Object findNoPwdPay() {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        String cardNumber = "";
        if (userInfo.getIdentityType().equals("3")) {
            //老师钱包
            cardNumber = userInfo.getFamilyCardNumber();
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生钱包
            cardNumber = userInfo.getCardNumber().get(0);
        }
        return walletAccountControllerClient.findNoPwdPay(userInfo.getSchoolCode(), cardNumber);
    }
}
