package com.bdxh.servicepermit.entity;

import javax.persistence.Table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.lang.String;
import java.lang.Long;

/**
 * @Description: 实体类
 * @Date 2019-04-26 10:26:58
 */
@Data
@Table(name = "t_service_user")
public class ServiceUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "school_code")
    private String schoolCode;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "family_id")
    private Long familyId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @Column(name = "days ")
    private Integer days;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "operator")
    private Long operator;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "remark")
    private String remark;


}