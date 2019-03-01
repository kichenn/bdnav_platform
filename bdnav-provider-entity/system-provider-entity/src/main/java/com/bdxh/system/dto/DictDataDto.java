package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import lombok.Data;

@Data
public class DictDataDto extends Query {


    /**
     * 字典类型id
     */
    private Long dictId;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;

}
