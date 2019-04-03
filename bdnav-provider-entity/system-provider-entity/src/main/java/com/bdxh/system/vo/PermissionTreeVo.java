package com.bdxh.system.vo;

import com.bdxh.common.helper.tree.bean.TreeBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionTreeVo extends TreeBean {



   /* @ApiModelProperty("类型 1 菜单 2 按钮")
    private Byte type;
    */
/*
    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("修改时间")
    private Date updateDate;

    @ApiModelProperty("操作人")
    private Long operator;
*/

   @ApiModelProperty("备注")
    private String remark;
}
