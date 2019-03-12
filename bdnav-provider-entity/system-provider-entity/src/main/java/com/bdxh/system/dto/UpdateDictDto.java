package com.bdxh.system.dto;


import lombok.Data;

@Data
public class UpdateDictDto {
    /**
     * 字典id
     */
    private Long id;
    /**
     * 字典类型名称
     */
    private String name;
    /**
     * 字典类型
     */
    private String type;
    /**
     * 操作者
     */
    private Long operator;
    /**
     * 备份
     */
    private String remark;

}
