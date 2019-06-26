package com.bdxh.user.entity;

import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author WanMing
* @Date 2019-06-25 10:17:12
*/
@Data
@Table(name = "t_family_black_url")
public class FamilyBlackUrl {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

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
	 * 家长号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 家长主键
	 */
	@Column(name = "family_id")
	private Long familyId;

	/**
	 * 学生主键
	 */
	@Column(name = "student_id")
	private Long studentId;

	/**
	 * 学生姓名
	 */
	@Column(name = "student_name")
	private String studentName;

	/**
	 * 学生学号
	 */
	@Column(name = "student_number")
	private String studentNumber;

	/**
	 * 网站名称
	 */
	@Column(name = "site_name")
	private String siteName;

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