package com.bdxh.school.vo;

import com.bdxh.school.entity.SchoolRole;
import lombok.Data;

/**
 * @Description: 学校角色列表vo
 * @Author: Kang
 * @Date: 2019/4/18 14:50
 */
@Data
public class SchoolRoleShowVo extends SchoolRole {

    /**
     * 学校名称
     */
    private String schoolName;
}
