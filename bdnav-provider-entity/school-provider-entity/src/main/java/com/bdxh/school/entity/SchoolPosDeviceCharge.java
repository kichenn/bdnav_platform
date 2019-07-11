package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
* @Description: 实体类
* @Author WanMing
* @Date 2019-07-11 10:58:37
*/
@Data
@Table(name = "t_school_posdevice_charge")
public class SchoolPosDeviceCharge {

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
	 * 设备主键
	 */
	@Column(name = "device_id")
	private String deviceId;

	/**
	 * 所属收费部门
	 */
	@Column(name = "charge_dept_id")
	private Long chargeDeptId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column(name = "update_date")
	private Date updateDate;

	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private Long operator;

	/**
	 * 操作人名称
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 注释
	 */
	@Column(name = "remark")
	private String remark;


}