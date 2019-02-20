package com.bdxh.common.base.constant;


/**
 * 百度地图使用常量
 */
public class MapConstrants {

    public interface BaiDuMap {

        /**
         * 用户的AK，授权使用
         */
        String AK="PbA0yBlMDBv4rpQoWF1AMRYsrjpUGzW0";

        /**
         * service 的唯一标识
         */
        String SERVICE_ID="200853";

        /**
         * 百度地图验证安全码
         */
        String MCODE="21:D7:5B:22:7C:D2:E0:09:B0:DD:6E:1A:E3:C9:82:4C:BB:61:E0:C9;com.bdxht.plumeui";

        /**
         * 实时位置点查询;
         */
        String getLatestPointURL="http://yingyan.baidu.com/api/v3/track/getlatestpoint";

        /**
         * 轨迹路程查询
         */
        String getTrackURL="http://yingyan.baidu.com/api/v3/track/gettrack";

        /**
         * 创建圆形围栏
         */
        String createcirclefenceURL="http://yingyan.baidu.com/api/v3/fence/createcirclefence";

        /**
         * 删除圆形围栏
         */
        String delCreatecirclefenceURL="http://yingyan.baidu.com/api/v3/fence/delete";

        /**
         * 查询监控对象相对围栏的状态
         */
        String queryStatusURl="http://yingyan.baidu.com/api/v3/fence/querystatus";

        /**
         * 增加围栏监控对象
         */
        String addMonitoredPersonURL="http://yingyan.baidu.com/api/v3/fence/addmonitoredperson";

    }

}
