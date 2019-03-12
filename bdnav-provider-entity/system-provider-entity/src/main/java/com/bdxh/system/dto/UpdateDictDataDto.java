package com.bdxh.system.dto;


import lombok.Data;


@Data
public class UpdateDictDataDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 字典类型id
     */
    private Long dictId;

    /**
     * 字典名称
     */
    private String dataName;


    /**
     * 字典值
     */
    private String dataValue;


    /**
     * 排序
     */
    private Integer sort;


    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;
}
