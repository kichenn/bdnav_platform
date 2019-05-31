package com.bdxh.school.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
* @Description: 实体类
* @Author WanMing
* @Date 2019-05-28 10:24:20
*/
@Data
@Table(name = "t_school_servicepermit")
public class SchoolServicePermit implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;

	/**
	 * 服务开始时间
	 */
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 服务结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	/**
	 * 状态 1 服务有效  2 服务无效
	 */
	@Column(name = "status")
	private Byte status;


	/**
	 * 延期天数
	 */
	@Column(name = "add_days")
	private Byte addDays;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

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


}