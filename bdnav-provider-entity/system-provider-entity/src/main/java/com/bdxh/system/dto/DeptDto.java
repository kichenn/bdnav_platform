package com.bdxh.system.dto;

import java.io.Serializable;

public class DeptDto implements Serializable {

    private static final long serialVersionUID = -6350638650907752885L;

    /**
     * 父级部门id
     */
    private Long parentId;

    /**
     * 父级部门ids
     */
    private String parentIds;

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
