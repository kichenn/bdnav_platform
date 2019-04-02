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
	private Long id;

	/**
	 * 应用id
	 */
	@Column(name = "app_id")
	private Long appId;

	/**
	 * 应用名称
	 */
	@Column(name = "app_name")
	private String appName;

	/**
	 * 应用描述
	 */
	@Column(name = "app_desc")
	private String appDesc;

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
	 * 操作人
	 */
	@Column(name = "operator")
	private Long operator;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

}