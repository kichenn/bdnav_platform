package com.bdxh.servicepermit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 服务许可信息vo
 * @Author: Kang
 * @Date: 2019/6/1 15:00
 */
@Data
public class ServiceRolePermitInfoVo {

    @ApiModelProperty("家长id")
    private Long familyId;

    @ApiModelProperty("家长卡号")
    private String familyCardNumber;

    @ApiModelProperty("学生卡号")
    private String studentCardNumber;

    @ApiModelProperty("学生名称")
    private String studentName;

    @ApiModelProperty("许可类型(1.试用，2.正式使用)")
    private Byte type;

    @ApiModelProperty("许可id")
    private Long serviceUserId;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色类型")
    private String roleName;
}
