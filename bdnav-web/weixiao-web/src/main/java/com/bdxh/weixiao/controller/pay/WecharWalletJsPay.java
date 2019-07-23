package com.bdxh.weixiao.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.WechatPayConstants;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JsOrderPayResponse;
import com.bdxh.order.dto.AddPayOrderDto;
import com.bdxh.order.enums.OrderPayStatusEnum;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.user.vo.TeacherVo;
import com.bdxh.wallet.dto.AddWalletPayDto;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.SortedMap;

/**
 * @Description: 钱包充值
 * @Author: Kang
 * @Date: 2019/7/15 10:28
 */
@RestController
@RequestMapping("/wecharWalletJsPayWeiXiaoWeb")
@Api(value = "JSAPI微校钱包充值", tags = "JSAPI微校钱包充值API")
@Validated
@Slf4j
public class WecharWalletJsPay {

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private FamilyControllerClient familyControllerClient;

    @Autowired
    private TeacherControllerClient teacherControllerClient;

    @Autowired
    private WalletRechargeControllerClient walletRechargeControllerClient;

    @Autowired
    private WechatJsPayControllerClient wechatJsPayControllerClient;


    @RequestMapping(value = "/walletJsPay", method = RequestMethod.POST)
    @ApiOperation(value = "JS统一下单充值钱包", response = String.class)
    public Object walletJsPay(@Validated @RequestBody AddWalletPayDto addWalletPayDto, HttpServletRequest request) {
        UserInfo userInfo = SecurityUtils.getCurrentUser();
        //账单充值entity
        AddWalletRechargeDto addWalletRechargeDto = new AddWalletRechargeDto();

        if (userInfo.getIdentityType().equals("1")) {
            //家长充值
            //查询家长信息
            Family family = familyControllerClient.queryFamilyInfoById(addWalletPayDto.getFamilyId()).getResult();
            Preconditions.checkArgument(family != null, "查询家长信息失败");
            //家长信息
            addWalletRechargeDto.setFamilyId(family.getId());
            addWalletRechargeDto.setFamilyName(family.getName());
            addWalletRechargeDto.setFamilyNumber(family.getCardNumber());
            //孩子信息
            Student student = studentControllerClient.queryStudentInfo2(addWalletPayDto.getUserId()).getResult();
            Preconditions.checkArgument(student != null, "查询学生信息失败");
            //学生信息
            addWalletRechargeDto.setUserId(student.getId());
            addWalletRechargeDto.setUserName(student.getName());
            addWalletRechargeDto.setCardNumber(student.getCardNumber());
        } else if (userInfo.getIdentityType().equals("2")) {
            //学生自己充值
            //查询学生信息
            Student student = studentControllerClient.queryStudentInfo2(addWalletPayDto.getUserId()).getResult();
            Preconditions.checkArgument(student != null, "查询学生信息失败");
            //学生信息
            addWalletRechargeDto.setUserId(student.getId());
            addWalletRechargeDto.setUserName(student.getName());
            addWalletRechargeDto.setCardNumber(student.getCardNumber());
        } else if (userInfo.getIdentityType().equals("3")) {
            //老师自己充值
            TeacherVo teacher = teacherControllerClient.queryTeacherInfo(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber()).getResult();
            Preconditions.checkArgument(teacher != null, "查询老师信息失败");
            //老师信息
            addWalletRechargeDto.setUserId(teacher.getId());
            addWalletRechargeDto.setUserName(teacher.getName());
            addWalletRechargeDto.setCardNumber(teacher.getCardNumber());
        }
        //学校信息
        addWalletRechargeDto.setSchoolId(userInfo.getSchoolId());
        addWalletRechargeDto.setSchoolCode(userInfo.getSchoolCode());
        addWalletRechargeDto.setSchoolName(userInfo.getSchoolName());

        //金额
        addWalletRechargeDto.setRechargeAmount(addWalletPayDto.getRechargeAmount());
        //支付时间
        addWalletRechargeDto.setRechargeTime(new Date());
        //设备号
        addWalletRechargeDto.setDeviceNumber(addWalletPayDto.getDeviceNumber());
        //充值类型
        addWalletRechargeDto.setRechargeType(addWalletPayDto.getRechargeType());
        //支付状态
        addWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.NO_PAY.getCode());
        //其他信息
        addWalletRechargeDto.setOperator(addWalletPayDto.getOperator());
        addWalletRechargeDto.setOperatorName(addWalletPayDto.getOperatorName());
        addWalletRechargeDto.setRemark(addWalletPayDto.getRemark());

        //添加充值记录信息(并返回我方订单号)
        Wrapper<String> wrapper = walletRechargeControllerClient.addWalletRecharge(addWalletRechargeDto);
        if (null == wrapper.getResult()) {
            return WrapMapper.error("充值钱包失败");
        }
        String orderNo = wrapper.getResult();

        //内部订单下单成功，到微信端下单预订单
        WxPayJsOrderDto wxPayJsOrderDto = new WxPayJsOrderDto();
        //我方订单号
        wxPayJsOrderDto.setOrderNo(orderNo);
        //商品金额
        wxPayJsOrderDto.setMoney(addWalletPayDto.getRechargeAmount());
        //商品描述
        wxPayJsOrderDto.setBody("校园钱包充值");
        //ip地址
        wxPayJsOrderDto.setIp(request.getRemoteAddr());
        //微信openid
        wxPayJsOrderDto.setOpenid(addWalletPayDto.getOpenId());
        //支付类型
        wxPayJsOrderDto.setPayType(new Integer("2"));

        Wrapper jsOrderWrapper = wechatJsPayControllerClient.wechatJsPayOrder(wxPayJsOrderDto);

        //返回预下单信息
        JSONObject jsonObject = JSONObject.parseObject(jsOrderWrapper.getResult().toString());

        //封装前端 微信 H5吊起支付对象,返回预订单信息
        JsOrderPayResponse jsOrderPayResponse = new JsOrderPayResponse();
        jsOrderPayResponse.setAppId(jsonObject.getString("appid"));
        jsOrderPayResponse.setTimeStamp(System.currentTimeMillis() / 1000L + "");
        jsOrderPayResponse.setNonceStr(jsonObject.getString("nonce_str"));
        jsOrderPayResponse.setPackages("prepay_id=" + jsonObject.getString("prepay_id"));
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(jsOrderPayResponse);
        if (paramMap.containsKey("packages")) {
            paramMap.put("package", paramMap.get("packages"));
            paramMap.remove("packages");
        }
        if (paramMap.containsKey("paySign")) {
            paramMap.remove("paySign");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + WechatPayConstants.JS.APP_KEY);
        jsOrderPayResponse.setPaySign(sign);

        //我方订单号绑定微信第三方预订单信息
        ModifyWalletRechargeDto modifyWalletRechargeDto = new ModifyWalletRechargeDto();
        modifyWalletRechargeDto.setOrderNo(Long.valueOf(orderNo));
        modifyWalletRechargeDto.setOutOrderNo(jsonObject.getString("prepay_id"));
        modifyWalletRechargeDto.setRechargeStatus(OrderPayStatusEnum.PAYING.getCode());
        walletRechargeControllerClient.modifyWalletRechargeByOrderNo(modifyWalletRechargeDto);
        return WrapMapper.ok(jsOrderPayResponse);
    }
}
