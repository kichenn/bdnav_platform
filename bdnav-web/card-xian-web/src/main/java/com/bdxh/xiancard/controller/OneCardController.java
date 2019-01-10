package com.bdxh.xiancard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.MD5;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.entity.WalletXianConfig;
import com.bdxh.wallet.feign.WalletConfigControllerClient;
import com.bdxh.xiancard.vo.TradeInfoVo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/oneCard")
@Slf4j
public class OneCardController {

     @Autowired
     private WalletConfigControllerClient walletConfigControllerClient;


    /**
     * 一卡通身份验证余额查询接口
     * @return
     */
    @RequestMapping(value = "/getAuthentication",method= RequestMethod.POST)
    @ResponseBody
    public Object getAuthentication(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber,
                                    @RequestParam("userName")String userName,@RequestParam(value = "mobile",required=false)String mobile){
         try{
             Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
             Preconditions.checkArgument(StringUtils.isNotEmpty(cardNumber),"卡号不能为空");
             Preconditions.checkArgument(StringUtils.isNotEmpty(userName),"姓名不能为空");

             Wrapper wrapper=walletConfigControllerClient.queryWalletConfigBySchoolCode(schoolCode);
             Preconditions.checkArgument(wrapper.getCode()==200,"获取学校基本信息失败");

             WalletXianConfig walletXianConfig = (WalletXianConfig)wrapper.getResult();
             String url = walletXianConfig.getUrl() + "/ocs/air/id/check";
             String mac = MD5.md5(cardNumber + "1" + walletXianConfig.getAppId() + userName + walletXianConfig.getSecret());
             String para = "{\"merchant\":\"" + walletXianConfig.getAppId() + "\",\"iccid\":\"" + cardNumber + "\"" + ",\"idtype\":\"1\",\"name\":\"" + userName + "\",\"mac\":\"" + mac + "\"}";
             RestTemplate restTemplate = new RestTemplate();
             JSONObject postData  = JSON.parseObject(para);
             JSONObject json = restTemplate.postForEntity(url, postData , JSONObject.class).getBody();
             String code=json.getString("code");
             Preconditions.checkArgument(code.equals("000000"),"一卡通余额查询失败");
             String amount = json.getString("amount");
             BigDecimal numBigDecimal = new BigDecimal(amount);
             BigDecimal newAmount = numBigDecimal.divide(new BigDecimal(100.00)).setScale(2);
             return WrapMapper.ok(newAmount);
         }catch (Exception e){
             e.printStackTrace();
             return WrapMapper.error(e.getMessage());
         }

    }


    /**
     * 一卡通充值接口
     */
    @RequestMapping("/addOneCard")
    @ResponseBody
    public Object addOneCard(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,
     @RequestParam("userName")String userName,@RequestParam("orderid")String orderid,@RequestParam("amount")String amount,
                             @RequestParam(value="account",required=false)String account){
          try{
              Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
              Preconditions.checkArgument(StringUtils.isNotEmpty(cardNumber),"卡号不能为空");
              Preconditions.checkArgument(StringUtils.isNotEmpty(userName),"姓名不能为空");
              Preconditions.checkArgument(StringUtils.isNotEmpty(orderid),"订单编号不能为空");
              Preconditions.checkArgument(StringUtils.isNotEmpty(amount),"金额不能为空");
              Wrapper wrapper=walletConfigControllerClient.queryWalletConfigBySchoolCode(schoolCode);
              Preconditions.checkArgument(wrapper.getCode()==200,"获取学校基本信息失败");
              WalletXianConfig walletXianConfig = (WalletXianConfig)wrapper.getResult();
              String url = walletXianConfig.getUrl() + "/ocs/air/recharge";
              String mac = MD5.md5(account + amount + cardNumber + "1" + walletXianConfig.getAppId() + userName + orderid + walletXianConfig.getSecret());
              String para = "{\"merchant\":\"" + walletXianConfig.getAppId()+ "\",\"iccid\":\"" + cardNumber + "\""
                      + ",\"idtype\":\"1\",\"name\":\"" + userName + "\",\"orderid\":\"" + orderid + "\",\"amount\":\""
                      + amount + "\",\"account\":\"" + account + "\",\"mac\":\"" + mac + "\"}";

              RestTemplate restTemplate = new RestTemplate();
              JSONObject postData  = JSON.parseObject(para);
              JSONObject json = restTemplate.postForEntity(url, postData , JSONObject.class).getBody();
              String code=json.getString("code");
              Preconditions.checkArgument(code.equals("000000"),"一卡通充值失败");
              return WrapMapper.ok();
          }catch (Exception e){
              e.printStackTrace();
              return WrapMapper.error(e.getMessage());
          }


    }


    /**
     * 消费记录查询
     */
    @RequestMapping(value = "/getExpenseCalendar",method= RequestMethod.POST)
    @ResponseBody
    public Object getExpenseCalendar(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,
            @RequestParam("userName")String userName,@RequestParam("idType")String idType){
            JSONObject jsonObject = new JSONObject();

            List<TradeInfoVo> list = new ArrayList<TradeInfoVo>();

        try{
            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(cardNumber),"卡号不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(userName),"名字不能为空");
            Wrapper wrapper=walletConfigControllerClient.queryWalletConfigBySchoolCode(schoolCode);
            Preconditions.checkArgument(wrapper.getCode()==200,"获取学校基本信息失败");
            WalletXianConfig walletXianConfig = (WalletXianConfig)wrapper.getResult();
            String url = walletXianConfig.getUrl() + "/ocs/air/weixiao/receive/trade/info";
            String mac = MD5.md5(cardNumber + idType + walletXianConfig.getAppId() + userName + walletXianConfig.getSecret());
            String para = "{\"merchant\":\"" + walletXianConfig.getAppId() + "\",\"iccid\":\"" + cardNumber + "\""
                    + ",\"idtype\":\"" + idType + "\",\"name\":\"" + userName + "\",\"mac\":\"" + mac + "\"}";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject postData  = JSON.parseObject(para);
            JSONObject json = restTemplate.postForEntity(url, postData , JSONObject.class).getBody();
            String code=json.getString("code");
            Preconditions.checkArgument(code.equals("000000"),"消费记录查询失败");
            TradeInfoVo tradeInfoVo=null;
            JSONArray array = json.getJSONArray("data");
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    tradeInfoVo = array.getObject(i, TradeInfoVo.class);
                    list.add(tradeInfoVo);
                }
            }
            return WrapMapper.ok(list);

        }catch(Exception e){

            return WrapMapper.error(e.getMessage());
        }

    }







}