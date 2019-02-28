package com.bdxh.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherDeptDto implements Serializable {

    private static final long serialVersionUID = 6769383748212626201L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校编码
     */
    @NotNull(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 老师工号
     */
    @NotNull(message = "老师工号不能为空")
    private String cardNumber;

    /**
     * 老师id
     */
    @NotNull(message = "老师id不能为空")
    private Long teacherId;

    /**
     * 组织架构id
     */
    @NotNull(message = "组织架构id不能为空")
    private Long deptId;

    /**
     * 组织架构名称
     */
    @NotNull(message = "组织架构名称不能为空")
    private String deptName;

    /**
     * 组织架构ids
     */
    @NotNull(message = "组织架构ids不能为空")
    private String deptIds;

    /**
     * 组织架构names
     */
    @NotNull(message = "组织架构names不能为空")
    private String deptNames;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 操作人
     */
    private Long operator;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 备注
     */
    private String remark;

}