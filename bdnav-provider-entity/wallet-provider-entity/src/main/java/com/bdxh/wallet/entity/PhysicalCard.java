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
	@ApiModelProperty("学校主键")
	private Long schoolId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	@ApiModelProperty("学校编码")
	private String schoolCode;

	/**
	 * 用户主键
	 */
	@Column(name = "user_id")
	@ApiModelProperty("用户主键")
	private Long userId;

	/**
	 * 学号、工号
	 */
	@Column(name = "card_number")
	@ApiModelProperty("学号")
	private String cardNumber;

	/**
	 * 钱包账户主键
	 */
	@Column(name = "wallet_account_id")
	@ApiModelProperty("钱包账户主键")
	private Long walletAccountId;

	/**
	 * 卡物理号
	 */
	@Column(name = "physical_number")
	@ApiModelProperty("卡物理号")
	private String physicalNumber;

	/**
	 * 物理卡芯片号
	 */
	@Column(name = "physical_chip_number")
	@ApiModelProperty("物理卡芯片号")
	private String physicalChipNumber;

	/**
	 * 押金
	 */
	@Column(name = "deposit")
	@ApiModelProperty("押金")
	private BigDecimal deposit;

	/**
	 * 物理卡状态 1 正常 2 挂失 3注销
	 */
	@Column(name = "status")
	@ApiModelProperty("物理卡状态 1 正常 2 挂失 3注销")
	private Byte status;

	/**
	 * 操作人
	 */
	@Column(name = "operator")
	@ApiModelProperty("操作人")
	private Long operator;

	/**
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	@ApiModelProperty("操作人姓名")
	private String operatorName;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	@ApiModelProperty("创建时间")
	private Date createDate;

	/**
	 * 修改时间
	 */
	@Column(name = "update_date")
	@ApiModelProperty("修改时间")
	private Date updateDate;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	@ApiModelProperty("备注")
	private String remark;


}