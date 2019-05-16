package com.bdxh.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class AccountLogsMongo {

    @ApiModelProperty("主键")
    private Long Id;


    @ApiModelProperty("学校主键")
    private Long SchoolId;


    @ApiModelProperty("学校编码")
    private String SchoolCode;


    @ApiModelProperty("学校名称")
    private String SchoolName;


    @ApiModelProperty("所属班级主键")
    private Long GroupId;


    @ApiModelProperty("用户主键")
    private Long UserId;


    @ApiModelProperty("学号")
    private String CardNumber;


    @ApiModelProperty("用户手机")
    private String UserPhone;


    @ApiModelProperty("姓名")
    private String UserName;


    @ApiModelProperty("登录账号")
    private String LoginName;


    @ApiModelProperty("设备IMEI")
    private String Imei;


    @ApiModelProperty("个推推送ID")
    private String ClientId;


    @ApiModelProperty("操作系统 1是安卓 2是IOS")
    private Integer OperationSystem;


    @ApiModelProperty("创建时间")
    private Date CreateDate;


    @ApiModelProperty("备注")
    private String Remark;


}
