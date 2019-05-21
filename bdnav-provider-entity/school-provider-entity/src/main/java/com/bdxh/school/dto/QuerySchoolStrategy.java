package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QuerySchoolStrategy extends Query {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 策略名称
     */
    @ApiModelProperty("策略名称")
    private String policyName;

    /**
     * 学校ID
     */
    @ApiModelProperty("学校ID")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long groupId;

    /**
     * 是否递归权限 1 是 2 否
     */
    @ApiModelProperty("是否递归权限 1 是 2 否")
    private Byte recursionPermission;

    /**
     * 递归权限ids
     */
    @ApiModelProperty("递归权限ids")
    private String recursionPermissionIds;

    /**
     * 模式优先级
     */
    @ApiModelProperty("优先级")
    private Byte priority;

    /**
     * 模式主键
     */
    @ApiModelProperty("模式主键")
    private Long modelId;

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
     * 推送状态 1 未推送 2 已推送
     */
    @ApiModelProperty("推送状态 1 未推送 2 已推送")
    private Byte pushState;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String groupName;

    /**
     * 模式名称
     */
    @ApiModelProperty("模式名称")
    private String modelName;

    /**
     * 适用平台
     */
    @ApiModelProperty("适用平台 1安卓  2苹果")
    private String platform;


}
