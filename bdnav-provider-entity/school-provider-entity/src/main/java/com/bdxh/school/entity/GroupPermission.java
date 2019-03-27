package com.bdxh.school.entity;

import javax.persistence.*;

import lombok.Data;
import java.util.Date;
import java.lang.String;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-03-27 10:00:24
*/
@Data
@Table(name = "t_group_permission")
public class GroupPermission {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 学校id
	 */
	@Column(name = "school_id")
	private Long schoolId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 用户群类型 1 学生 2 老师
	 */
	@Column(name = "group_type")
	private Byte groupType;

	/**
	 * 部门id
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
	 * 设备id
	 */
	@Column(name = "device_id")
	private Long deviceId;

	/**
	 * 设备编码
	 */
	@Column(name = "device_code")
	private String deviceCode;

	/**
	 * 权限标识 1 允许 2 不允许
	 */
	@Column(name = "access_flag")
	private Byte accessFlag;

	/**
	 * 允许开始时间
	 */
	@Column(name = "access_begin")
	private Date accessBegin;

	/**
	 * 允许结束时间
	 */
	@Column(name = "access_end")
	private Date accessEnd;

	/**
	 * 周时间段
	 */
	@Column(name = "access_day_mark")
	private String accessDayMark;

	/**
	 * 日时间段
	 */
	@Column(name = "access_time_mark")
	private String accessTimeMark;

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