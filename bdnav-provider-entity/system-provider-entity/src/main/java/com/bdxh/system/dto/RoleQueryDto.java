package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RoleQueryDto extends Query {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long id;

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
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
