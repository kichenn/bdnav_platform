package com.bdxh.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
* @Description: 实体类
* @Author xuyuan
* @Date 2019-03-26 18:27:42
*/
@Data
@Table(name = "t_base_user")
public class BaseUser implements Serializable {

	private static final long serialVersionUID = 7986372297321464764L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize(using= ToStringSerializer.class)
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
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 用户类型 1 学生 2 老师 3 家长
	 */
	@Column(name = "user_type")
	private Integer userType;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 用户姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 用户性别
	 */
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 出身日期
	 */
	@Column(name = "birth")
	private String birth;

	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 学号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 身份证号
	 */
	@Column(name = "idcard")
	private String idcard;

	/**
	 * qq号
	 */
	@Column(name = "qq_number")
	private String qqNumber;

	/**
	 * 微信号
	 */
	@Column(name = "wx_number")
	private String wxNumber;

	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;

	/**
	 * 头像地址
	 */
	@Column(name = "image")
	private String image;

	/**
	 * 头像名称
	 */
	@Column(name = "image_name")
	private String imageName;

	/**
	 * 民族名称
	 */
	@Column(name = "nation_name")
	private String nationName;

	/**
	 * 家庭住址
	 */
	@Column(name = "adress")
	private String adress;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date")
	private Date createDate;

	/**
	 * 修改日期
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