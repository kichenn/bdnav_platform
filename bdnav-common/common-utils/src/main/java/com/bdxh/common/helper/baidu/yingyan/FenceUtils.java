package com.bdxh.common.helper.baidu.yingyan;

import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
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
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 删除围栏
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
            result = HttpClientUtils.doPost(FenceConstant.CREATE_ROUND_URL, map);
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @Description: 创建监控对象
     * @Author: Kang
     * @Date: 2019/4/16 11:55
     */
    public static String createNewEntity(CreateNewEntityRequest request) {
        Map<String, Object> map = toMap(request);
        String result = "";
        try {
            result = HttpClientUtils.doPost(FenceConstant.CREATE_NEW_ENTITY, map);
            log.info(result);
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
