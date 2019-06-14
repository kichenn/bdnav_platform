package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 推送策略返参
 */
@Data
public class MobileStrategyVo {

    /**
     * 策略名称
     */
    @ApiModelProperty("策略名称")
    private String policyName;


    /**
     * 模式优先级
     */
    @ApiModelProperty("优先级")
    private Byte priority;


    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    private Date startDate;

    /**
     * 结束日期
     */
    @ApiModelProperty("结束日期")
    private Date endDate;

    /**
     * 周时间段(1允许，0不允许)
     */
    @ApiModelProperty("周时间段")
    private String dayMark;

    /**
     * 日时间段
     */
    @ApiModelProperty("日时间段")
    private String timeMark;

    /**
     * 排除日期
     */
    @ApiModelProperty("排除日期")
    private String exclusionDays;

    /**
     * 可用的设备
     */
    @ApiModelProperty("可用的设备")
    private String usableDevice;

    /**
     * 应用包名
     */
    @ApiModelProperty("应用包名")
    private List<String> appPackage;

}
