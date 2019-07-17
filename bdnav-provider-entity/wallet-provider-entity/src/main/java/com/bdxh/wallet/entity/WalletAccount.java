package com.bdxh.wallet.entity;

import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-07-11 09:40:53
*/
@Data
@Table(name = "t_wallet_account")
public class WalletAccount {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
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
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 用户主键
	 */
	@Column(name = "user_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	/**
	 * 学号、工号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 用户姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 用户类型 1家长 2学生 3老师
	 */
	@Column(name = "user_type")
	private Byte userType;

	/**
	 * 所属组织架构
	 */
	@Column(name = "org_id")
	private Long orgId;

	/**
	 * 实体卡
	 */
	@Column(name = "physical_number")
	private String physicalNumber;

	/**
	 * 账户金额
	 */
	@Column(name = "amount")
	private BigDecimal amount;

	/**
	 * 支付密码
	 */
	@Column(name = "pay_password")
	private String payPassword;

	/**
	 * 免密金额 0为不免密 大于0校验金额
	 */
	@Column(name = "quick_pay_money")
	private BigDecimal quickPayMoney;

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
	 * 账户状态：1 正常  2异常
	 */
	@Column(name = "status")
	private Byte status;

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