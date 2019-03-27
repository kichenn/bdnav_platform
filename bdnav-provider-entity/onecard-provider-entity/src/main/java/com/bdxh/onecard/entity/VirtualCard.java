package com.bdxh.onecard.entity;

import javax.persistence.*;
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
@Table(name = "t_virtual_card")
public class VirtualCard {

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
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 基本用户id
	 */
	@Column(name = "base_user_id")
	private Long baseUserId;

	/**
	 * 账户类型 1 学生 2 老师 3 家长
	 */
	@Column(name = "user_type")
	private Byte userType;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 账户总余额
	 */
	@Column(name = "money")
	private BigDecimal money;

	/**
	 * 可用余额
	 */
	@Column(name = "avalible_money")
	private BigDecimal avalibleMoney;

	/**
	 * 冻结余额
	 */
	@Column(name = "freeze_money")
	private BigDecimal freezeMoney;

	/**
	 * 状态 1 正常 2 异常
	 */
	@Column(name = "valid")
	private Byte valid;

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