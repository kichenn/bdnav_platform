package com.bdxh.school.vo;

import com.bdxh.school.entity.SchoolUser;
import lombok.Data;


/**
 * @Description: 学校用户列表 vo
 * @Author: Kang
 * @Date: 2019/4/18 14:37
 */
@Data
public class SchoolUserShowVo extends SchoolUser {

    private String schoolName;
}
