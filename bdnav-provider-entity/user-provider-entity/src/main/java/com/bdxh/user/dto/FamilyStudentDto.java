package com.bdxh.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class FamilyStudentDto implements Serializable {

    private static final long serialVersionUID = 3231869906680207572L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 学校id
     */
    @NotNull(message ="学校id不能为空")
    private Long schoolId;

    /**
     * 学校编码
     */
    @NotNull(message ="学校编码不能为空")
    private String schoolCode;

    /**
     * 家长号
     */
    @NotNull(message ="家长号不能为空")
    private String cardNumber;

    /**
     * 家长id
     */
    @NotNull(message ="家长id不能为空")
    private Long familyId;

    /**
     * 学生id
     */
    @NotNull(message ="学生id不能为空")
    private Long studentId;

    /**
     * 学生姓名
     */
    @NotNull(message ="学生姓名不能为空")
    private String studentName;

    /**
     * 学生学号
     */
    @NotNull(message ="学生姓名不能为空")
    private String studentNumber;

    /**
     * 关系 父亲 母亲 等等
     */
    @NotNull(message ="关系 不能为空")
    private String relation;

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
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;

}