package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import lombok.Data;

import java.util.Date;
@Data
public class DictDataQueryDto extends Query {
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
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;
}
