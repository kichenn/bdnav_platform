package com.bdxh.wallet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 一卡通配置类dto
 * @author: xuyuan
 * @create: 2019-01-08 13:23
 **/
@Data
public class WalletConfigDto implements Serializable {

    private static final long serialVersionUID = 7707761438040681434L;

    /**
     * 学校编码
     */
    @NotNull(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 地址
     */
    @NotNull(message = "请求地址不能为空")
    private String url;

    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    private String appId;

    /**
     * 秘钥
     */
    @NotNull(message = "秘钥不能为空")
    private String secret;

    /**
     * 版本
     */
    @NotNull(message = "版本不能为空")
    private String version;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 备注
     */
    private String remark;
}
