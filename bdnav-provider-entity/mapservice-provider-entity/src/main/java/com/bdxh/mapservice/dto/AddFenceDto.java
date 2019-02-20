package com.bdxh.mapservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddFenceDto implements Serializable {

    private static final long serialVersionUID = -9144318970216621827L;

    /**
     * 监控对象
     */
    private String monitored_person;

    /**
     * 围栏圆心经度
     */
    @NotNull(message = "围栏圆心经度不能为空")
    private Double longitude;

    /**
     * 围栏圆心纬度
     */
    @NotNull(message = "围栏圆心纬度不能为空")
    private Double latitude;

    /**
     * 围栏半径
     */
    @NotNull(message = "围栏半径不能为空")
    private Double radius;

    /**
     * 坐标类型
     */
    private String coord_type = "bd09ll";

}
