package com.bdxh.wallet.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 充值记录查询条件类
 */
@Data
public class RechargeConditionsDto implements Serializable {

    private static final long serialVersionUID = 6970436863386413416L;

    /**
     * 学校唯一标识
     */
    @NotEmpty(message = "学校编码不能为空")
    private String schoolCode;
    /**
     * 用户标识
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 开始时间
     */
     private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 查询状态
     */
    private Integer status;
    /**
     * 首页
     */
    private Integer pageNum=1;
    /**
     * 大小
     */
    private Integer pageSize=3;
}
