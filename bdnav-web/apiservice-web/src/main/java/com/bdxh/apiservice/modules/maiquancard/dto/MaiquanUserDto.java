package com.bdxh.apiservice.modules.maiquancard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 麦圈获取用户信息请求类
 * @author: xuyuan
 * @create: 2019-03-26 17:49
 **/
@Data
@ApiModel("麦圈获取用户信息请求类")
public class MaiquanUserDto implements Serializable {

    private static final long serialVersionUID = 7683558128579755589L;

    /**
     * 卡号
     */
    @NotEmpty(message = "卡号不能为空")
    @ApiModelProperty("卡号")
    private String cardNumber;

    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空")
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    @ApiModelProperty("商户号")
    private Long mchId;

    /**
     * 随机字符串
     */
    @NotEmpty(message = "随机字符串不能为空")
    @ApiModelProperty("随机字符串")
    private String noticeStr;

    /**
     * 时间戳
     */
    @NotNull(message = "时间戳不能为空")
    @ApiModelProperty("时间戳")
    private Date timeStamp;

    /**
     * 签名
     */
    @NotEmpty(message = "签名不能为空")
    @ApiModelProperty("签名")
    private String sign;

}
