package com.bdxh.user.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Data
@Table(name = "t_visit_logs")
public class VisitLogs {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 学校ID
	 */
	@Column(name = "school_id")
	private Integer schoolId;

	/**
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 学校CODE
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 学生卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;
	/**
	 * 学生卡号
	 */
	@Column(name = "student_id")
	private String studentId;
	/**
	 * 学生姓名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 访问的URL
	 */
	@Column(name = "url")
	private String url;

	/**
	 * 访问时间
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column(name = "update_date")
	private Date updateDate;

	/**
	 * 状态，是否拦截： 0未拦截  1拦截
	 */
	@Column(name = "status")
	private Byte status;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}