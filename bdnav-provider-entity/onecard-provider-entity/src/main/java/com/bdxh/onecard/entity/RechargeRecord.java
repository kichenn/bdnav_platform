package com.bdxh.onecard.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-03-27 10:54:14
*/
@Data
@Table(name = "t_recharge_record")
public class RechargeRecord {

	/**
	 * 平台订单号
	 */
	@Id
	private Long orderNo;

	/**
	 * 虚拟账户id
	 */
	@Column(name = "virtual_card_id")
	private Long virtualCardId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 充值金额
	 */
	@Column(name = "recharge_money")
	private BigDecimal rechargeMoney;

	/**
	 * 订单类型 1 线下 2 微信线上支付
	 */
	@Column(name = "order_type")
	private Byte orderType;

	/**
	 * 外部订单号
	 */
	@Column(name = "out_order_no")
	private String outOrderNo;

	/**
	 * 卡发行号
	 */
	@Column(name = "issue_number")
	private String issueNumber;

	/**
	 * 卡物理号
	 */
	@Column(name = "phy_number")
	private String phyNumber;

	/**
	 * 状态 1 未支付 2 支付中 3 支付成功 4 支付失败 5 未充值 6 充值中 7充值成功 8 充值失败
	 */
	@Column(name = "order_status")
	private Byte orderStatus;

	/**
	 * 微信订单类型 JSAPI APP等
	 */
	@Column(name = "wx_order_type")
	private String wxOrderType;

	/**
	 * 充值说明
	 */
	@Column(name = "body")
	private String body;

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
	 * 失败原因
	 */
	@Column(name = "message")
	private String message;

}