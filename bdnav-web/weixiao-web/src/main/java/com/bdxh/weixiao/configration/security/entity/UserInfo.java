package com.bdxh.weixiao.configration.security.entity;

import com.bdxh.weixiao.configration.security.userdetail.WeixiaoGrantedAuthority;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 用户信息
 * @Author: Kang
 * @Date: 2019/5/14 15:57
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 8541838882121685433L;

    /*
    微信返参
     */
    //学校编码
    private String schoolCode;

    //名称
    private String name;

    //手机号
    private String phone;

    //学生卡号
    private String cardNumber;

    //微校学生id
    private String weixiaoStuId;

    // 身份类型，1为其他，2为学生，3为教职工，4为校友
    private String identityType;


    /*
      以下是扩展字段
     */
    //学生id
    private Long studentId;

    //家长id
    private Long familyId;

    //家长号
    private String familyCardNumber;

    //权限集合(role --- studentcardNumber 学生卡号)
    private List<WeixiaoPermit> weixiaoGrantedAuthorities;
}
