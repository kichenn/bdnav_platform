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
@ApiModel(value = "学生访问网页日志Vo类")
public class VisitLogsVo implements Serializable {

	private static final long serialVersionUID = -2932829760837750329L;
	/**
	 * id
	 */
	@ApiModelProperty("id")
	@JsonSerialize(using= ToStringSerializer.class)
	private String id;

	/**
	 * 学校ID
	 */
	@ApiModelProperty("学校ID")
	private Integer schoolId;

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
	private Long studentId;

	/**
	 * 学生姓名
	 */
	@ApiModelProperty("学生姓名")
	private String userName;

	/**
	 * 访问的URL
	 */
	@ApiModelProperty("访问的URL")
	private String url;

	/**
	 * 访问时间
	 */
	@ApiModelProperty("访问时间")
	private Date createDate;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateDate;

	/**
	 * 状态，是否拦截： 0未拦截  1拦截
	 */
	@ApiModelProperty("状态，是否拦截： 0未拦截  1拦截")
	private Byte status;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;


}