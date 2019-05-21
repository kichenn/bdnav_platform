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
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 策略名称
	 */
	@Column(name = "policy_name")
	private String policyName;

	/**
	 * 学校ID
	 */
	@Column(name = "school_id")
	private Long schoolId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 部门ID
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 是否递归权限 1 是 2 否
	 */
	@Column(name = "recursion_permission")
	private Byte recursionPermission;

	/**
	 * 递归权限ids
	 */
	@Column(name = "recursion_permission_ids")
	private String recursionPermissionIds;

	/**
	 * 模式优先级
	 */
	@Column(name = "priority")
	private Byte priority;

	/**
	 * 模式主键
	 */
	@Column(name = "model_id")
	private Long modelId;

	/**
	 * 开始日期
	 */
	@Column(name = "start_date")
	private Date startDate;

	/**
	 * 结束日期
	 */
	@Column(name = "end_date")
	private Date endDate;

	/**
	 * 周时间段(1允许，0不允许)
	 */
	@Column(name = "day_mark")
	private String dayMark;

	/**
	 * 日时间段
	 */
	@Column(name = "time_mark")
	private String timeMark;

	/**
	 * 排除日期
	 */
	@Column(name = "exclusion_days")
	private String exclusionDays;

	/**
	 * 推送状态 1 未推送 2 已推送
	 */
	@Column(name = "push_state")
	private Byte pushState;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 修改时间
	 */
	@Column(name = "update_date")
	private Date updateDate;

	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private Long operator;

	/**
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

}