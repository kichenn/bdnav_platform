package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.SchoolUserSexEnum;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.enums.SchoolUserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchoolUserQueryDto extends Query {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("性别 1 男 2 女")
    private SchoolUserSexEnum schoolUserSexEnum;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("状态 1 正常 2 锁定")
    private SchoolUserStatusEnum schoolUserStatusEnum;

    @ApiModelProperty("类型 1 普通用户 2 管理员")
    private SchoolUserTypeEnum schoolUserTypeEnum;


}
