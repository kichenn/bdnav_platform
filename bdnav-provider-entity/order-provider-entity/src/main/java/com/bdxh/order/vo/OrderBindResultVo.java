package com.bdxh.order.vo;

import lombok.Data;

/**
* @Description:   支付成功后订单编号，绑定双方订单并修改订单vo
* @Author: Kang
* @Date: 2019/6/21 19:04
*/
@Data
public class OrderBindResultVo {

    /**
     * 绑定订单后的返回值
     */
    private Long result;
}
