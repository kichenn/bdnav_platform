package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SchoolRoleQueryDto extends Query {


    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private String role;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

}
