package com.bdxh.BDMap.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class AddFenceDto {
    /**
     * 监控对象
     */
   @NotEmpty(message = "监控对象不能为空")
    String monitored_person;
    /**
     * 围栏圆心经度
     */
   @NotNull(message = "围栏圆心经度不能为空")
    Double longitude;
    /**
     * 围栏圆心纬度
     */
    @NotNull(message = "围栏圆心纬度不能为空")
    Double latitude;
    /**
     * 围栏半径
     */
   @NotNull(message = "围栏半径不能为空")
    Double radius;
    /**
     * 坐标类型
     */
    String coord_type="bd09ll";
}
