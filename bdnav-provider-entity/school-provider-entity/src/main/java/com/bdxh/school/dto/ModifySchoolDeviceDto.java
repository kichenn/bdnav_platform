package com.bdxh.school.dto;

import com.bdxh.school.enums.DeviceStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 修改门禁信息dto
 * @Author: Kang
 * @Date: 2019/3/27 17:43
 */
@Data
public class ModifySchoolDeviceDto {


    @NotNull
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("设备编码")
    private String deviceId;

    @ApiModelProperty("设备类型")
    private Byte deviceType;

    @ApiModelProperty("设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty("设备品牌")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备地址")
    private String deviceAddress;

    @ApiModelProperty("ip")
    private String deviceIp;

    @ApiModelProperty("设备状态 1 正常 2 离线")
    private DeviceStatusEnum deviceStatusEnum;

    @ApiModelProperty("注释")
    private String remark;


}