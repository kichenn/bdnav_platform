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
    @Field("id")
    private Long id;

    @Field("schoolId")
    private Long school_id;

    @Field("schoolName")
    private String school_name;

    @Field("schoolCode")
    private String school_code;

    @Field("studentId")
    private Long student_id;

    @Field("cardNumber")
    private String card_number;

    @Field("userName")
    private String user_name;

    @Field("url")
    private String url;

    @Field("createDate")
    private Date create_date;

    @Field("updateDate")
    private Date update_date;

    @Field("status")
    private Byte status;

    @Field("remark")
    private String remark;
}