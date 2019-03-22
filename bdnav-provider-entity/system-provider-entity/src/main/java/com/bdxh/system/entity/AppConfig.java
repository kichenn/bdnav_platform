package com.bdxh.system.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;

/**
* @Description: 应用配置实体
* @Author xuyuan
* @Date 2019-03-21 15:23:32
*/
@Data
@Table(name = "sys_app_config")
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 3548993850045024791L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	/**
	 * 应用id
	 */
	@Column(name = "app_id")
	private Long AppId;

	/**
	 * 应用名称
	 */
	@Column(name = "app_name")
	private String AppName;

	/**
	 * 应用描述
	 */
	@Column(name = "app_desc")
	private String AppDesc;

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

	/**
	 * 操作人
	 */
	@Column(name = "operator")
	private Long Operator;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String Remark;

}