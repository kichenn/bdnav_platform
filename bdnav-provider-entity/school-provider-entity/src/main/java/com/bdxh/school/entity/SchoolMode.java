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

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 学校ID
	 */
	@Column(name = "school_id")
	private Long schoolId;

	/**
	 * 学校CODE
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 模式名称
	 */
	@Column(name = "model_name")
	private String modelName;

	/**
	 * 使用平台 1安卓 2苹果
	 */
	@Column(name = "platform")
	private String platform;

	/**
	 * 模式优先级
	 */
	@Column(name = "priority")
	private Byte priority;

	/**
	 * 可用的应用
	 */
	@Column(name = "usable_app")
	private String usableApp;

	/**
	 * 可用的设备
	 */
	@Column(name = "usable_device")
	private String usableDevice;

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