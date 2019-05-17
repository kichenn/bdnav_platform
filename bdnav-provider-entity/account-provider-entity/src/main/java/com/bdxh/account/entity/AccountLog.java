package com.bdxh.account.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Long;
import java.lang.Byte;

/**
 * @Description: 实体类
 * @Author Kang
 * @Date 2019-05-16 14:18:09
 */
@Data
@Document(collection = "t_account_log")
public class AccountLog implements Serializable {

    private static final long serialVersionUID = 8984645711385005541L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 学校主键
     */
    @Field("school_id")
    private Long schoolId;

    /**
     * 学校编码
     */
    @Field("school_code")
    private String schoolCode;

    /**
     * 学校名称
     */
    @Field("school_name")
    private String schoolName;

    /**
     * 所属班级主键
     */
    @Field("group_id")
    private String groupId;

    /**
     * 用户主键
     */
    @Field("user_id")
    private Long userId;

    /**
     * 学号
     */
    @Field("card_number")
    private String cardNumber;

    /**
     * 用户手机
     */
    @Field("user_phone")
    private String userPhone;

    /**
     * 姓名
     */
    @Field("user_name")
    private String userName;

    /**
     * 登录账号
     */
    @Field("login_name")
    private String loginName;

    /**
     * 设备IMEI
     */
    @Field("imei")
    private String imei;

    /**
     * 个推推送ID
     */
    @Field("client_id")
    private String clientId;

    /**
     * 操作系统 1是安卓 2是IOS
     */
    @Field("operation_system")
    private Byte operationSystem;

    /**
     * 创建时间
     */
    @Field("create_date")
    private Date createDate;

    /**
     * 备注
     */
    @Field("remark")
    private String remark;


}