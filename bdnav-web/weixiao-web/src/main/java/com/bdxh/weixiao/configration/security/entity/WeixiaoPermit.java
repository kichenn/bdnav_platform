package com.bdxh.weixiao.configration.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 微校许可实体
 * @Author: Kang
 * @Date: 2019/5/31 10:45
 */
@Data
public class WeixiaoPermit implements Serializable {

    private static final long serialVersionUID = -1328770202411866655L;

    //角色信息（ROLE_FENCE）
    private String role;

    //有此权限的孩子卡号的集合
    private List<String> studentCardNumber;
}
