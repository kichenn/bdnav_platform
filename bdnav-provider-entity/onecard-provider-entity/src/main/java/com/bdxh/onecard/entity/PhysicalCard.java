package com.bdxh.onecard.entity;

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
* @Date 2019-03-25 14:14:54
*/
@Data
@Table(name = "t_physical_card")
public class PhysicalCard implements Serializable {

	private static final long serialVersionUID = -1622343661319211391L;

	/**
	 * 主键
	 */
	@Id
	private Long id;

	/**
	 * 虚拟账户id
	 */
	@Column(name = "virtual_card_id")
	private Long VirtualCardId;

	/**
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String SchoolCode;

	/**
	 * 卡号
	 */
	@Column(name = "card_number")
	private String CardNumber;

	/**
	 * 卡发行号
	 */
	@Column(name = "issue_number")
	private String IssueNumber;

	/**
	 * 卡物理号
	 */
	@Column(name = "phy_number")
	private String PhyNumber;

	/**
	 * 卡类型  1 M1卡 2 CPU卡
	 */
	@Column(name = "card_type")
	private Integer CardType;

	/**
	 * 押金
	 */
	@Column(name = "deposit")
	private BigDecimal Deposit;

	/**
	 * 使用标记 1 使用 2 未使用
	 */
	@Column(name = "use_flag")
	private Integer UseFlag;

	/**
	 * 物理卡状态 1 发卡中 2 正常 3 挂失 4 退卡
	 */
	@Column(name = "status")
	private Integer Status;

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