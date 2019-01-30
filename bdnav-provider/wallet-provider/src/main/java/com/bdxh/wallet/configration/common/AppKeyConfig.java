package com.bdxh.wallet.configration.common;

import com.google.common.collect.HashBasedTable;

/**
 * @description: appKey配置类
 * @author: xuyuan
 * @create: 2019-01-29 10:31
 **/
public class AppKeyConfig {

    private static final HashBasedTable<String,String,String> appKeys = HashBasedTable.create();

    static {
        appKeys.put("bdxhwallet20190129","201901290001","bKA0jSF4rWt0GsUfT2pPRUzwD3avOi2JvoHPcg5+k/M=");
    }

    public static String getAppKey(String appId, String mchId){
        return appKeys.get(appId,mchId);
    }

}
