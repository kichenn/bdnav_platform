package com.bdxh.common.helper.weixiao.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.authentication.AESEncryption.WxEncryption;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 微校认证工具类
 * @author: binzh
 * @create: 2019-04-24 15:04
 **/
@Slf4j
public class AuthenticationUtils {

    /**
     * 同步用户信息到腾讯微校后台  成功返回参数 errcode:0
     * @param synUserInfoRequest 需要加密的数据
     * @param appKey  腾讯微校后台提供
     * @param appSecret 取前16位 腾讯微校后台提供
     * @return
     * @throws Exception
     */
    public static String synUserInfo(SynUserInfoRequest synUserInfoRequest,String appKey,String appSecret ){
        try {
            //获取同步微校用户所有属性 和值
            List<Map<String,Object>> list=AuthenticationUtils.getFiledsInfo(synUserInfoRequest);
            JSONObject dataJson=new JSONObject();
            for (Map<String, Object> map : list) {
                if(null!=map.get("value")){
                    dataJson.put(map.get("name").toString(),map.get("value"));
                }
            }
            List<String> dataAttr=new ArrayList<>();
            dataAttr.add(dataJson.toString());
            String rawData=WxEncryption.Encrypt(dataAttr.toString(),appKey,appSecret.substring(0,16));
            Map<String,Object> map =new HashMap<>();
            map.put("app_key",appKey);
            map.put("raw_data",rawData);
            String result = HttpClientUtils.doPost(AuthenticationConstant.SYNC_USER_INFO_URL,map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

        /**
         * 认证推送用户信息到腾讯微校后台  成功返回参数 errcode:0
         * @param synUserInfoRequest 需要加密的数据
         * @param appKey  腾讯微校后台提供
         * @param appSecret 取前16位 腾讯微校后台提供
         * @oaram state     由微校跳转前端页面前端接收再传给后台
         * @return
         * @throws Exception
         */
    public static String authUserInfo(SynUserInfoRequest synUserInfoRequest,String appKey,String appSecret,String state){
        try {
            //获取同步微校用户所有属性 和值
            List<Map<String,Object>> list=AuthenticationUtils.getFiledsInfo(synUserInfoRequest);
            JSONObject dataJson=new JSONObject();
            for (Map<String, Object> map : list) {
                if(null!=map.get("value")){
                    dataJson.put(map.get("name").toString(),map.get("value"));
                }
            }
            dataJson.put("state",state);
            String rawData=WxEncryption.Encrypt(dataJson.toString(),appKey,appSecret.substring(0,16));
            log.info("-----------raw_data:{}",rawData);
            log.info("-----------app_key:{}",appKey);
            Map<String,Object> map =new HashMap<>();
            map.put("raw_data",rawData);
            map.put("app_key",appKey);
            String result = HttpClientUtils.doPost(AuthenticationConstant.RECEIVE_STU_INFO,map);
            JSONObject jsonObject=JSONObject.parseObject(result);
            log.info("-------------errmsg: {}",jsonObject.get("errmsg"));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }










    /**
     * 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }

    }

    private static List<Map<String,Object>> getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List<Map<String,Object>> list = new ArrayList();
        Map<String,Object> infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }



}