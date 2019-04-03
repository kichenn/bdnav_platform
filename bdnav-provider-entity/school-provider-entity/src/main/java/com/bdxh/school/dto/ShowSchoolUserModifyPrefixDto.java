package com.bdxh.school.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ShowSchoolUserModifyPrefixDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("性别")
    private Byte sex;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校code")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("账号状态")
    private Byte status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("出生日期")
    private String birth;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("用户类型")
    private Byte type;

    @ApiModelProperty("角色分配Id name  (List<Map<Long, String>> (角色id,角色名称))")
    private String roles;

    @ApiModelProperty("备注")
    private String remark;
}
