package com.bdxh.apiservice.modules.maiquancard.config;

import com.google.common.collect.HashBasedTable;

/**
 * @description: appKey配置类
 * @author: xuyuan
 * @create: 2019-01-29 10:31
 **/
public class AppKeyConfig {

    private static final HashBasedTable<Long,Long,String> appKeys = HashBasedTable.create();

    static {
        appKeys.put(123L,456L,"bKA0jSF4rWt0GsUfT2pPRUzwD3avOi2JvoHPcg5+k/M=");
    }

    public static String getAppKey(Long appId, Long mchId){
        return appKeys.get(appId,mchId);
    }

}
