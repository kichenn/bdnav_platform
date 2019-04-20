package com.bdxh.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Data
@ApiModel(value = "学生围栏警报Vo类")
public class FenceAlarmVo implements Serializable {

	private static final long serialVersionUID = 8136057428613025228L;
	/**
	 * id
	 */
	@ApiModelProperty("id")
	@JsonSerialize(using= ToStringSerializer.class)
	private String id;

	/**
	 * 围栏ID
	 */
	@ApiModelProperty("围栏ID")
	@JsonSerialize(using= ToStringSerializer.class)
	private Integer fenceId;

	/**
	 * 围栏名称
	 */
	@ApiModelProperty("围栏名称")
	private String fenceName;

	/**
	 * 消息类型
	 */
	@ApiModelProperty("消息类型")
	private Byte type;

	/**
	 * 监控对象
	 */
	@ApiModelProperty("监控对象")
	private String monitoredPerson;

	/**
	 * 出入动作
	 */
	@ApiModelProperty("出入动作")
	private String action;

	/**
	 * 报警轨迹点
	 */
	@ApiModelProperty("报警轨迹点")
	private String alarmPoint;

	/**
	 * 报警前轨迹点
	 */
	@ApiModelProperty("报警前轨迹点")
	private String prePoint;

	/**
	 * 学校ID
	 */
	@ApiModelProperty("学校ID")
	private Long schoolId;
	/**
	 * 学校名称
	 */
	@ApiModelProperty("学校名称")
	private String schoolName;

	/**
	 * 学校CODE
	 */
	@ApiModelProperty("学校CODE")
	private String schoolCode;

	/**
	 * 学生卡号
	 */
	@ApiModelProperty("学生卡号")
	private String cardNumber;

	/**
	 * 学生ID
	 */
	@ApiModelProperty("学生ID")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long studentId;


	/**
	 * 学生姓名
	 */
	@ApiModelProperty("学生姓名")
	private String studentName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createDate;

	/**
	 * 修改时间
	 */
	@ApiModelProperty("修改时间")
	private Date updateDate;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;


}