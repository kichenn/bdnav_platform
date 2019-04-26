package com.bdxh.school.dto;

import com.bdxh.school.enums.BlackStatusEnum;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.enums.RecursionPermissionStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 添加学校围栏dto
 * @Author: Kang
 * @Date: 2019/4/11 12:17
 */
@Data
public class AddSchoolFenceDto {

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @NotNull(message = "用户群类型不能为空")
    @ApiModelProperty("用户群类型(1 学生 2 老师)")
    private GroupTypeEnum groupTypeEnum;

    @NotNull(message = "部门id不能为空")
    @ApiModelProperty("部门id")
    private Long groupId;

    @NotNull(message = "是否递归权限不能为空")
    @ApiModelProperty("是否递归权限 1 是 2 否")
    private RecursionPermissionStatusEnum recursionPermissionStatusEnum;

    @ApiModelProperty("递归权限ids")
    private String recursionPermissionIds;

    @ApiModelProperty("围栏id 百度返回")
    private Integer fenceId;

    @ApiModelProperty("围栏名称")
    private String name;

    @NotNull(message = "经度不能为空")
    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @NotNull(message = "纬度不能为空")
    @ApiModelProperty("维度")
    private BigDecimal latitude;

    @NotNull(message = "半径不能为空")
    @ApiModelProperty("半径")
    private BigDecimal radius;

    @ApiModelProperty("坐标类型（wgs84：GPS经纬度 gcj02：国测局经纬度  bd09ll：百度经纬度）")
    private String coordType;

    @ApiModelProperty("围栏去噪参数")
    private Integer denoise;

    @NotNull(message = "围栏状态不能为空")
    @ApiModelProperty("状态 1 启用 2 禁用")
    private BlackStatusEnum blackStatusEnum;

//	@ApiModelProperty("创建时间")
//	private Date createDate;
//
//	@ApiModelProperty("修改时间")
//	private Date updateDate;

    @ApiModelProperty("操作人")
    private Long operator;

    @ApiModelProperty("操作人姓名")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

    /**
     * 监控对象 集合
     */
    private List<FenceEntityDto> fenceEntityDtos;

}