package com.bdxh.system.entity;

import javax.persistence.*;
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
	 * 应用id
	 */
	@Column(name = "app_id")
	private Long AppId;

	/**
	 * 商户号
	 */
	@Column(name = "mch_id")
	private Long MchId;

	/**
	 * 商户名称
	 */
	@Column(name = "mch_name")
	private String MchName;

	/**
	 * 秘钥
	 */
	@Column(name = "app_secret")
	private String AppSecret;

	/**
	 * 回调地址
	 */
	@Column(name = "notice_url")
	private String NoticeUrl;

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