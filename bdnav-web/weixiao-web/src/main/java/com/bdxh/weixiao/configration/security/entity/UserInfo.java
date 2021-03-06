package com.bdxh.weixiao.configration.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    //孩子的组织架构(班级id)信息
    private Long orgId;

    //学生id
    private Long userId;

    //学生卡号(如果是家长登录则有多个孩子，如果是学生自己登录则list大小为1)
    private List<String> cardNumber;

    //微校学生id
    private String weixiaoStuId;

    // 身份类型，1为家长，2为学生，3为教职工，4为校友
    private String identityType;

    /*
      以下是扩展字段
     */
    //学校id
    private Long schoolId;

    private String schoolName;

    //家长id （如果是班主任登录此处id则为班主任id）
    private Long familyId;

    //家长号（如果是班主任登录此处cardnumber则为班主任cardnumber）
    private String familyCardNumber;

    //家长姓名（如果是班主任登录此处name为班主任name）
    private String familyName;

    //权限集合(key:roleName ---Value:studentcardNumber 学生卡号)
    private Map<String, List<String>> weixiaoGrantedAuthorities;
}
