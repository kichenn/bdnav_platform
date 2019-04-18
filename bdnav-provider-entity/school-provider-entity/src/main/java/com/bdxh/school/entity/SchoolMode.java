package com.bdxh.school.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
* @Description: 实体类
* @Date 2019-04-18 09:52:43
*/
@Data
@Table(name = "t_school_mode")
public class SchoolMode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("主键")
	private Long Id;

	@Column(name = "school_id")
	@ApiModelProperty("学校ID")
	private Long SchoolId;

	@Column(name = "school_code")
	@ApiModelProperty("学校CODE")
	private String SchoolCode;

	@Column(name = "name")
	@ApiModelProperty("模式名称")
	private String Name;

	@Column(name = "priority")
	@ApiModelProperty("模式优先级")
	private Integer Priority;

	@Column(name = "usable_app")
	@ApiModelProperty("可用的应用")
	private String UsableApp;

	@Column(name = "usable_device")
	@ApiModelProperty("可用的设备")
	private String UsableDevice;


	@Column(name = "create_date")
	@ApiModelProperty("创建时间")
	private Date CreateDate;

	@Column(name = "update_date")
	@ApiModelProperty("修改时间")
	private Date UpdateDate;

	
	@Column(name = "operator")
	@ApiModelProperty("操作人")
	private Long Operator;

	@Column(name = "operator_name")
	@ApiModelProperty("操作人姓名")
	private String OperatorName;

	@Column(name = "remark")
	@ApiModelProperty("备注")
	private String Remark;


}