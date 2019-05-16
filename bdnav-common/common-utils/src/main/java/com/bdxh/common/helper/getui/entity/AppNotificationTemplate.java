package com.bdxh.common.helper.getui.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 个推消息模版相关属性
 * @Author: Kang
 * @Date: 2019/5/8 11:32
 */
@Data
public class AppNotificationTemplate implements Serializable {

    private static final long serialVersionUID = 195356556838026674L;

    /**
     * 个推标题
     */
    private String title = "";

    /**
     * 个推内容(单推内容存放处)
     */
    private String text = "";

    /**
     * 跳转路径
     */
    private String url = "";

    /**
     * 通知栏图标名称
     */
    private String logo = "";

    /**
     * 通知栏图标名称地址
     */
    private String logoUrl = "";

    /**
     * 是否响铃
     */
    private boolean ring = true;

    /**
     * 是否震动
     */
    private boolean vibrate = true;

    /**
     * 是否允许清除该同志
     */
    private boolean clearable = true;

    /**
     * 透传消息设置，1为强制启动应用，客户端接收到消息后就会⽴即启动应用；2为等待应用启动
     */
    private int transmissionType = 2;

    /**
     * 穿透消息的内容信息(群推内容存放处)
     */
    private String transmissionContent;
}
