package com.bdxh.school.dto;

import com.bdxh.school.enums.SchoolUserSexEnum;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.enums.SchoolUserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class AddSchoolUserDto implements Serializable {

    private static final long serialVersionUID = 7421251545883628226L;


    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;


    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String realName;

    /**
     * 性别 1 男 2 女
     */
    @ApiModelProperty("性别 1 男 2 女")
    @NotNull(message = "性别不能为空")
    private SchoolUserSexEnum schoolUserSexEnum;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private String birth;


    /**
     * 手机
     */
    @ApiModelProperty("手机")
    @NotEmpty(message = "手机不能为空")
    private String phone;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    /**
     * 状态 1 正常 2 锁定
     */
    @NotNull(message = "学校用户状态不能为空")
    @ApiModelProperty("状态 1 正常 2 锁定")
    private SchoolUserStatusEnum schoolUserStatusEnum;

    /**
     * 头像图片
     */
    @ApiModelProperty("用户头像")
    private String image;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String image_name;


    /**
     * 分配的角色id
     */
    @ApiModelProperty("分配的角色ids")
    private String roleIds;


    /**
     * 类型 1 普通用户 2 管理员
     */
    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty("类型 1 普通用户 2 管理员")
    private SchoolUserTypeEnum schoolUserTypeEnum;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人名称
     */
    @ApiModelProperty("操作人名称")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("角色分配")
    private List<Long> roles;
}
