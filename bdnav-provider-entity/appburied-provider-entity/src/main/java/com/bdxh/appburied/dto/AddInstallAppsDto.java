package com.bdxh.appburied.dto;

import com.bdxh.appburied.enums.InstallAppsPlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
* @Description: 添加上报Appdto、
* @Author: Kang
* @Date: 2019/4/12 9:43
*/
@Data
public class AddInstallAppsDto {

	@ApiModelProperty("平台 1 android 2 ios")
	private InstallAppsPlatformEnum installAppsPlatformEnum;

	@ApiModelProperty("账户id")
	private Long accountId;

	@NotEmpty(message = "学校编码不能为空")
	@ApiModelProperty("学校编码")
	private String schoolCode;

	@ApiModelProperty("学校名称")
	private String schoolName;

	@NotEmpty(message = "用户学号不能为空")
	@ApiModelProperty("用户学号")
	private String cardNumber;

	@ApiModelProperty("用户姓名")
	private String userName;

	@ApiModelProperty("应用名称")
	private String appName;

	@ApiModelProperty("应用包名")
	private String appPackage;

	@ApiModelProperty("应用图标地址")
	private String iconUrl;

	@ApiModelProperty("应用图标名称")
	private String iconName;

}