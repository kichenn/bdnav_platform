package com.bdxh.user.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 18:10
 **/
@Document(collection = "t_fence_alarm")
@Data
public class FenceAlarmMongo implements Serializable {
    private static final long serialVersionUID = 1873366085946013169L;


    @Id
    private String id;

    @Field("fence_id")
    private Long  fenceId;

    @Field("fence_name")
    private String fenceName;

    @Field("type")
    private Byte type;

    @Field("monitored_person")
    private String monitoredPerson;

    @Field("action")
    private String action;

    @Field("alarm_point")
    private String alarmPoint;

    @Field("pre_point")
    private String prePoint;

    @Field("school_id")
    private Long schoolId;

    @Field("school_code")
    private String schoolCode;

    @Field("school_name")
    private String schoolName;

    @Field("student_id")
    private Long studentId;

    @Field("card_number")
    private String cardNumber;

    @Field("student_name")
    private String studentName;

    @Field("create_date")
    private Date createDate;

    @Field("update_date")
    private Date updateDate;

    @Field("remark")
    private String remark;

}