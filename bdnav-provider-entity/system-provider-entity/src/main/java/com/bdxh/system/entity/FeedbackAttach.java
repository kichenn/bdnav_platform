package com.bdxh.system.entity;

import javax.persistence.Table;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
* @Description: 用户反馈的附件实体类
* @Author wanMing
* @Date 2019-06-13 11:43:52
*/
@Data
@Table(name = "sys_feedback_attach")
public class FeedbackAttach {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 反馈表主键
	 */
	@Column(name = "feedback_id")
	private Long feedbackId;

	/**
	 * 上传的图片url
	 */
	@Column(name = "img")
	private String img;

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