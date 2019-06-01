package com.bdxh.servicepermit.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
* @Description: 实体类
* @Author Kang
<<<<<<< HEAD
* @Date 2019-06-01 11:27:34
=======
* @Date 2019-05-31 11:36:26
>>>>>>> 155daf9faf19898290ead2286b7fbbfb47f88f7a
*/
@Data
@Table(name = "t_service_role_permit")
public class ServiceRolePermit {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
<<<<<<< HEAD
	 * 学校编码
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 家长号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
=======
>>>>>>> 155daf9faf19898290ead2286b7fbbfb47f88f7a
	 * 服务许可主键
	 */
	@Column(name = "service_user_id")
	private Long serviceUserId;

	/**
	 * 服务权限主键
	 */
	@Column(name = "service_role_id")
	private Long serviceRoleId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column(name = "update_date")
	private Date updateDate;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}