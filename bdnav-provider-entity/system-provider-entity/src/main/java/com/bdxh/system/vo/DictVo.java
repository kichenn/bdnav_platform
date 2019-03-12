package com.bdxh.system.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DictVo {
    /**
     * 主键
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
