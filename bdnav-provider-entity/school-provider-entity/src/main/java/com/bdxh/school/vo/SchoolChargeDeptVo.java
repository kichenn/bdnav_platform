package com.bdxh.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**展示学校收费部门的vo
 * @author WanMing
 * @date 2019/7/10 18:59
 */
@Data
public class SchoolChargeDeptVo {

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
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;

    /**
     * 收费部门名称
     */
    @ApiModelProperty("收费部门名称")
    private String chargeDeptName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createDate;

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
