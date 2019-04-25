package com.bdxh.school.dto;

import com.bdxh.school.enums.SchoolPermissionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description: 增加角色的权限 dto
 * @Author: Kang
 * @Date: 2019/3/4 10:36
 */
@Data
public class AddSchoolPermissionDto {

    @ApiModelProperty("父级菜单id")
    private Long parentId;

    @ApiModelProperty("学校id")
    private Long schoolId;

    @ApiModelProperty("学校编码")
    private String schoolCode;

    //    @ApiModelProperty("父级菜单ids")
//    private String parentIds;
    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("路由地址")
    private String name;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("前端组件")
    private String component;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("层级")
    private Integer level;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("类型 1 菜单 2 按钮")
    private SchoolPermissionTypeEnum schoolPermissionTypeEnum;

    @ApiModelProperty("操作人（前端不需要传递，后端自己获取）")
    private Long operator;

    @ApiModelProperty("操作人姓名（前端不需要传递，后端自己获取）")
    private String operatorName;

    @ApiModelProperty("备注")
    private String remark;
}
