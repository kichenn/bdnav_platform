package com.bdxh.appmarket.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-05-14 12:15:06
*/
@Data
@Table(name = "t_app_version")
public class AppVersion {

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
	 * 应用版本号
	 */
	@Column(name = "app_version")
	private String appVersion;

	/**
	 * apk文件名称
	 */
	@Column(name = "apk_name")
	private String apkName;

	/**
	 * apk文件下载地址
	 */
	@Column(name = "apk_url")
	private String apkUrl;

	/**
	 * 文件服务器名称
	 */
	@Column(name = "apk_url_name")
	private String apkUrlName;

	/**
	 * apk文件大小
	 */
	@Column(name = "apk_size")
	private Long apkSize;

	/**
	 * apk描述
	 */
	@Column(name = "apk_desc")
	private String apkDesc;

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
	 * 操作人姓名
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}