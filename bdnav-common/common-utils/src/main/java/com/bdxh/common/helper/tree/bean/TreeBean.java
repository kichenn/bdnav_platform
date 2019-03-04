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
    private String name;

    @ApiModelProperty("节点层级")
    private String level;

    @ApiModelProperty("子节点")
    private List<? extends TreeBean> treeLists;
}
