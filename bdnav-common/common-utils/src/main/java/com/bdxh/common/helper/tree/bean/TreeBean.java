package com.bdxh.common.helper.tree.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import sun.net.TelnetInputStream;

import java.util.Date;
import java.util.List;

@Data
public class TreeBean {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("节点名")
    private String title;

    @ApiModelProperty("节点层级")
    private String level;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("是否展开直子节点")
    private Boolean expand=Boolean.FALSE;

    @ApiModelProperty("禁掉响应")
    private Boolean disabled=Boolean.FALSE;

    @ApiModelProperty("禁掉 checkbox")
    private Boolean disableCheckbox=Boolean.FALSE;

    @ApiModelProperty("是否选中子节点")
    private Boolean selected=Boolean.FALSE;

    @ApiModelProperty("半选状态 ")
    private Boolean indeterminate=Boolean.FALSE;

    @ApiModelProperty("是否勾选(如果勾选，子节点也会全部勾选)")
    private Boolean checked=Boolean.FALSE;

    @ApiModelProperty("父级ids")
    private String parentIds;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("前端组件")
    private String component;

    @ApiModelProperty("路由路径")
    private String name;

    @ApiModelProperty("类型 1 菜单 2 按钮")
    private Byte type;

    @ApiModelProperty("子节点")
    private List<? extends TreeBean> children;
}
