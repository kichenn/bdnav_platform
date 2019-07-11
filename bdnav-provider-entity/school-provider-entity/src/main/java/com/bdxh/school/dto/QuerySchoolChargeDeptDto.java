package com.bdxh.school.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/** 查询学校收费部门的dto
 * @author WanMing
 * @date 2019/7/10 18:48
 */
@Data
public class QuerySchoolChargeDeptDto extends Query {


    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    @NotBlank(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    @NotBlank(message = "收费部门名称不能为空")
    private String chargeDeptName;

}
