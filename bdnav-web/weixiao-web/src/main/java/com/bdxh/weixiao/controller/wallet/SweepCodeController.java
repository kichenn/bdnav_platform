package com.bdxh.weixiao.controller.wallet;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.qrcode.QRCodeUtils;
import com.bdxh.common.helper.weixiao.qrcode.request.CampusCodeRequest;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.ObjectUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.wallet.dto.QRCodeDto;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.SortedMap;

/**
 * @Description: 二维码相关操作
 * @Author: Kang
 * @Date: 2019/7/11 11:01
 */
@Slf4j
@RestController
@Api(value = "扫描二维码相关", tags = {"扫描二维码相关API"})
@Validated
public class SweepCodeController {

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @ApiOperation(value = "扫码相关", response = Boolean.class)
    @RequestMapping(value = "/authenticationWeixiao/codeOperation", method = RequestMethod.POST)
    public Object codeOperation(@Validated @RequestBody QRCodeDto qRCodeDto) {
        School school = schoolControllerClient.findSchoolBySchoolCode(qRCodeDto.getSchoolCode()).getResult();
        Preconditions.checkArgument(school != null, "该学校不存在，请检查");

        //组装请参信息
        CampusCodeRequest campusCodeRequest = new CampusCodeRequest();
        campusCodeRequest.setApp_key(school.getAppKey());
        campusCodeRequest.setTimestamp(System.currentTimeMillis() / 1000L);
        campusCodeRequest.setNonce(ObjectUtil.generateNonceStr());
        campusCodeRequest.setScene(qRCodeDto.getScene());
        campusCodeRequest.setDevice_no(qRCodeDto.getDeviceNo());
        campusCodeRequest.setLocation(qRCodeDto.getLocation());
        campusCodeRequest.setAuth_code(qRCodeDto.getAuthCode());
        campusCodeRequest.setSchool_code(qRCodeDto.getSchoolCode());
        //生成签名
        SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(campusCodeRequest);
        if (paramMap.containsKey("signature")) {
            paramMap.remove("signature");
        }
        String paramStr = BeanToMapUtil.mapToString(paramMap);
        String sign = MD5.md5(paramStr + "&key=" + campusCodeRequest.getApp_key());
        campusCodeRequest.setSignature(sign);
        String result = QRCodeUtils.campusCode(campusCodeRequest);

        JSONObject jsonObject = JSONObject.parseObject(result);

        if (jsonObject.getString("code").equals("0")) {
            //解析校园码成功
        } else {
            //解析校园码失败
            return WrapMapper.error("resultCode:" + jsonObject.getString("code") + ",resultMessage:" + jsonObject.getString("message"));
        }

        return WrapMapper.ok();
    }
}
