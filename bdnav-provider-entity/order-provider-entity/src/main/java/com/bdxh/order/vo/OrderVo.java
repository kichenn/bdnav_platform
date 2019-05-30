package com.bdxh.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @description: 生成订单返回类
 * @author: xuyuan
 * @create: 2019-01-09 15:44
 **/
@Data
public class OrderVo implements Serializable {

    private static final long serialVersionUID = 3244051781774832266L;

    /**
     * 学校编码
     */
    private String schoolCode;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 购买数量
     */
    private Integer count;

}
