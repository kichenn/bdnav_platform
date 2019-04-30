package com.bdxh.common.helper.weixiao.authentication.request;

import lombok.Data;
/**
 * @description:
 * @author: binzh
 * @create: 2019-04-24 14:44
 **/
@Data
public class SynUserInfoRequest {

    //如 用户类型为学生  那么这个值就为  本科生
    private String identity_title;

    //用户类型 当前只有三种卡  学生 家长  老师
    private String identity_type;

    //学校Code
    private String school_code;

    //姓名
    private  String name;

    //头像
    private  String head_image;

    //学工号
    private  String card_number;

    //(性别)
    private  String gender;

    //(年级)
    private  String grade;

    //(学院/部门)
    private  String college;

    //(专业)
    private  String profession;

    //(班级) 这里班级名为class_name  实际微校那边是用的class 因为class是java中的关键词，在转成JSON后  把这个键名为class_name的属性删除 加一个class的属性 对应班级名称
    private  String class_name;

    //(组织架构)
    private  String organization;

    //(有效期)
    private  String expire_at;

    //(手机)
    private  String telephone;

    //(证件类型)
    //0 表达空值
    //1	身份证
    //2	回乡证
    //3	台胞证
    //4	外国护照
    //5	其它
    //当证件号码有值，证件类型和国籍传空值时，则默认将证件类型和国籍分别归属于"1"（身份证）和"CHN"（中国）
    //当证件号码有值 ，证件类型或国籍有值，但不满足下列规则时，则默认将证件类型和国籍分别归属于"1"（身份证）和"CHN"（中国）
    private  String card_type;

    //(证件号码)
    private  String id_card;

