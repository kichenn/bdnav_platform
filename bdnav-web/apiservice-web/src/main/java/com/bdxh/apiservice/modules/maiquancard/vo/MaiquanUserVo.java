package com.bdxh.apiservice.modules.maiquancard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 麦圈获取用户信息响应类
 * @author: xuyuan
 * @create: 2019-03-26 19:24
 **/
@Data
@ApiModel("麦圈获取用户信息响应类")
public class MaiquanUserVo implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 虚拟卡号
     */
    @ApiModelProperty("虚拟卡号")
    private Long virtualCardId;

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
     * 签名
     */
    @ApiModelProperty("签名")
    private String sign;

}
