package com.bdxh.appburied.entity;

import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Data
@Table(name = "t_app_status")
public class AppStatus {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 平台 1 andriod 2 ios
	 */
	@Column(name = "platform")
	private Byte platform;

	/**
	 * 账户id
	 */
	@Column(name = "account_id")
	private Long accountId;

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
	 * 学生名称
	 */
	@Column(name = "user_name")
	private String userName;


	/**
	 * 用户卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 应用包名标识
	 */
	@Column(name = "app_package")
	private String appPackage;

	/**
	 * 应用状态 1 正常 2 锁定
	 */
	@Column(name = "app_status")
	private Byte appStatus;

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


}