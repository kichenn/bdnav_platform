package com.bdxh.school.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchoolPermissionTreeVo extends TreeBean {

    @ApiModelProperty("父级菜单ids")
    private String parentIds;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("前端组件")
    private String component;

//    @ApiModelProperty("排序")
//    private Integer sort;

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
}
