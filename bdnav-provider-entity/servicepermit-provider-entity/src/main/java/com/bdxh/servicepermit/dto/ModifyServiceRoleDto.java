package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: 修改服务许可角色dto
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@Data
public class ModifyServiceRoleDto {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("修改时间")
    private Date updateDate;


}