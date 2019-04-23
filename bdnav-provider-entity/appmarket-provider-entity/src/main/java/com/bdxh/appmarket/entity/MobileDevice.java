package com.bdxh.appmarket.entity;

import javax.persistence.*;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;


/**
* @Description: 实体类
* @Author Kang
* @Date 2019-04-22 16:28:40
*/
@Data
@Table(name = "t_mobile_device")
public class MobileDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("设备名称")
	private Long Id;

	@Column(name = "device_name")
	@ApiModelProperty("设备名称")
	private String DeviceName;

	@Column(name = "package_name")
	@ApiModelProperty("包名")
	private String PackageName;

	@Column(name = "status")
	@ApiModelProperty("状态 1是可用，2是不可用")
	private Integer Status;

	@Column(name = "remark")
	@ApiModelProperty("备注")
	private String Remark;

	@Column(name = "create_date")
	@ApiModelProperty("创建时间")
	private Date CreateDate;

	@Column(name = "update_date")
	@ApiModelProperty("更新时间")
	private Date UpdateDate;


}