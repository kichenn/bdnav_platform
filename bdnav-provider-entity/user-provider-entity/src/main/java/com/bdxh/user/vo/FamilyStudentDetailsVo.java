package com.bdxh.user.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import  lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-04 10:51
 **/
@Data
public class FamilyStudentDetailsVo implements Serializable {
    private static final long serialVersionUID = 3953145400679544770L;

    /**
     * 家长类
     */
    @ApiModelProperty(value = "家长类")
    private FamilyVo familyVo;

    /**
     * 学生类
     */
    @ApiModelProperty(value = "学生类")
    private StudentVo studentVo;
}