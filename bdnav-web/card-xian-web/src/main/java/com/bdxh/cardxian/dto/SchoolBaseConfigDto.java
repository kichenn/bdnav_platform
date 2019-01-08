package com.bdxh.cardxian.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class SchoolBaseConfigDto implements Serializable {

    private static final long serialVersionUID = 5237303274636001627L;

    private Long id;

    private String schoolCode;

    private String httpUrl;

    private String appId;

    private String secret;

    private String cver;

    private String type;

    private String remark;


}
