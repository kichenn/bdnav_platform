package com.bdxh.system.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
@Data
public class DictDto implements Serializable {

    private static final long serialVersionUID = 811259837532613258L;
    /**
     * 字典类型名称
     */
    @NotEmpty(message ="字典类型名称不能为空")
    private String name;
    /**
     * 字典类型
     */
    @NotEmpty(message ="字典类型不能为空")
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
