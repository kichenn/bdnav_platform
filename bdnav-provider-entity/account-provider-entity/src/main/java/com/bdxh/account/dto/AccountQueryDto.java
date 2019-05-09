package com.bdxh.account.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 账户查询dto
 * @Author: Kang
 * @Date: 2019/5/8 16:56
 */
@Data
@ApiModel("账户查询dto")
public class AccountQueryDto extends Query {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户类型 1 学生 2 老师 3 家长")
    private Byte userType;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户手机号")
    private String userPhone;

    @ApiModelProperty("用户学号")
    private String cardNumber;

    @ApiModelProperty("用户登录名")
    private String loginName;

    @ApiModelProperty("修改登录名 1 未修改 2 已修改")
    private Byte loginNameUpdate;

    @ApiModelProperty("账户是否过期 1 正常 2 过期")
    private Byte accountExpired;

    @ApiModelProperty("账户是否锁定 1 正常 2 锁定")
    private Byte accountLocked;

}
