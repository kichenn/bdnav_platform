package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.DeviceStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 设备列表分页查询条件
 * @Author: Kang
 * @Date: 2019/3/27 15:34
 */
@Data
public class SchoolDeviceQueryDto extends Query {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("设备编码")
    private String deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备地址")
    private String deviceAddress;

    @ApiModelProperty("设备状态 1 正常 2 离线")
    private DeviceStatusEnum deviceStatusEnum;

}