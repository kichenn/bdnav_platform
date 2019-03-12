package com.bdxh.system.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class DictDataDto implements Serializable {


    private static final long serialVersionUID = -3433433657513888923L;

    @ApiModelProperty("字典类型id")
    private Long dictId;



    @ApiModelProperty("字典名称")
    private String dataName;


    /**
     * 字典值
     */
    @ApiModelProperty("字典值")
    private String dataValue;


    @ApiModelProperty("排序")
    private Integer sort;


    @ApiModelProperty("操作人")
    private Long operator;


    @ApiModelProperty("备注")
    private String remark;

}
