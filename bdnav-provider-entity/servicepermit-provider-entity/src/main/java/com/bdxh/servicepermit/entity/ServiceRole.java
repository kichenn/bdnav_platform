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
*/
@Data
@Table(name = "t_service_role")
public class ServiceRole {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 权限名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

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


}