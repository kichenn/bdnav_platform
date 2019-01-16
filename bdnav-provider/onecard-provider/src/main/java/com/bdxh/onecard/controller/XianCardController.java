package com.bdxh.onecard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.XianCardConstants;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.xiancard.domain.*;
import com.bdxh.onecard.dto.XianAddBlanceDto;
import com.bdxh.onecard.dto.XianQueryBlanceDto;
import com.bdxh.onecard.dto.XianQueryConsListDto;
import com.bdxh.onecard.dto.XianSyscDataDto;
import com.bdxh.wallet.entity.WalletXianConfig;
import com.bdxh.wallet.feign.WalletConfigControllerClient;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/xianCard")
@Slf4j
@Validated
public class XianCardController {

   @Autowired
    private WalletConfigControllerClient walletConfigControllerClient;

    /**
     * 一卡通用户同步接口
     * @return
     */
    @RequestMapping(value = "/syscUser", method = RequestMethod.POST)
    @ResponseBody
    public Object syscUser(@Valid @RequestBody XianSyscDataDto xianSyscDataDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = walletConfigControllerClient.queryWalletConfigBySchoolCode(xianSyscDataDto.getSchoolCode());
            Preconditions.checkArgument(wrapper.getCode() == 200, "获取学校一卡通信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig) wrapper.getResult();
            //准备请求参数
            SyscDataRequest syscDataRequest=new SyscDataRequest();
            syscDataRequest.setIccid(xianSyscDataDto.getCardNumber());
            syscDataRequest.setIdtype(XianCardConstants.IdentityType.CARD_XUEHAO.getCode());
            syscDataRequest.setMerchant(walletXianConfig.getAppId());
            syscDataRequest.setUsertype(xianSyscDataDto.getUserType());
            syscDataRequest.setName(xianSyscDataDto.getName());
            SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(syscDataRequest);
            String valueString = BeanToMapUtil.mapToValueString(paramMap);
            String sign = MD5.md5(valueString + walletXianConfig.getSecret());
            syscDataRequest.setMac(sign);
            String requestStr = JSON.toJSONString(syscDataRequest);
            //发起http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/json; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            String url = walletXianConfig.getUrl() + XianCardConstants.QUERY_BALANCE_URI;
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("一卡通用户同步失败");
            }
            JSONObject result = responseEntity.getBody();
            Preconditions.checkNotNull(result,"一卡通用户同步失败");
            Preconditions.checkArgument(StringUtils.equalsAnyIgnoreCase(result.getString("code"),"000000"), "一卡通用户同步失败");
            return WrapMapper.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 一卡通身份验证余额查询接口
     * @return
     */
    @RequestMapping(value = "/queryBalance", method = RequestMethod.POST)
    @ResponseBody
    public Object queryBalance(@Valid @RequestBody XianQueryBlanceDto xianQueryBlanceDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = walletConfigControllerClient.queryWalletConfigBySchoolCode(xianQueryBlanceDto.getSchoolCode());
            Preconditions.checkArgument(wrapper.getCode() == 200, "获取学校一卡通信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig) wrapper.getResult();
            //准备请求参数
            QueryBalanceRequest queryBalanceRequest=new QueryBalanceRequest();
            queryBalanceRequest.setIccid(xianQueryBlanceDto.getCardNumber());
            queryBalanceRequest.setIdtype(XianCardConstants.IdentityType.CARD_XUEHAO.getCode());
            queryBalanceRequest.setMerchant(walletXianConfig.getAppId());
            queryBalanceRequest.setName(xianQueryBlanceDto.getUserName());
            SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(queryBalanceRequest);
            String valueString = BeanToMapUtil.mapToValueString(paramMap);
            String sign = MD5.md5(valueString + walletXianConfig.getSecret());
            queryBalanceRequest.setMac(sign);
            String requestStr = JSON.toJSONString(queryBalanceRequest);
            //发起http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/json; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            String url = walletXianConfig.getUrl() + XianCardConstants.QUERY_BALANCE_URI;
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("查询一卡通余额失败");
            }
            JSONObject result = responseEntity.getBody();
            Preconditions.checkNotNull(result,"查询一卡通余额失败");
            Preconditions.checkArgument(StringUtils.equalsAnyIgnoreCase(result.getString("code"),"000000"), "查询一卡通余额失败");
            //返回余额
            String amount = result.getString("amount");
            BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(100.00)).setScale(2);
            return WrapMapper.ok(bigDecimal);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 一卡通充值接口
     */
    @RequestMapping(value = "/addBalance", method = RequestMethod.POST)
    @ResponseBody
    public Object addBalance(@Valid @RequestBody XianAddBlanceDto xianAddBlanceDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = walletConfigControllerClient.queryWalletConfigBySchoolCode(xianAddBlanceDto.getSchoolCode());
            Preconditions.checkArgument(wrapper.getCode() == 200, "获取学校一卡通信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig) wrapper.getResult();
            //准备参数
            AddBalanceRequest addBalanceRequest=new AddBalanceRequest();
            addBalanceRequest.setAmount(String.valueOf(xianAddBlanceDto.getMoney().multiply(new BigDecimal("100")).intValue()));
            addBalanceRequest.setIccid(xianAddBlanceDto.getCardNumber());
            addBalanceRequest.setIdtype(XianCardConstants.IdentityType.CARD_XUEHAO.getCode());
            addBalanceRequest.setMerchant(walletXianConfig.getAppId());
            addBalanceRequest.setOrderid(xianAddBlanceDto.getOrderNo());
            addBalanceRequest.setName(xianAddBlanceDto.getUserName());
            addBalanceRequest.setAccount("");
            SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(addBalanceRequest);
            String valueString = BeanToMapUtil.mapToValueString(paramMap);
            String sign = MD5.md5(valueString + walletXianConfig.getSecret());
            addBalanceRequest.setMac(sign);
            String requestStr = JSON.toJSONString(addBalanceRequest);
            //发起http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/json; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            String url = walletXianConfig.getUrl() + XianCardConstants.RECHARGE_URI;
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("充值一卡通失败");
            }
            JSONObject result = responseEntity.getBody();
            Preconditions.checkNotNull(result,"充值一卡通失败");
            Preconditions.checkArgument(StringUtils.equalsAnyIgnoreCase(result.getString("code"),"000000"), "充值一卡通失败");
            //返回流水号
            return WrapMapper.ok(result.getString("acceptseq"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 消费记录查询
     */
    @RequestMapping(value = "/queryTradeList", method = RequestMethod.POST)
    @ResponseBody
    public Object queryTradeList(@Valid @RequestBody XianQueryConsListDto xianQueryConsListDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Wrapper wrapper = walletConfigControllerClient.queryWalletConfigBySchoolCode(xianQueryConsListDto.getSchoolCode());
            Preconditions.checkArgument(wrapper.getCode() == 200, "获取学校一卡通信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig) wrapper.getResult();
            //准备参数
            QueryConsRecordRequest queryConsRecordRequest=new QueryConsRecordRequest();
            queryConsRecordRequest.setMerchant(walletXianConfig.getAppId());
            queryConsRecordRequest.setName(xianQueryConsListDto.getUserName());
            queryConsRecordRequest.setIccid(xianQueryConsListDto.getCardNumber());
            queryConsRecordRequest.setIdtype(XianCardConstants.IdentityType.CARD_XUEHAO.getCode());
            queryConsRecordRequest.setStarttime(xianQueryConsListDto.getStarttime());
            queryConsRecordRequest.setEntime(xianQueryConsListDto.getEntime());
            SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(queryConsRecordRequest);
            String valueString = BeanToMapUtil.mapToValueString(paramMap);
            String sign = MD5.md5(valueString + walletXianConfig.getSecret());
            queryConsRecordRequest.setMac(sign);
            String requestStr = JSON.toJSONString(queryConsRecordRequest);
            //发起http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/json; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            String url = walletXianConfig.getUrl() + XianCardConstants.QUERY_CONSUME_URI;
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("查询一卡通消费记录失败");
            }
            JSONObject result = responseEntity.getBody();
            Preconditions.checkNotNull(result,"查询一卡通消费记录失败");
            Preconditions.checkArgument(StringUtils.equalsAnyIgnoreCase(result.getString("code"),"000000"), "查询一卡通消费记录失败");
            //返回消费记录
            return WrapMapper.ok(result.getJSONArray("data"));
        } catch (Exception e) {

            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 充值结果查询
     */
    @RequestMapping(value = "/queryAddResult", method = RequestMethod.POST)
    @ResponseBody
    public Object queryAddResult(@RequestParam(name = "schoolCode") @NotEmpty(message = "学校编码不能为空") String schoolCode,
                                 @RequestParam(name = "orderNo") @NotEmpty(message = "订单号不能为空") String orderNo) {
        try {
            Wrapper wrapper = walletConfigControllerClient.queryWalletConfigBySchoolCode(schoolCode);
            Preconditions.checkArgument(wrapper.getCode() == 200, "获取学校一卡通信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig) wrapper.getResult();
            //准备参数
            QueryAddResultRequest queryAddResultRequest=new QueryAddResultRequest();
            queryAddResultRequest.setMerchant(walletXianConfig.getAppId());
            queryAddResultRequest.setOrderid(orderNo);
            SortedMap<String, String> paramMap = BeanToMapUtil.objectToTreeMap(queryAddResultRequest);
            String valueString = BeanToMapUtil.mapToValueString(paramMap);
            String sign = MD5.md5(valueString + walletXianConfig.getSecret());
            queryAddResultRequest.setMac(sign);
            String requestStr = JSON.toJSONString(queryAddResultRequest);
            //发起http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "utf-8");
            headers.set("Content-type", "application/json; charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestStr,headers);
            String url = walletXianConfig.getUrl() + XianCardConstants.RECHARGE_RESULT_URI;
            ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
            if (!(responseEntity.getStatusCode().value() == 200 && responseEntity.hasBody())) {
                return WrapMapper.error("查询一卡通充值结果失败");
            }
            JSONObject result = responseEntity.getBody();
            Preconditions.checkNotNull(result,"查询一卡通充值结果失败");
            return WrapMapper.ok(result);
        } catch (Exception e) {

            return WrapMapper.error(e.getMessage());
        }
    }

}