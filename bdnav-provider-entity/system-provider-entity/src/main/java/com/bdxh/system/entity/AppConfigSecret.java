package com.bdxh.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;

/**
* @Description: 应用秘钥实体
* @Author xuyuan
* @Date 2019-03-21 15:23:32
*/
@Data
@Table(name = "sys_app_config_secret")
public class AppConfigSecret implements Serializable {

	private static final long serialVersionUID = -104456892042614703L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

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
	 * 应用id
	 */
	@Column(name = "app_id")
	private Long appId;

	/**
	 * 商户号
	 */
	@Column(name = "mch_id")
	private Long mchId;

	/**
	 * 商户名称
	 */
	@Column(name = "mch_name")
	private String mchName;

	/**
	 * 秘钥
	 */
	@Column(name = "app_secret")
	private String appSecret;

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