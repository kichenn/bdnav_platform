package com.bdxh.school.dto;

import com.bdxh.school.enums.GroupTypeEnum;
import lombok.Data;

/**
 * @Description: 监控对象dto
 * @Author: Kang
 * @Date: 2019/4/25 14:37
 */
@Data
public class FenceEntityDto {

    /**
     * 监控人员的id
     */
    private Long id;

    /**
     * 监控人员的名称
     */
    private String name;

    /**
     * 监控人员描述
     */
    private String desc;

    /**
     * 监控人员所属学校名称
     */
    private String schoolName;

    /**
     * 监控人员所属班级或者部门名称
     */
    private String className;

    /**
     * 用户群类型(1 学生 2 老师)
     */
    private GroupTypeEnum groupTypeEnum;
}
