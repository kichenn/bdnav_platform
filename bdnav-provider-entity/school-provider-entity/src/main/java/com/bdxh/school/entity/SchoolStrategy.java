package com.bdxh.school.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;


/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-18 09:52:44
*/
@Data
@Table(name = "t_school_strategy")
public class SchoolStrategy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("主键")
	private Long Id;

	@Column(name = "name")
	@ApiModelProperty("策略名称")
	private String Name;

	@Column(name = "school_id")
	@ApiModelProperty("学校ID")
	private Long SchoolId;

	@Column(name = "school_code")
	@ApiModelProperty("学校编码")
	private String SchoolCode;

	@Column(name = "group_id")
	@ApiModelProperty("部门ID")
	private Long GroupId;

	@Column(name = "recursion_permission")
	@ApiModelProperty("是否递归权限 1 是 2 否")
	private Integer RecursionPermission;

	@Column(name = "recursion_permission_ids")
	@ApiModelProperty("递归权限ids")
	private String RecursionPermissionIds;

	@Column(name = "platform")
	@ApiModelProperty("适用平台 1安卓  2苹果")
	private String Platform;

	@Column(name = "mode_id")
	@ApiModelProperty("模式主键")
	private Long ModeId;

	@Column(name = "start_date")
	@ApiModelProperty("开始日期")
	private Date StartDate;

	@Column(name = "end_date")
	@ApiModelProperty("结束日期")
	private Date EndDate;

	@Column(name = "day_mark")
	@ApiModelProperty("周时间段")
	private String DayMark;

	@Column(name = "time_mark")
	@ApiModelProperty("日时间段")
	private String TimeMark;

	@Column(name = "exclusion_days")
	@ApiModelProperty("排除日期")
	private String ExclusionDays;

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