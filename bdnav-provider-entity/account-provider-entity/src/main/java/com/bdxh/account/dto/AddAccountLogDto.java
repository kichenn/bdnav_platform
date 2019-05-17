package com.bdxh.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 增加账户日志信息实体类
 * @Author: Kang
 * @Date: 2019/5/16 16:02
 */
@Data
public class AddAccountLogDto implements Serializable {

    private static final long serialVersionUID = -3143982832501242308L;

    @ApiModelProperty("学校主键")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("所属班级主键")
    private String groupId;

    @ApiModelProperty("用户主键")
    private Long userId;

    @ApiModelProperty("学号")
    private String cardNumber;

    @ApiModelProperty("用户手机")
    private String userPhone;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("登录账号")
    private String loginName;

    @ApiModelProperty("设备IMEI")
    private String imei;

    @ApiModelProperty("个推推送ID")
    private String clientId;

    @ApiModelProperty("操作系统 1是安卓 2是IOS")
    private Byte operationSystem;

    @ApiModelProperty("备注")
    private String remark;

}