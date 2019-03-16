package com.bdxh.system.dto;

import com.bdxh.common.base.page.Query;
import lombok.Data;


@Data
public class DeptQueryDto extends Query {



    /**
     * 部门名称
     */
    private Long deptName;

    /**
     * 部门全称
     */
    private String deptFullName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 部门层级
     */
    private Byte level;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 备注
     */
    private String remark;
}
