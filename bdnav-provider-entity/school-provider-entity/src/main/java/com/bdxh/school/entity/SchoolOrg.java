package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;
import java.lang.Integer;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-05-31 14:06:09
*/
@Data
@Table(name = "t_school_org")
public class SchoolOrg {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 父级id
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 父级ids
	 */
	@Column(name = "parent_ids")
	private String parentIds;

	/**
	 * 父级names
	 */
	@Column(name = "parent_names")
	private String parentNames;

	/**
	 * 组织架构路径
	 */
	@Column(name = "this_url")
	private String thisUrl;

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
	 * 组织名称
	 */
	@Column(name = "org_name")
	private String orgName;

	/**
	 * 类型 1 学院 2 系 3 专业 4 年级 5 班级,6 行政,7 党团 8 教学 9 后勤 10其他
	 */
	@Column(name = "org_type")
	private Byte orgType;

	/**
	 * 层级
	 */
	@Column(name = "level")
	private Byte level;

	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;

	/**
	 * 管理员Id
	 */
	@Column(name = "manage_id")
	private Long manageId;

	/**
	 * 管理员微校卡号
	 */
	@Column(name = "manage_card_number")
	private String manageCardNumber;

	/**
	 * 管理员名称
	 */
	@Column(name = "manage_name")
	private String manageName;
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
	 * 操作人id
	 */
	@Column(name = "operator")
	private Long operator;

	/**
	 * 操作人
	 */
	@Column(name = "operator_name")
	private String operatorName;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}