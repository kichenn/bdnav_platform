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
@Table(name = "t_wallet_consumer")
public class WalletConsumer {

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
	 * 学生姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 学号/工号/卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 物理卡号（刷卡消费是不为空）
	 */
	@Column(name = "physical_number")
	private String physicalNumber;

	/**
	 * 虚拟卡号
	 */
	@Column(name = "virtual_number")
	private String virtualNumber;

	/**
	 * 消费时的设备编码
	 */
	@Column(name = "device_number")
	private String deviceNumber;

	/**
	 * 平台订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 外部设备生成的订单号
	 */
	@Column(name = "out_order_no")
	private String outOrderNo;

	/**
	 * 消费金额
	 */
	@Column(name = "consumer_amount")
	private BigDecimal consumerAmount;

	/**
	 * 消费支付时间
	 */
	@Column(name = "consumer_time")
	private Date consumerTime;

	/**
	 * 业务类型：1刷卡 2 刷码  3扫码  4人脸识别
	 */
	@Column(name = "consumer_type")
	private Byte consumerType;

	/**
	 * 支付状态 1 未扣款  2扣款成功  3 扣款中 4 扣款失败
	 */
	@Column(name = "consumer_status")
	private Byte consumerStatus;

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