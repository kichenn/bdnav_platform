package com.bdxh.servicepermit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
* @Description: 增加服务权限角色权限dto
* @Author Kang
* @Date 2019-06-01 11:27:34
*/
@Data
public class AddServiceRolePermitDto {

	@ApiModelProperty("学校编码")
	private String schoolCode;

	@ApiModelProperty("家长号")
	private String cardNumber;

	@ApiModelProperty("服务许可主键")
	private Long serviceUserId;

	@ApiModelProperty("服务权限主键")
	private Long serviceRoleId;

	@ApiModelProperty("备注")
	private String remark;


}