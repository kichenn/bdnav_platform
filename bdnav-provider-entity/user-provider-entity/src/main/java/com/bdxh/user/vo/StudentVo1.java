/**
 * Copyright (C), 2019-2019
 * FileName: StudentVo
 * Author:   binzh
 * Date:     2019/3/5 16:15
 * Description: TOOO
 * History:
 */
package com.bdxh.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StudentVo1 {

    /**
     * 学生id
     */
    private Long sId;

    /**
     * 学生姓名
     */
    private String sName;

}
