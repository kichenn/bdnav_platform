package com.bdxh.user.dto;

import com.bdxh.user.enums.FamilyBlackUrlStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加家长端黑名单dto
 * @author WanMing
 * @date 2019/6/25 10:31
 */
@Data
public class AddFamilyBlackUrlDto {

    /**
     * 学校主键
     */
    @ApiModelProperty("学校主键")
    private Long schoolId;

    /**
     * 学校编码
     */
    @ApiModelProperty("学校编码")
    private String schoolCode;

    /**
     * 学校名称
     */
    @ApiModelProperty("学校名称")
    private String schoolName;


    /**
     * 家长号
     */
    @ApiModelProperty("家长号")
    @NotBlank(message = "家长号不能为空")
    private String cardNumber;

    /**
     * 家长主键
     */
    @ApiModelProperty("家长主键")
    private Long familyId;

    /**
     * 家长名称
     */
    @ApiModelProperty("家长名称")
    private String familyName;

    /**
     * 学生主键
     */
    @ApiModelProperty("学生主键")
    private Long studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    @NotBlank(message = "学生学号不能为空")
    private String studentNumber;

    /**
     * 网站名称
     */
    @ApiModelProperty("网站名称")
    private String siteName;

    /**
     * 填写域名或者ip
     */
    @ApiModelProperty("填写域名或者ip")
    private String ip;

    /**
     * 状态 1 启用 2 禁用
     */
    @ApiModelProperty("状态 1 启用 2 禁用")
    @NotNull(message = "状态不能为空")
    private FamilyBlackUrlStatusEnum status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
