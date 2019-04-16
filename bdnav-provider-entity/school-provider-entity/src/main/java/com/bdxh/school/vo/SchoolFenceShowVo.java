package com.bdxh.school.vo;

import com.bdxh.school.entity.SchoolFence;
import lombok.Data;

/**
 * @Description: 学校围栏列表展示vo
 * @Author: Kang
 * @Date: 2019/4/16 12:05
 */
@Data
public class SchoolFenceShowVo extends SchoolFence {

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 部门名称或者院系名称
     */
    private String groupName;
}
