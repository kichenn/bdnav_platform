package com.bdxh.wallet.entity;

import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
* @Date 2019-07-11 09:40:52
*/
@Data
@Table(name = "t_physical_card")
public class PhysicalCard {

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
	 * 用户主键
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 学号、工号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 钱包账户主键
	 */
	@Column(name = "wallet_account_id")
	private Long walletAccountId;

	/**
	 * 卡物理号
	 */
	@Column(name = "physical_number")
	private String physicalNumber;

	/**
	 * 物理卡芯片号
	 */
	@Column(name = "physical_chip_number")
	private String physicalChipNumber;

	/**
	 * 押金
	 */
	@Column(name = "deposit")
	private BigDecimal deposit;

	/**
	 * 物理卡状态 1 正常 2 挂失 3注销
	 */
	@Column(name = "status")
	private Byte status;

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