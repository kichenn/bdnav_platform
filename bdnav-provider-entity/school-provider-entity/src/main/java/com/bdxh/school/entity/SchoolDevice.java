package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-03-27 10:49:27
*/
@Data
@Table(name = "t_school_device")
public class SchoolDevice {

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
	 * 设备编码
	 */
	@Column(name = "device_id")
	private String deviceId;

	/**
	 * 设备类型
	 */
	@Column(name = "device_type")
	private Byte deviceType;

	/**
	 * 设备类型名称
	 */
	@Column(name = "device_type_name")
	private String deviceTypeName;

	/**
	 * 设备品牌
	 */
	@Column(name = "device_brand")
	private String deviceBrand;

	/**
	 * 设备型号
	 */
	@Column(name = "device_model")
	private String deviceModel;

	/**
	 * 设备名称
	 */
	@Column(name = "device_name")
	private String deviceName;

	/**
	 * 设备地址
	 */
	@Column(name = "device_address")
	private String deviceAddress;

	/**
	 * ip
	 */
	@Column(name = "device_ip")
	private String deviceIp;

	/**
	 * 设备状态 1 正常 2 离线
	 */
	@Column(name = "device_status")
	private Byte deviceStatus;

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