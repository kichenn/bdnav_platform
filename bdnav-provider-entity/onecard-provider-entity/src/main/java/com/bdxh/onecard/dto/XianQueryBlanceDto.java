package com.bdxh.onecard.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @description: 西安一卡通余额查询dto
 * @author: xuyuan
 * @create: 2019-01-10 19:32
 **/
@Data
public class XianQueryBlanceDto implements Serializable {

    private static final long serialVersionUID = 3723722877549314308L;

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String cardNumber;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String userName;

}
