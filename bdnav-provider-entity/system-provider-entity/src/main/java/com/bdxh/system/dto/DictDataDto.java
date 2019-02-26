package com.bdxh.system.dto;

import java.io.Serializable;

public class DictDataDto implements Serializable {

    private static final long serialVersionUID = 3988319613265251497L;

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
