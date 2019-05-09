package com.bdxh.common.helper.getui.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 个推消息模版相关属性
 * @Author: Kang
 * @Date: 2019/5/8 11:32
 */
@Data
public class AppLinkTemplate implements Serializable {

    private static final long serialVersionUID = 19815696838026674L;
    /**
     * 个推标题
     */
    private String title = "";

    /**
     * 个推内容
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
     * 是否允许清除该通知
     */
    private boolean clearable = true;
}
