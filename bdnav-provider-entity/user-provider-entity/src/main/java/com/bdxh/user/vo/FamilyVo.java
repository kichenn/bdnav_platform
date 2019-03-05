/**
 * Copyright (C), 2019-2019
 * FileName: FamilyVo
 * Author:   bdxh
 * Date:     2019/3/1 12:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * bin           修改时间           版本号              描述
 */
package com.bdxh.user.vo;

import com.bdxh.user.entity.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FamilyVo {

    //家长ID
    @ApiModelProperty("家长ID")
    private Long id;

    //家长姓名
    @ApiModelProperty("家长姓名")
    private String fName;

    //家长性别
    @ApiModelProperty("家长性别")
    private Byte fGender;

    //家长电话
    @ApiModelProperty("家长电话")
    private String phone;

    //家长头像
    @ApiModelProperty("家长头像")
    private String image;

    //家长生日
    @ApiModelProperty("家长生日")
    private String birth;

   //学生数据集合
    @ApiModelProperty("学生数据集合")
    private List<Student> students;
    //备注
    @ApiModelProperty("备注")
    private String remark;
}
