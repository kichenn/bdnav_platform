package com.bdxh.user.dto;

import com.bdxh.common.base.page.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-27 18:17
 **/
@Data
public class FamilyStudentQueryDto  extends Query {


    /**
     * 家长号
     */
    @ApiModelProperty(name = "家长号")
    private String cardNumber;


    /**
     * 学生姓名
     */
    @ApiModelProperty(name = "学生姓名")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty(name = "学生学号")
    private String studentNumber;




}