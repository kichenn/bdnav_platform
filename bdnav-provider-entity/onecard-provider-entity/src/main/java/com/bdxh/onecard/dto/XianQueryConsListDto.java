package com.bdxh.onecard.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @description: 消费记录查询dto
 * @author: xuyuan
 * @create: 2019-01-11 11:42
 **/
@Data
public class XianQueryConsListDto implements Serializable {

    /**
     * 学校编码
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;

    /**
     * 卡号
     */
    @NotEmpty(message = "卡号不能为空")
    private String cardNumber;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String userName;

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 截止时间
     */
    private String entime;

}
