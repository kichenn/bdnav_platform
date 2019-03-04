package com.bdxh.system.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeptVo{
    /**
     * 主键id
     */
    private Long id;

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
    /**
     * 子部门
     */
    private List<DeptVo> deptVos;


}
