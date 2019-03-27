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
@Table(name = "t_physical_card")
public class PhysicalCard {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	 * 卡类型  1 M1卡 2 CPU卡
	 */
	@Column(name = "card_type")
	private Byte cardType;

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
	 * 押金
	 */
	@Column(name = "deposit")
	private BigDecimal deposit;

	/**
	 * 使用标记 1 使用 2 未使用
	 */
	@Column(name = "use_flag")
	private Byte useFlag;

	/**
	 * 物理卡状态 1 发卡中 2 正常 3 挂失 4 退卡
	 */
	@Column(name = "status")
	private Byte status;

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