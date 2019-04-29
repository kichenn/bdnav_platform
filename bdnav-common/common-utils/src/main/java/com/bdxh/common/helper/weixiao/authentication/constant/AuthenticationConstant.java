package com.bdxh.common.helper.weixiao.authentication.constant;

/**
 * @description: 微校用户认证常量
 * @author: binzh
 * @create: 2019-04-24 11:47
 **/
public class AuthenticationConstant {

    //测试用APPKEY
    public static final String APP_KEY="D6C1E793E3C2305C";

    //测试用APPSECRET
    public static final String APP_SECRET="6F2A7B2332534486324109D9EBC50102";

    public static final String TEACHER="教职工";

    public static final String STUDENT="学生";

    public static final String FAMILY="家长";


    //跳转微校指定页面获取wxcode接口地址
    public static final String WEIXIAO_LOGIN_URL="https://weixiao.qq.com/apps/school-auth/login";

    //通过wxcode换取网页授权access_token接口地址
    // 请求类型：POST
    public static final String WEIXIAO_ACCESS_TOKEN_URL="https://weixiao.qq.com/apps/school-auth/access-token";

    //通过access_token换取用户基本信息
    // 请求类型：POST
    public static final String WEIXIAO_USER_INFO_URL="https://weixiao.qq.com/apps/school-auth/user-info";

    //主动推送请求更新学生信息接口地址 sync-stu-info
    public static final String SYNC_USER_INFO_URL="https://uni.weixiao.qq.com/open/identity/sync-stu-info";

    //用户激活时的用户认证接口
    public static final String RECEIVE_STU_INFO="https://uni.weixiao.qq.com/identity/receive-stu-info";
}