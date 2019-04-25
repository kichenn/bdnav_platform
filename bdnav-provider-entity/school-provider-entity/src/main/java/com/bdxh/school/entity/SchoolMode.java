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
	private Long Id;

	@Column(name = "school_id")
	private Long SchoolId;

	@Column(name = "school_code")
	private String SchoolCode;

	@Column(name = "model_name")
	private String ModelName;

	@Column(name = "platform")
	@ApiModelProperty("适用平台 1安卓  2苹果")
	private String Platform;

	@Column(name = "priority")
	private Integer Priority;

	@Column(name = "usable_app")
	private String UsableApp;

	@Column(name = "usable_device")
	private String UsableDevice;


	@Column(name = "create_date")
	private Date CreateDate;

	@Column(name = "update_date")
	private Date UpdateDate;

	
	@Column(name = "operator")
	private Long Operator;

	@Column(name = "operator_name")
	private String OperatorName;

	@Column(name = "remark")
	private String Remark;


}