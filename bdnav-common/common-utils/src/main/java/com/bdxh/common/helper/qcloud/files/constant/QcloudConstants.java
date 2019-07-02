package com.bdxh.common.helper.qcloud.files.constant;

/**
* @Description:  存储桶常量
* @Author: Kang
* @Date: 2019/7/2 12:30
*/
public class QcloudConstants {

    public static final String SECRET_ID = "AKIDmhZcOvMyaVdNQZoBXw5xZtqVR6SqdIK6";

    public static final String SERCRET_KEY = "5wespZ2PYPnjS2BF3vfHhq8Hp8Fbkdyo";

    //储存地区简称名
    public static final String REGION_NAME = "ap-guangzhou";

    /**
     * @Description: 线程池大小，建议在客户端与COS网络充足(如使用腾讯云的CVM，同园区上传COS)的情况下，
     * 设置成16或32即可, 可较充分的利用网络资源
     * 对于使用公网传输且网络带宽质量不高的情况，
     * 建议减小该值，避免因网速过慢，造成请求超时。
     * @Author: Kang
     * @Date: 2019/3/12 17:23
     */
    public static final int N_THREADS = 500;//200;//32;

    //所上传资源路径的文件夹(图片)
    public static final String RESOURCES_PREFIX = "data/";

    //所上传资源路径的文件夹(apk)
    public static final String RESOURCES_PREFIX1 = "files/";

    //所上传资源路径的文件夹(mp4)
    public static final String RESOURCES_PREFIX2 = "video/";

    // bucket 的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式 (后台存储桶)
    public static final String BUCKET_NAME = "bdnav-1258570075-1258570075";

    //app存储桶
    public static final String APP_BUCKET_NAME = "app-1258570075";
}
