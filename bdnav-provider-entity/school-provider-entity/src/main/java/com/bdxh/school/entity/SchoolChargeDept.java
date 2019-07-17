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
* @Date 2019-07-10 18:12:31
*/
@Data
@Table(name = "t_school_charge_dept")
public class SchoolChargeDept {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 学校主键
	 */
	@Column(name = "school_id")
	private Long schoolId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String schoolCode;



	/**
	 * 收费部门名称
	 */
	@Column(name = "charge_dept_name")
	private String chargeDeptName;

	/**
	 * 部门类型 1 充值部门 2 消费部门
	 */
	@Column(name = "charge_dept_type")
	private Byte chargeDeptType;


	/**
	 * 消费类型 1 餐饮美食 2生活日用  3读书学习 4医疗保健 5其他分类
	 */
	@Column(name = "consume_type")
	private Byte consumeType;

	/**
	 * 操作人主键
	 */
	@Column(name = "operator")
	private Long operator;

	/**
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

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
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}