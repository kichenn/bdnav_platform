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
* @Date 2019-07-11 09:40:53
*/
@Data
@Table(name = "t_wallet_recharge")
public class WalletRecharge {

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
	private Long userId;

	/**
	 * 用户姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 学号/工号/卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 平台订单号
	 */
	@Column(name = "order_no")
	private Long orderNo;

	/**
	 * 充值：第三方支付订单号/线下充值订单号
	 */
	@Column(name = "out_order_no")
	private String outOrderNo;

	/**
	 * 充值类型  1线上充值   2 线上代充  3 一体机自充  4窗口充值
	 */
	@Column(name = "recharge_type")
	private Byte rechargeType;

	/**
	 * 状态 1 未支付 2 支付中 3 支付成功 4 支付失败 
	 */
	@Column(name = "recharge_status")
	private Byte rechargeStatus;

	/**
	 * 充值金额
	 */
	@Column(name = "recharge_amount")
	private BigDecimal rechargeAmount;

	/**
	 * 支付时间
	 */
	@Column(name = "recharge_time")
	private Date rechargeTime;

	/**
	 * 线下充值时的设备编号
	 */
	@Column(name = "device_number")
	private String deviceNumber;

	/**
	 * 父母为子女充值（父母代充时不为空）
	 */
	@Column(name = "family_id")
	private Long familyId;

	/**
	 * 家长的卡号（父母代充时不为空）
	 */
	@Column(name = "family_number")
	private String familyNumber;

	/**
	 * 父母姓名（父母代充时不为空）
	 */
	@Column(name = "family_name")
	private String familyName;

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