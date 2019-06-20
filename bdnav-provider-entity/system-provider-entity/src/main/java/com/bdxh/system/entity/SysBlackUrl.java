package com.bdxh.system.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author wanMing
* @Date 2019-06-20 11:32:50
*/
@Data
@Table(name = "sys_black_url")
public class SysBlackUrl {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 网站名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 网站域名或者ip
	 */
	@Column(name = "ip")
	private String ip;

	/**
	 * 网站来源 1 北斗 2 金山 3 360 4 百度
	 */
	@Column(name = "origin")
	private Byte origin;

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