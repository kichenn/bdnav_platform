package com.bdxh.wallet.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-03-25 14:14:55
*/
@Data
@Table(name = "t_virtual_card")
public class VirtualCard implements Serializable {

	private static final long serialVersionUID = 5817590269303654495L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	private Long id;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String SchoolCode;

	/**
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String SchoolName;

	/**
	 * 账户类型 1 学生 2 老师 3 家长
	 */
	@Column(name = "user_type")
	private Integer UserType;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long UserId;

	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String UserName;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String CardNumber;

	/**
	 * 账户总余额
	 */
	@Column(name = "money")
	private BigDecimal Money;

	/**
	 * 可用余额
	 */
	@Column(name = "avalible_money")
	private BigDecimal AvalibleMoney;

	/**
	 * 冻结余额
	 */
	@Column(name = "freeze_money")
	private BigDecimal FreezeMoney;

	/**
	 * 状态 1 正常 2 异常
	 */
	@Column(name = "valid")
	private Integer Valid;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date CreateDate;

	/**
	 * 修改时间
	 */
	@Column(name = "update_date")
	private Date UpdateDate;

}