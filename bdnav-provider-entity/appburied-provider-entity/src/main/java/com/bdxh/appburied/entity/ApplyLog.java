package com.bdxh.appburied.entity;

import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@Table(name = "t_apply_log")
public class ApplyLog {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using= ToStringSerializer.class)
	private Long id;

	/**
	 * 平台 1 android 2 ios
	 */
	@Column(name = "platform")
	private Byte platform;

	/**
	 * 账户id
	 */
	@Column(name = "account_id")
	private Long accountId;

	/**
	 * 推送标识
	 */
	@Column(name = "client_id")
	private String clientId;

	/**
	 * 模式 1 单个应用解锁 2 全部解锁 
	 */
	@Column(name = "model")
	private Byte model;

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
	 * 用户卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 用户姓名
	 */
	@Column(name = "user_name")
	private String userName;

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
	 * 是否读取：1：已读、2：未读
	 */
	@Column(name="is_read")
	private Byte isRead;

	/**
	 *申请理由
	 */
	@Column(name = "reason")
	private String reason;

	/**
	 * 操作人编码
	 */
	@Column(name = "operator_code")
	private String operatorCode;

	/**
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 操作状态 1 待审核 2审核拒绝 3 审核通过 
	 */
	@Column(name = "operator_status")
	private Byte operatorStatus;

	/**
	 * 审核意见
	 */
	@Column(name="review")
	private String review;

	/**
	 * 开始时间
	 */
	@Column(name = "start_date")
	private Date startDate;

	/**
	 * 结束时间
	 */
	@Column(name = "end_date")
	private Date endDate;

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
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}