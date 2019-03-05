package com.bdxh.common.helper.tree.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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


    @ApiModelProperty("是否展开直子节点")
    private Boolean expand=Boolean.FALSE;

    @ApiModelProperty("禁掉响应")
    private Boolean disabled=Boolean.FALSE;

    @ApiModelProperty("禁掉 checkbox")
    private Boolean disableCheckbox=Boolean.FALSE;

    @ApiModelProperty("是否选中子节点")
    private Boolean selected=Boolean.FALSE;

    @ApiModelProperty("是否勾选(如果勾选，子节点也会全部勾选)")
    private Boolean checked=Boolean.FALSE;

    @ApiModelProperty("子节点")
    private List<? extends TreeBean> children;
}
