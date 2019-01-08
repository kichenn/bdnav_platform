package com.bdxh.backend.entity;

import javax.persistence.*;

@Table(name = "t_school_base_config")
public class SchoolBaseConfig {
    @Id
    private Integer id;

    @Column(name = "school_code")
    private String schoolCode;

    @Column(name = "http_url")
    private String httpUrl;

    @Column(name = "app_id")
    private String appId;

    private String secret;

    private String cver;

    private String type;

    private String remark;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return school_code
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * @param schoolCode
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode == null ? null : schoolCode.trim();
    }

    /**
     * @return http_url
     */
    public String getHttpUrl() {
        return httpUrl;
    }

    /**
     * @param httpUrl
     */
    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl == null ? null : httpUrl.trim();
    }

    /**
     * @return app_id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * @return secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret
     */
    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    /**
     * @return cver
     */
    public String getCver() {
        return cver;
    }

    /**
     * @param cver
     */
    public void setCver(String cver) {
        this.cver = cver == null ? null : cver.trim();
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}