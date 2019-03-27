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
@Table(name = "t_single_permission")
public class SinglePermission {

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
	 * 用户类型 1 学生 2 老师 3 家长
	 */
	@Column(name = "user_type")
	private Byte userType;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 用户名称
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

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