package com.bdxh.user.vo;

import com.bdxh.user.enums.FamliyBlackUrlStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author WanMing
 * @date 2019/6/25 11:54
 */
@Data
public class FamilyBlackUrlVo {


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 家长号
     */
    @ApiModelProperty("家长号")
    private String cardNumber;

    /**
     * 家长名称
     */
    @ApiModelProperty("家长名称")
    private String familyName;


    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    private String studentNumber;

    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    private String siteName;

    /**
     * 填写域名或者ip
     */
    @ApiModelProperty("填写域名或者ip")
    private String ip;

    /**
     * 状态 1 启用 2 禁用
     */
    @ApiModelProperty("状态 1 启用 2 禁用")
    private Byte status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
