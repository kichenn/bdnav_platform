package com.bdxh.appmarket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
* @Description: 实体类
* @Author Kang
* @Date 2019-05-14 12:15:06
*/
@Data
@Table(name = "t_app_version")
public class UpdateAppVersionDto {

	/**
	 * 主键
	 */
	@ApiModelProperty(name = "主键")
	private Long id;

	/**
	 * 应用id
	 */
	@ApiModelProperty(name = "应用id")
	private Long appId;

	/**
	 * 应用版本号
	 */
	@ApiModelProperty(name = "应用版本号")
	private String appVersion;

	/**
	 * apk文件名称
	 */
	@ApiModelProperty(name = "apk文件名称")
	private String apkName;

	/**
	 * apk文件下载地址
	 */
	@ApiModelProperty(name = "apk文件下载地址")
	private String apkUrl;

	/**
	 * 文件服务器名称
	 */
	@ApiModelProperty(name = "文件服务器名称")
	private String apkUrlName;

	/**
	 * apk文件大小
	 */
	@ApiModelProperty(name = "apk文件大小")
	private Long apkSize;

	/**
	 * apk描述
	 */
	@ApiModelProperty(name = "apk描述")
	private String apkDesc;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "创建时间")
	private Date createDate;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(name = "修改时间")
	private Date updateDate;

	/**
	 * 操作人
	 */
	@ApiModelProperty(name = "操作人")
	private Long operator;

	/**
	 * 操作人姓名
	 */
	@ApiModelProperty(name = "操作人姓名")
	private String operatorName;

	/**
	 * 备注
	 */
	@ApiModelProperty(name = "备注")
	private String remark;


}