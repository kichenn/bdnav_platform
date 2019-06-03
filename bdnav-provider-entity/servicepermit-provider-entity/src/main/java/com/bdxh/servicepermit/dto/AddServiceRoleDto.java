package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
* @Description: 新增服务许可角色dto
* @Author Kang
* @Date 2019-06-01 10:47:48
*/
@Data
public class AddServiceRoleDto {

	/**
	 * 权限名称
	 */
	@NotEmpty(message = "权限名称不能为空")
	@ApiModelProperty("权限名称")
	private String name;

	/**
	 * 备注
	 */
	@NotEmpty(message = "备注信息不能为空")
	@ApiModelProperty("备注")
	private String remark;



}