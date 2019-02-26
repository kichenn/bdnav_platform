package com.bdxh.school.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SchoolVo implements Serializable {

    private static final long serialVersionUID = -7781357195631117518L;
    /**
     * 学校编码
     */
    private String schoolCode;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校地区代码
     */
    private String schoolAreaCode;
}
