package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-11 09:56:14
*/
@Data
@Table(name = "t_black_url")
public class BlackUrl {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	 * 网站名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 填写域名或者ip
	 */
	@Column(name = "ip")
	private String ip;

	/**
	 * 状态 1 启用 2 禁用
	 */
	@Column(name = "status")
	private Byte status;

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