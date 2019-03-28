package com.bdxh.apiservice.modules.maiquancard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 麦圈开卡请求类
 * @author: xuyuan
 * @create: 2019-03-26 17:49
 **/
@Data
@ApiModel("麦圈开卡请求类")
public class MaiquanCardDto implements Serializable {

    private static final long serialVersionUID = 8241015016701583649L;

    /**
     * 基本信息主键
     */
    @ApiModelProperty("基本信息主键")
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
    private Long SchoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String SchoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String SchoolName;

    /**
     * 用户类型 1 学生 2 老师 3 家长
     */
    @ApiModelProperty("用户类型")
    private Integer UserType;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long UserId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String Name;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String CardNumber;

    /**
     * 卡发行号
     */
    @ApiModelProperty("卡发行号")
    private String issueNumber;

    /**
     * 卡物理号
     */
    @ApiModelProperty("卡物理号")
    private String phyNumber;

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
