package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Long;
import java.lang.Byte;
import java.lang.Integer;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-11 09:56:15
*/
@Data
@Table(name = "t_school_fence")
public class SchoolFence {

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
	 * 用户群类型 1 学生 2 老师
	 */
	@Column(name = "group_type")
	private Byte groupType;

	/**
	 * 部门id
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 是否递归权限 1 是 2 否
	 */
	@Column(name = "recursion_permission")
	private Byte recursionPermission;

	/**
	 * 递归权限ids
	 */
	@Column(name = "recursion_permission_ids")
	private String recursionPermissionIds;

	/**
	 * 围栏id 百度返回
	 */
	@Column(name = "fence_id")
	private Integer fenceId;

	/**
	 * 围栏名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 经度
	 */
	@Column(name = "longitude")
	private BigDecimal longitude;

	/**
	 * 维度
	 */
	@Column(name = "latitude")
	private BigDecimal latitude;

	/**
	 * 半径
	 */
	@Column(name = "radius")
	private BigDecimal radius;

	/**
	 * 坐标类型
	 */
	@Column(name = "coord_type")
	private String coordType;

	/**
	 * 围栏去噪参数
	 */
	@Column(name = "denoise")
	private Integer denoise;

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