package com.bdxh.apiservice.modules.maiquancard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 麦圈开卡dto
 * @author: xuyuan
 * @create: 2019-03-27 09:50
 **/
@Data
@ApiModel("麦圈开卡dto")
public class MaiquanCreateCardDto implements Serializable {

    private static final long serialVersionUID = -6527371071937228264L;

    @ApiModelProperty("虚拟卡号")
    private Long virtualCardId;

    /**
     * 学校id
     */
    @NotNull(message = "学校id不能为空")
    @ApiModelProperty("学校id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @NotEmpty(message = "学校名称不能为空")
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 基本用户id
     */
    @NotNull(message = "基本用户id不能为空")
    @ApiModelProperty("基本用户id")
    private Long baseUserId;

    /**
     * 用户类型 1 学生 2 老师 3 家长
     */
    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty("用户类型")
    private Byte userType;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    @ApiModelProperty("姓名")
    private String userName;

    /**
     * 卡号
     */
    @NotEmpty(message = "卡号不能为空")
    @ApiModelProperty("卡号")
    private String cardNumber;

    /**
     * 卡类型  1 M1卡 2 CPU卡
     */
    @NotNull(message = "卡类型不能为空")
    @ApiModelProperty("卡类型")
    private Byte card_type;

    /**
     * 卡发行号
     */
    @NotEmpty(message = "卡发行号不能为空")
    @ApiModelProperty("卡发行号")
    private String issue_number;

    /**
     * 卡物理号
     */
    @NotEmpty(message = "卡物理号不能为空")
    @ApiModelProperty("卡物理号")
    private String phy_number;

    /**
     * 押金
     */
    @NotNull(message = "押金不能为空")
    @ApiModelProperty("押金")
    private BigDecimal deposit;

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
