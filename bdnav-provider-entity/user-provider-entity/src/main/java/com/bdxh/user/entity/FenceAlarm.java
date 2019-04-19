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
@Table(name = "t_fence_alarm")
public class FenceAlarm {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 围栏ID
	 */
	@Column(name = "fence_id")
	private Integer fenceId;

	/**
	 * 围栏名称
	 */
	@Column(name = "fence_name")
	private String fenceName;

	/**
	 * 消息类型
	 */
	@Column(name = "type")
	private Byte type;

	/**
	 * 监控对象
	 */
	@Column(name = "monitored_person")
	private String monitoredPerson;

	/**
	 * 出入动作
	 */
	@Column(name = "action")
	private String action;

	/**
	 * 报警轨迹点
	 */
	@Column(name = "alarm_point")
	private String alarmPoint;

	/**
	 * 报警前轨迹点
	 */
	@Column(name = "pre_point")
	private String prePoint;

	/**
	 * 学校ID
	 */
	@Column(name = "school_id")
	private Long schoolId;

	/**
	 * 学校CODE
	 */
	@Column(name = "school_code")
	private String schoolCode;

	/**
	 * 学校名称
	 */
	@Column(name = "school_name")
	private String schoolName;
	/**
	 * 学生id
	 */
	@Column(name = "student_id")
	private String studentId;
	/**
	 * 学生卡号
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * 姓名
	 */
	@Column(name = "student_name")
	private String studentName;
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
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;


}