package com.bdxh.servicepermit.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Data
@Table(name = "t_service_used")
public class ServiceUsed {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 订单主键
	 */
	@Column(name = "order_no")
	private Long orderNo;

	/**
	 * 产品主键
	 */
	@Column(name = "product_id")
	private Long productId;

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
	 * 家长主键
	 */
	@Column(name = "family_id")
	private Long familyId;

	/**
	 * 家长主键
	 */
	@Column(name = "card_number")
	private Long cardNumber;

	/**
	 * 开始使用时间
	 */
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 状态 1 正常使用 2已过期
	 */
	@Column(name = "status")
	private Byte status;

	/**
	 * 类型 1是试用  2是正式使用
	 */
	@Column(name = "type")
	private Byte type;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 修改日期
	 */
	@Column(name = "update_date")
	private Date updateDate;


}