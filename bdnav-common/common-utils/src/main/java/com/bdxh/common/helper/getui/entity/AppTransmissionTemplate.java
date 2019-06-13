package com.bdxh.common.helper.getui.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *  个推透传携带参数
 */
@Data
public class AppTransmissionTemplate implements Serializable {

    private static final long serialVersionUID = -3732336235124759322L;

    /**
     * 透传消息设置，1为强制启动应用，客户端接收到消息后就会⽴即启动应用；2为等待应用启动
     */
    private int transmissionType = 2;

    /**
     * 穿透消息的内容信息(群推内容存放处)
     */
    private String transmissionContent;
}
