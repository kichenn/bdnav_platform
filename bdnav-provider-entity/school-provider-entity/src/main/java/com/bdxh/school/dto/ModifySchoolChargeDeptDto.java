package com.bdxh.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**修改学校收费部门的dto
 * @author WanMing
 * @date 2019/7/10 18:42
 */
@Data
public class ModifySchoolChargeDeptDto {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    private String chargeDeptName;

    /**
     * 操作人主键
     */
    @ApiModelProperty("操作人主键")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
