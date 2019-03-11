package com.bdxh.system.vo;

import com.bdxh.system.entity.Dept;
import lombok.Data;

import java.io.Serializable;


@Data
public class DeptDetailsVo implements Serializable {

    private static final long serialVersionUID = -1575660987309037287L;


    /**
     * 父级名称
     */
    private String parentName;
    /**
     * 直属层级
     */
    private Byte parentLeven;
    /**
     * 部门
     */
    private Dept dept;
}
