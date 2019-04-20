package com.bdxh.user.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 17:32
 **/

@Document(collection = "t_visit_logs")
@Data
public class VisitLogsMongo  implements Serializable {
    private static final long serialVersionUID = 7114096798856564807L;

    @Id
    private String id;

    @Field("school_id")
    private Integer schoolId;

    @Field("school_name")
    private String schoolName;

    @Field("school_code")
    private String schoolCode;

    @Field("student_id")
    private Long studentId;

    @Field("card_number")
    private String cardNumber;

    @Field("user_name")
    private String userName;

    @Field("url")
    private String url;

    @Field("create_date")
    private Date createDate;

    @Field("update_date")
    private Date updateDate;

    @Field("status")
    private Byte status;

    @Field("remark")
    private String remark;
}