    //国籍
    /**
     * CHN	中国
     * CAF	中非
     * CHL	智利
     * TCD	乍得
     * ZMB	赞比亚
     * VNM	越南
     * JOR	约旦
     * ISR	以色列
     * IDN	印尼
     * GBR	英国
     * IND	印度
     * IRN	伊朗
     * IRQ	伊拉克
     * ITA	意大利
     * YEM	也门
     * ARM	亚美尼亚
     * JAM	牙买加
     * SYR	叙利亚
     * HUN	匈牙利
     * NZL	新西兰
     * SGP	新加坡
     * GRC	希腊
     * ESP	西班牙
     * UZB	乌兹别克斯坦
     * URY	乌拉圭
     * UKR	乌克兰
     * UGA	乌干达
     * BRU	文莱
     * VEN	委内瑞拉
     * GTM	危地马拉
     * VUT	瓦努阿图
     * TUV	图瓦卢
     * TUN	突尼斯
     * TKM	土库曼斯坦
     * TUR	土耳其
     * TTO	特立尼达和多巴哥
     * TZA	坦桑尼亚
     * TON	汤加
     * TJK	塔吉克斯坦
     * THA	泰国
     * SOM	索马里
     * SLB	所罗门群岛
     * SUR	苏里南
     * SDN	苏丹
     * SWZ	斯威士兰
     * SVN	斯洛文尼亚
     * SVK	斯洛伐克
     * LKA	斯里兰卡
     * VCT	圣文森特和格林纳丁斯
     * SMR	圣马力诺
     * LCA	圣卢西亚
     * KNA	圣基茨和尼维斯
     * STP	圣多美和普林西比
     * SAU	沙特阿拉伯
     * WSM	萨摩亚
     * SYC	塞舌尔
     * CYP	塞浦路斯
     * SEN	塞内加尔
     * SLE	塞拉利昂
     * SRB	塞尔维亚
     * SLV	萨尔瓦多
     * CHE	瑞士
     * SWE	瑞典
     * JPN	日本
     * PRT	葡萄牙
     * PLW	帕劳
     * NOR	挪威
     * NIU	纽埃
     * NGA	尼日利亚
     * NER	尼日尔
     * NIC	尼加拉瓜
     * NPL	尼泊尔
     * SSD	南苏丹
     * ZAF	南非
     * NAM	纳米比亚
     * MEX	墨西哥
     * MOZ	莫桑比克
     * MCO	摩纳哥
     * MAR	摩洛哥
     * MDA	摩尔多瓦
     * PER	秘鲁
     * FSM	密克罗尼西亚联邦
     * MMR	缅甸
     * BGD	孟加拉国
     * MNG	蒙古国
     * USA	美国
     * MHL	马绍尔群岛
     * MRT	毛里塔尼亚
     * MUS	毛里求斯
     * MLI	马里
     * MWI	马拉维
     * MYS	马来西亚
     * MLT	马耳他
     * MDV	马尔代夫
     * MDG	马达加斯加
     * RWA	卢旺达
     * LUX	卢森堡
     * ROM	罗马尼亚
     * NRU	瑙鲁
     * LTU	立陶宛
     * LIE	列支敦士登
     * LBY	利比亚
     * LBR	利比里亚
     * LBN	黎巴嫩
     * LVA	拉脱维亚
     * LAO	老挝
     * LSO	莱索托
     * COK	库克群岛
     * KWT	科威特
     * CIV	科特迪瓦
     * KEN	肯尼亚
     * COM	科摩罗
     * HRV	克罗地亚
     * QAT	卡塔尔
     * CMR	喀麦隆
     * GNB	几内亚比绍
     * GIN	几内亚
     * ZWE	津巴布韦
     * KIR	基里巴斯
     * KGZ	吉尔吉斯斯坦
     * CZE	捷克
     * DJI	吉布提
     * GAB	加蓬
     * KHM	柬埔寨
     * CAN	加拿大
     * GHA	加纳
     * FJI	斐济
     * HND	洪都拉斯
     * NLD	荷兰
     * MNE	黑山
     * KAZ	哈萨克斯坦
     * KOR	韩国
     * HTI	海地
     * GUY	圭亚那
     * CUB	古巴
     * CRI	哥斯达黎加
     * COL	哥伦比亚
     * GEO	格鲁吉亚
     * GRD	格林纳达
     * COD	刚果（金）
     * COG	刚果（布）
     * GMB	冈比亚
     * CPV	佛得角
     * FIN	芬兰
     * PHL	菲律宾
     * FRA	法国
     * RUS	俄罗斯
     * ERI	厄立特里亚
     * ECU	厄瓜多尔
     * DMA	多米尼克
     * DMA	多米尼加
     * TGO	多哥
     * TMP	东帝汶
     * VAT	梵蒂冈
     * DEU	德国
     * DNK	丹麦
     * GNQ	赤道几内亚
     * PRK	朝鲜
     * BDI	布隆迪
     * BFA	布基纳法索
     * BTN	不丹
     * BIH	波斯尼亚和黑塞哥维那
     * BLZ	伯利兹
     * BOL	玻利维亚
     * POL	波兰
     * BWA	博茨瓦纳
     * ISL	冰岛
     * BEL	比利时
     * BEN	贝宁
     * MKD	北马其顿
     * BRA	巴西
     * BGR	保加利亚
     * PAN	巴拿马
     * BHR	巴林
     * PLE	巴勒斯坦
     * PRY	巴拉圭
     * PAK	巴基斯坦
     * BLR	白俄罗斯
     * BHS	巴哈马
     * PNG	巴布亚新几内亚
     * BRB	巴巴多斯
     * AZE	阿塞拜疆
     * AUT	奥地利
     * AUS	澳大利亚
     * ATG	安提瓜和巴布达
     * AGO	安哥拉
     * AND	安道尔
     * OMN	阿曼
     * ARE	阿联酋
     * EST	爱沙尼亚
     * ETH	埃塞俄比亚
     * EGY	埃及
     * IRL	爱尔兰
     * ARG	阿根廷
     * AFG	阿富汗
     * DZA	阿尔及利亚
     * ALB	阿尔巴尼亚
     */
    private  String country;

    //(工作单位)
    private  String employer;

    //(邮箱)
    private  String email;

    //(QQ号)
    private  String qq;

    //(民族)
    private  String nation;

    //(校区)
    private  String campus;

    //(宿舍号)
    private  String dorm_number;

    //(生源地)
    private  String origin_place;

    //(毕业学校)
    private  String graduated_school;

    //是否进行实名认证 1-是；0-否
    private Byte real_name_verify;

    //(家庭住址)
    private  String address;

    //(物理卡号)
    private  String physical_card_number;

    //(物理芯片号)
    private  String physical_chip_number;
}