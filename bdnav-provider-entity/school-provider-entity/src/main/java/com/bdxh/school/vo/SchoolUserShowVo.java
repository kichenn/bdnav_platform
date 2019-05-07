package com.bdxh.school.vo;

import com.bdxh.school.entity.SchoolUser;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * @Description: 学校用户列表 vo
 * @Author: Kang
 * @Date: 2019/4/18 14:37
 */
@Data
public class SchoolUserShowVo extends SchoolUser {

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 角色id + 角色用户信息
     */
    private List<Long> roleIds;
}
