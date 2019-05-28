package com.bdxh.common.helper.baidu.yingyan;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.common.helper.baidu.yingyan.request.FindTrackRequest;
import com.bdxh.common.helper.baidu.yingyan.request.ModifyFenceRoundRequest;
import com.bdxh.common.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;

import java.util.*;

/**
 * @Description: 百度围栏Utils
 * @Author: Kang
 * @Date: 2019/4/16 10:45
 */
@Slf4j
public class FenceUtils {

    /**
     * @Description: 创建百度圆形围栏
     * @Author: Kang
     * @Date: 2019/4/16 10:59
     */
    public static String createRoundFence(CreateFenceRoundRequest request) {
        Map<String, Object> map = toMap(request);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.CREATE_ROUND_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 修改百度圆形围栏
     * @Author: Kang
     * @Date: 2019/4/16 10:59
     */
    public static String modifyRoundFence(ModifyFenceRoundRequest request) {
        Map<String, Object> map = toMap(request);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.MODIFY_ROUND_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 增加围栏需监控的entity
     * @Author: Kang
     * @Date: 2019/4/26 9:44
     */
    public static String addMonitoredPerson(int fenceId, String entitys) {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("fence_id", fenceId);
        map.put("monitored_person", entitys);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.ROUND_ADD_ENTITY_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 去除围栏中监控的entity对象 （只是去除对entity监控，并不会删除entity的对象）
     * @注：entitys =#clearentity 为去除该围栏底下所有监控对象
     * @Author: Kang
     * @Date: 2019/4/26 14:06
     */
    public static String deleteMonitoredPerson(int fenceId, String entitys) {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("fence_id", fenceId);
        map.put("monitored_person", entitys);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.ROUND_DELETE_ENTITY_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @Description: 删除圆形围栏
     * @Author: Kang
     * @Date: 2019/4/16 14:12
     */
    public static String deleteRoundFence(int fenceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        List<Integer> fenceIds = new ArrayList<>();
        fenceIds.add(fenceId);
        map.put("fence_ids", fenceIds);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.DELETE_ROUND_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 增加监控终端实体
     * @Author: Kang
     * @Date: 2019/4/16 11:55
     */
    public static String createNewEntity(CreateNewEntityRequest request) {
        Map<String, Object> map = toMap(request);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.CREATE_NEW_ENTITY, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 删除监控终端实体
     * @Author: Kang
     * @Date: 2019/4/17 15:55
     */
    public static String deleteNewEntity(String entityName) {
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("entity_name", entityName);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.DELETE_NEW_ENTITY, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询围栏信息
     * @param fenceIds  1,2,3,4
     * @return
     */
    public static String findFence(String fenceIds){
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("fence_ids", fenceIds);
        String result = "";
        try {
            result = HttpClientUtils.doGet(FenceConstant.SEL_ROUND_IN_FENCE_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  获取单个实体的鹰眼轨迹
     * @param findTrackRequest
     * @return
     */
    public static String getTrack(FindTrackRequest findTrackRequest){
        Map<String, Object> map = toMap(findTrackRequest);
        String result = "";
        try {
            result = HttpClientUtils.doGet(FenceConstant.SEL_YINYAN_TRACK_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取单个围栏下的所有entity
     * @return
     */
    public static String listmonitoredperson(Integer fenceId){
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("fence_id", fenceId);
        String result = "";
        try {
            result = HttpClientUtils.doGet(FenceConstant.SEL_ENTITY_IN_FENCE_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查找entity最近一个轨迹点，支持实时纠偏
     * @param entity
     * @return
     */
    public static String getLatestPoint(String entity){
        Map<String, Object> map = new HashMap<>();
        map.put("ak", FenceConstant.AK);
        map.put("service_id", FenceConstant.SERVICE_ID);
        map.put("entity_name", entity);
        String result = "";
        try {
            result = HttpClientUtils.doGet(FenceConstant.SEL_YINYAN_LATESTPOINT_URL, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        } else if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        BeanMap beanMap = new BeanMap(obj);
        Iterator<String> it = beanMap.keyIterator();
        while (it.hasNext()) {
            String name = it.next();
            Object value = beanMap.get(name);
            // 转换时会将类名也转换成属性，此处去掉
            if (value != null && !name.equals("class")) {
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 测试创建围栏
     */
    public static void main(String[] args) {

 /*        String result=getLatestPoint("2011032919040314");
        JSONObject jsonObject=JSONObject.parseObject(result);
        System.out.println(jsonObject);
        if(jsonObject.get("status").equals(0)){
            System.out.println(jsonObject.getString("latest_point"));
        }*/

try {
    Map<String, Object> map = new HashMap<>();
    map.put("ak", FenceConstant.AK);
    map.put("service_id", FenceConstant.SERVICE_ID);
    map.put("entity_name", "2011032919040317");
    map.put("latitude","22.550738738891674");
    map.put("longitude","113.91204626688796");
    log.info("======"+Long.toString(new Date().getTime()/1000));
    map.put("loc_time",Long.toString(new Date().getTime()/1000));
    map.put("coord_type_input","bd09ll");
    String result="";
    result = HttpClientUtils.doPost("http://yingyan.baidu.com/api/v3/track/addpoint", map);
    System.out.println(result);
}
catch (Exception e){

}

/*
       String monitoredPerson="2011032920190516001";
        String entityResult = FenceUtils.deleteNewEntity(monitoredPerson);
        JSONObject entityResultJson = JSONObject.parseObject(entityResult);
        System.out.println(entityResult);*/
 /*     String createRoundResult =listmonitoredperson(63);
        JSONObject createRoundJson = JSONObject.parseObject(createRoundResult);
        System.out.println(createRoundJson);
/*
        CreateNewEntityRequest entityRequest = new CreateNewEntityRequest();
        entityRequest.setAk(FenceConstant.AK);
        entityRequest.setService_id(FenceConstant.SERVICE_ID);
        entityRequest.setEntity_name("测试监控对象一");
        entityRequest.setEntity_desc("测试用的yin");
        createNewEntity(entityRequest);


        CreateFenceRoundRequest request = new CreateFenceRoundRequest();
        request.setAk(FenceConstant.AK);
        request.setService_id(FenceConstant.SERVICE_ID);
        request.setDenoise(0);
        request.setCoord_type("bd09ll");
        request.setFence_name("测试围栏一");
        request.setLatitude(22.548);
        request.setLongitude(114.10987);
        request.setRadius(1603);
        request.setMonitored_person("测试监控对象一");
*/

       // createRoundFence(request);
    }
}
