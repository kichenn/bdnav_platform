package com.bdxh.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ModifyUserDevice {

    /**
     * 用户设备信息主键
     */
    @ApiModelProperty("用户设备信息主键")
    private Long id;
    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编号
     */
    @ApiModelProperty("学校编号")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 所属组织主键
     */
    @ApiModelProperty("所属组织主键")
    private Long groupId;

    /**
     * 用户主键
     */
    @ApiModelProperty("用户主键")
    private Long userId;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String cardNumber;

    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

    /**
     * 设备号
     */
    @ApiModelProperty("设备号")
    private String imei;

    /**
     * 推送帐号
     */
    @ApiModelProperty("推送帐号")
    private String clientId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
