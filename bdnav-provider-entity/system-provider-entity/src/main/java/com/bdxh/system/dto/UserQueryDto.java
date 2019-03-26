package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryDto extends Query {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptFullName;

    /**
     * 性别 1 男 2 女
     */
    @ApiModelProperty("性别 1 男 2 女")
    private Byte sex;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 状态 1 正常 2 锁定
     */
    @ApiModelProperty("状态 1 正常 2 锁定")
    private Byte status;

    /**
     * 类型 1 普通用户 2 管理员
     */
    @ApiModelProperty("类型 1 普通用户 2 管理员")
    private Byte type;


}
