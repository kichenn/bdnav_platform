package com.bdxh.system.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PermissionVo {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父级菜单id")
    private Long parentId;

    @ApiModelProperty("父级菜单ids")
    private String parentIds;

    @ApiModelProperty("菜单名称")
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

    @ApiModelProperty("类型 1 菜单 2 按钮")
    private Byte type;

    /*@ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("修改时间")
    private Date updateDate;

    @ApiModelProperty("操作人")
    private Long operator;*/

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("子集合")
    private List<PermissionVo> permissionVos;
}
