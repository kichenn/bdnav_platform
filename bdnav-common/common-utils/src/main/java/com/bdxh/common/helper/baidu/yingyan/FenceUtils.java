package com.bdxh.common.helper.baidu.yingyan;

import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
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

        createRoundFence(request);
    }
}
