package com.bdxh.school.vo;

import com.bdxh.school.entity.BlackUrl;
import lombok.Data;

/**
 * @Description: url 列表的显示vo
 * @Author: Kang
 * @Date: 2019/4/18 14:10
 */
@Data
public class BlackUrlShowVo extends BlackUrl {

    /**
     * 学校名称
     */
    private String schoolName;
}
