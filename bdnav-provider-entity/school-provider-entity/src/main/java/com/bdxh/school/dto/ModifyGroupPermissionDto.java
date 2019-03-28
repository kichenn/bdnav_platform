package com.bdxh.school.dto;

import com.bdxh.school.enums.AccessFlagEnum;
import com.bdxh.school.enums.GroupTypeEnum;
import com.bdxh.school.enums.RecursionPermissionStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description: 修改门禁组 dto
 * @Author: Kang
 * @Date: 2019/3/27 17:42
 */
@Data
public class ModifyGroupPermissionDto {


    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    @NotNull(message = "用户群类型不能为空")
    @ApiModelProperty("用户群类型 1 学生 2 老师")
    private GroupTypeEnum groupTypeEnum;

    @ApiModelProperty("部门id")
    private Long groupId;

    @NotNull(message = "是否递归权限不能为空")
    @ApiModelProperty("是否递归权限 1 是 2 否")
    private RecursionPermissionStatusEnum recursionPermissionStatusEnum;

    @ApiModelProperty("递归权限ids")
    private String recursionPermissionIds;

    @ApiModelProperty("设备id")
    private Long deviceId;

    @ApiModelProperty("设备编码")
    private String deviceCode;

    @NotNull(message = "权限标记不能为空")
    @ApiModelProperty("权限标识 1 允许 2 不允许")
    private AccessFlagEnum accessFlagEnum;

    @ApiModelProperty("允许开始时间")
    private Date accessBegin;

    @ApiModelProperty("允许结束时间")
    private Date accessEnd;

    @ApiModelProperty("周时间段")
    private String accessDayMark;

    @ApiModelProperty("日时间段 00111(这种形式0允许1不允许)")
    private String accessTimeMark;

    @ApiModelProperty("操作人（前端不需要传递，后端自己获取）")
    private Long operator;

    @ApiModelProperty("操作人姓名（前端不需要传递，后端自己获取）")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;

}