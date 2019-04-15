/**
 * Copyright (C), 2019-2019
 * FileName: TeacherVo
 * Author:   binzh
 * Date:     2019/3/9 15:02
 * Description: TOOO
 * History:
 */

package com.bdxh.user.vo;

import com.bdxh.common.base.page.Query;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TeacherVo extends Query implements Serializable {
    private static final long serialVersionUID = -1554572358517434246L;
    /**
     * 主键
     */
    @Id
    @ApiModelProperty("主键")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 学校id
     */
    @ApiModelProperty("学校id")
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
     * 校区名称
     */
    @ApiModelProperty("校区名称")
    private String campusName;

    /**
     * 老师姓名
     */
    @ApiModelProperty("老师姓名")
    private String name;

    /**
     * 老师性别
     */
    @ApiModelProperty("老师性别")
    private Byte gender;

    /**
     * 出身日期
     */
    @ApiModelProperty("出身日期")
    private String birth;

    /**
     * 老师职称
     */
    @ApiModelProperty("老师职称")
    private String position;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String cardNumber;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idcard;

    /**
     * qq号
     */
    @ApiModelProperty("qq号")
    private String qqNumber;

    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String wxNumber;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 图像
     */
    @ApiModelProperty("图像")
    private String image;

    @ApiModelProperty("用户头像名称")
    private String imageName;

    /**
     * 民族名称
     */
    @ApiModelProperty("民族名称")
    private String nationName;

    /**
     * 宿舍地址
     */
    @ApiModelProperty("宿舍地址")
    private String dormitoryAddress;

    /**
     * 家庭住址
     */
    @ApiModelProperty("家庭住址")
    private String adress;

    /**
     * 物理卡号
     */
    @ApiModelProperty("物理卡号")
    private String physicalNumber;

    /**
     * 物理芯片号
     */
    @ApiModelProperty("物理芯片号")
    private String physicalChipNumber;

    /**
     * 是否激活 1 未激活 2 激活
     */
    @ApiModelProperty("是否激活 1 未激活 2 激活")
    private Byte activate;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private Date createDate;

    /**
     * 修改日期
     */
    @ApiModelProperty("修改日期")
    private Date updateDate;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    private Long operator;

    /**
     * 操作人姓名
     */
    @ApiModelProperty("操作人姓名")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 老师绑定部门关系信息集合
     */
    @ApiModelProperty("老师绑定部门关系信息集合")
    private List<TeacherDeptVo> teacherDeptVos;
}
