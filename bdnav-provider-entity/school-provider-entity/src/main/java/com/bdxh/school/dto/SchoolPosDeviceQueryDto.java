package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import com.bdxh.school.enums.DeviceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WanMing
 * @date 2019/7/11 16:14
 */
@Data
public class SchoolPosDeviceQueryDto extends Query {


    @ApiModelProperty("学校编码")
    private String schoolCode;

    @ApiModelProperty("设备编码")
    private String deviceId;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备类型 1.post机，2.门禁机，3.打卡机")
    private Byte deviceType;


}
