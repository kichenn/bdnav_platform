package com.bdxh.common.helper.getui.request;

import com.bdxh.common.helper.getui.entity.AppLinkTemplate;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 个推请参
 * @Author: Kang
 * @Date: 2019/5/8 11:08
 */
@Data
public class AppPushRequest implements Serializable {

    private static final long serialVersionUID = 7708321604485028623L;

    /**
     * getui APPID
     */
    private String appId;

    /**
     * getui APPKEY
     */
    private String appKey;

    /**
     * getui mastarSecret
     */
    private String masterSecret;

    /**
     * 手机客户端id
     */
    private List<String> clientId;

    /**
     * 是否设置消息离线
     */
    private boolean offline = true;

    /**
     * 离线有效时间，单位为毫秒，可选（默认一天）
     */
    private long offlineExpireTime = 24L * 3600 * 1000;

    /**
     * 推送的状态
     * 1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
     */
    private int pushNetWorkType = 0;

    /**
     * 普通模版内容信息
     */
    private AppLinkTemplate appTemplate;

    /**
     * 穿透模版内容信息
     */
    private AppNotificationTemplate appNotificationTemplate;

    /**
     * 自定义穿透模板
     */
    private AppTransmissionTemplate appTransmissionTemplate;
}
