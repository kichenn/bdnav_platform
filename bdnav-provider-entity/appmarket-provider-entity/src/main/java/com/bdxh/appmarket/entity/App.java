package com.bdxh.appmarket.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@Data
@Table(name = "t_app")
public class App {

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
	 * 平台 1 andriod 2 ios
	 */
	@Column(name = "platform")
	private Byte platform;

	/**
	 * 分类id
	 */
	@Column(name = "category_id")
	private Long categoryId;

	/**
	 * 应用名称
	 */
	@Column(name = "app_name")
	private String appName;

	/**
	 * 应用包名
	 */
	@Column(name = "app_package")
	private String appPackage;

	/**
	 * 应用图标地址
	 */
	@Column(name = "icon_url")
	private String iconUrl;

	/**
	 * 应用图标名称
	 */
	@Column(name = "icon_name")
	private String iconName;

	/**
	 * 应用版本
	 */
	@Column(name = "app_version")
	private String appVersion;

	/**
	 * 应用描述
	 */
	@Column(name = "app_desc")
	private String appDesc;

	/**
	 *  状态 1 上架 2 下架
	 */
	@Column(name = "status")
	private Byte status;

	/**
	 *  是否预置 1 是 2 否
	 */
	@Column(name = "preset")
	private Byte preset;


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
	 * 操作姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}